package nosql.workshop.model;


import org.bson.Document;
import org.jongo.marshall.jackson.oid.MongoId;

/**
 * Installation sportive.
 */
public class Installation extends Document{

    @MongoId
    private String _id;

}
