package nosql.workshop.batch.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.opencsv.CSVReader;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import nosql.workshop.connection.ESConnectionUtil;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gabriel QUESTE & Quentin VICTOOR on 09/02/2016.
 */
public class TownToElasticSearch {

    public static void main(String[] args){

        List<DBObject> dbObjectList = new ArrayList<>();
        List<String[]> myEntries = readCSV("src/main/resources/batch/csv/towns_paysdeloire.csv");
        myEntries.stream()
                .skip(1)
                .forEach(columns -> {
                    DBObject obj = new BasicDBObject()
                            .append("townName", columns[1].trim())
                            .append("id", columns[0].trim())
                            .append(
                                    "location",
                                    Arrays.asList(
                                            Double.valueOf(columns[6]),
                                            Double.valueOf(columns[7])
                                    )
                            );
                    dbObjectList.add(obj);
                });

        JestClient client = ESConnectionUtil.createClient("");

        Bulk bulk = new Bulk.Builder()
                .defaultIndex("towns")
                .defaultType("town")
                .addAction(getListBulkable(dbObjectList))
                .build();

        try {
            client.execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<Index> getListBulkable(List<DBObject> list){
        List<Index> indexes = new ArrayList<>();
        for (DBObject object: list) {
            indexes.add(createIndex(object));
        }
        return indexes;
    }

    private static Index createIndex(DBObject object){
        String id = object.get("id").toString();
        object.removeField("id");
        return new Index.Builder(object).id(id).build();
    }

    private static List<String[]> readCSV(String path) {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path));
            return reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
