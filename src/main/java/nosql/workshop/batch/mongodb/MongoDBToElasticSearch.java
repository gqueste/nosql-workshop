package nosql.workshop.batch.mongodb;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Index;
import nosql.workshop.connection.ESConnectionUtil;

import java.util.*;

/**
 * Created by Gabriel QUESTE & Quentin VICTOOR on 09/02/2016.
 */
public class MongoDBToElasticSearch {

    public static void main(String[] args) throws Exception{

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

        client.execute(bulk);

    }


    public static Collection<Index> getListBulkable(DBCursor cursor){
        ArrayList<Index> list = new ArrayList<>();

        while(cursor.hasNext()){
            list.add(createIndex(cursor.next()));
        }
        return list;
    }

    private static Index createIndex(DBObject object){
        Map<String, Object> source = new HashMap<String, Object>();
        String id = object.get("_id").toString();
        object.removeField("_id");
        object.removeField("dateMiseAJourFiche");
        source.put("installation", object);
        return new Index.Builder(object).id(id).build();

    }
}
