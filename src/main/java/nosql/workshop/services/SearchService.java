package nosql.workshop.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Suggest;
import io.searchbox.core.SuggestResult;
import net.codestory.http.Context;
import nosql.workshop.connection.ESConnectionUtil;
import nosql.workshop.model.Equipement;
import nosql.workshop.model.Installation;
import nosql.workshop.model.suggest.TownSuggest;
import org.elasticsearch.common.inject.Singleton;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Search service permet d'encapsuler les appels vers ElasticSearch
 */
@Singleton
public class SearchService {

    public List<Installation> search(String param) throws IOException {
        List<Installation> list = new ArrayList<>();

        System.out.println(param);

        JestClient client = ESConnectionUtil.createClient("");

        String query = "{\n" +
                "        \"query\": {\n"+
                "           \"multi_match\": {\n"+
                "               \"query\": \"" + param.toLowerCase() + "\", \n"+
                "               \"fields\": [\"_all\"] \n" +
                "           }\n" +
                "       }\n" +
                "}";

        Search search = (Search) new Search.Builder(query)
                .addIndex("installations")
                .addType("installation")
                .build();

        JestResult result = client.execute(search);

        JsonObject object = result.getJsonObject();
        JsonArray hits = object.get("hits").getAsJsonObject().get("hits").getAsJsonArray();

        for(int i = 0; i < hits.size(); i++){
            Installation installation = new Installation();
            JsonObject hit = hits.get(i).getAsJsonObject();
            installation.set_id(hit.get("_id").getAsString());
            JsonObject objectInstallation = hit.get("_source").getAsJsonObject();
            installation.setNom(objectInstallation.get("nom").getAsString());

            Installation.Adresse adresse = new Installation.Adresse();
            JsonObject objectAdresse = objectInstallation.getAsJsonObject("adresse");
            adresse.setNumero(objectAdresse.get("numero").getAsString());
            adresse.setVoie(objectAdresse.get("voie").getAsString());
            adresse.setLieuDit(objectAdresse.get("lieuDit").getAsString());
            adresse.setCodePostal(objectAdresse.get("codePostal").getAsString());
            adresse.setCommune(objectAdresse.get("commune").getAsString());
            installation.setAdresse(adresse);

            Installation.Location location = new Installation.Location();
            JsonObject objectLocation = objectInstallation.getAsJsonObject("location");
            location.setType(objectLocation.get("type").getAsString());
            JsonArray coordinates = objectLocation.get("coordinates").getAsJsonArray();
            double[] doubleArray = {coordinates.get(0).getAsDouble(), coordinates.get(1).getAsDouble()};
            location.setCoordinates(doubleArray);
            installation.setLocation(location);

            installation.setMultiCommune(objectInstallation.get("multiCommune").getAsBoolean());
            installation.setNbPlacesParking(Float.floatToIntBits(objectInstallation.get("nbPlacesParking").getAsFloat()));
            installation.setNbPlacesParkingHandicapes(Float.floatToIntBits(objectInstallation.get("nbPlacesParkingHandicapes").getAsFloat()));

            List<Equipement> equipements = new ArrayList<>();
            JsonArray arrayEquipements = objectInstallation.get("equipements").getAsJsonArray();
            for(int j = 0; j < arrayEquipements.size(); j++){
                JsonObject objetEquipement = arrayEquipements.get(j).getAsJsonObject();
                Equipement equipement = new Equipement();
                equipement.setNumero(objetEquipement.get("numero").getAsString());
                equipement.setNom(objetEquipement.get("nom").getAsString());
                equipement.setType(objetEquipement.get("type").getAsString());
                equipement.setFamille(objetEquipement.get("famille").getAsString());
                List<String> activites = new ArrayList<>();
                JsonArray arrayActivites = objetEquipement.get("activites").getAsJsonArray();
                for(JsonElement activite: arrayActivites){
                    activites.add(activite.getAsString());
                }
                equipement.setActivites(activites);

                equipements.add(equipement);
            }
            installation.setEquipements(equipements);
            list.add(installation);
        }

        return list;
    }

    public List<TownSuggest> suggest(String text) throws IOException {
        List<TownSuggest> list = new ArrayList<>();
        JestClient client = ESConnectionUtil.createClient("");

        String query = "{\n" +
                "        \"query\": {\n"+
                "           \"wildcard\": {\n"+
                "               \"townName\": {\n"+
                "                   \"value\": \"*" + text.toLowerCase() + "*\" \n" +
                "               }\n"+
                "           }\n" +
                "       }\n" +
                "}";

        Search search = (Search) new Search.Builder(query)
                .addIndex("towns")
                .addType("town")
                .build();

        JestResult result = client.execute(search);

        JsonObject object = result.getJsonObject();
        JsonArray hits = object.get("hits").getAsJsonObject().get("hits").getAsJsonArray();

        for(int i = 0; i < hits.size(); i++){
            JsonObject hit = hits.get(i).getAsJsonObject();
            JsonObject town = hit.get("_source").getAsJsonObject();
            String townName = town.get("townName").getAsString();
            JsonArray location = town.get("location").getAsJsonArray();
            Double[] ret = {location.get(0).getAsDouble(), location.get(1).getAsDouble()};
            TownSuggest suggest = new TownSuggest(townName, Arrays.asList(ret));
            list.add(suggest);
        }
        return list;
    }

    public Double[] getLocation(String townName) throws IOException{
        JestClient client = ESConnectionUtil.createClient("");

        String query = "{\n" +
                "        \"query\": {\n"+
                "           \"match\": {\n"+
                "               \"townName\": \"" + townName + "\" \n" +
                "           }\n" +
                "       }\n" +
                "}";

        Search search = (Search) new Search.Builder(query)
                .addIndex("towns")
                .addType("town")
                .build();

        JestResult result = client.execute(search);

        JsonObject object = result.getJsonObject();
        JsonArray hits = object.get("hits").getAsJsonObject().get("hits").getAsJsonArray();
        if(hits.size() > 0){
            JsonObject firstHit = hits.get(0).getAsJsonObject();
            JsonObject town = firstHit.get("_source").getAsJsonObject();
            JsonArray location = town.get("location").getAsJsonArray();
            Double[] ret = {location.get(0).getAsDouble(), location.get(1).getAsDouble()};
            return ret;
        }
        else {
            return null;
        }
    }
}
