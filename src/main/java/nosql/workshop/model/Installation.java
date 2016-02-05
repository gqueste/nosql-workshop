package nosql.workshop.model;


import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Date;
import java.util.List;

/**
 * Installation sportive.
 */
public class Installation {

    @MongoId
    private String _id;
}
