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

        return result.getSourceAsObjectList(Installation.class);
    }

    public List<TownSuggest> suggest(String text) throws IOException {
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

        return result.getSourceAsObjectList(TownSuggest.class);
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