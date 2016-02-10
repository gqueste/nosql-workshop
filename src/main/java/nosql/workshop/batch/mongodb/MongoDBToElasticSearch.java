package nosql.workshop.batch.mongodb;

import com.mongodb.*;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import nosql.workshop.connection.ESConnectionUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Gabriel QUESTE & Quentin VICTOOR on 09/02/2016.
 */
public class MongoDBToElasticSearch {

    public static void main(String[] args){

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("nosql-workshop");
        DBCollection collection = db.getCollection("installations");
        DBCursor cursor = collection.find();

        JestClient client = ESConnectionUtil.createClient("");

        Bulk bulk = new Bulk.Builder()
                .defaultIndex("installations")
                .defaultType("installation")
                .addAction(getListBulkable(cursor))
                .build();

        try {
            client.execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Collection<Index> getListBulkable(DBCursor cursor){
        ArrayList<Index> list = new ArrayList<>();

        while(cursor.hasNext()){
            list.add(createIndex(cursor.next()));
        }
        return list;
    }

    private static Index createIndex(DBObject object){
        String id = object.get("_id").toString();
        object.removeField("_id");
        object.removeField("dateMiseAJourFiche");
        return new Index.Builder(object).id(id).build();

    }
}
