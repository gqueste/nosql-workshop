package nosql.workshop.services;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.codestory.http.Context;
import nosql.workshop.model.Installation;
import nosql.workshop.model.stats.Average;
import nosql.workshop.model.stats.CountByActivity;
import nosql.workshop.model.stats.InstallationsStats;
import org.jongo.MongoCollection;
import org.jongo.marshall.jackson.oid.MongoId;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Service permettant de manipuler les installations sportives.
 */
@Singleton
public class InstallationService {
	/**
	 * Nom de la collection MongoDB.
	 */
	public static final String COLLECTION_NAME = "installations";

	private final MongoCollection installations;

	@Inject
	public InstallationService(MongoDB mongoDB) throws UnknownHostException {
		this.installations = mongoDB.getJongo().getCollection(COLLECTION_NAME);
	}

	public Installation random() {
		return installations.aggregate("{ $sample: { size: 1 } }").as(Installation.class).next();
	}

	public List<Installation> getAll(Context context) {
		return Lists.newArrayList(installations.find().as(Installation.class).iterator());
	}

	public InstallationsStats getStats() {
		InstallationsStats stats = new InstallationsStats();
		stats.setTotalCount(installations.count());
		stats.setCountByActivity(Lists.newArrayList(installations.aggregate("{$unwind: \"$equipements\"}")
				                                            .and("{$unwind: \"$equipements.activites\"}")
				                                            .and("{$group: {_id: \"$equipements.activites\", total:{$sum : 1}}}")
				                                            .and("{$project: {activite: \"$_id\", total : 1}}")
				                                            .and("{ $sort : { total : -1 } }")
				                                            .as(CountByActivity.class).iterator()));

		stats.setAverageEquipmentsPerInstallation(installations.aggregate("{$group: {_id: null, average : { $avg : { $size : \"$equipements\"}}}}")
				                                          .as(Average.class).next().getAverage());

		IdResult result = installations.aggregate("{ $unwind : \"$equipements\" }")
				                  .and("{ $group : { _id : \"$_id\", len : { $sum : 1 } } }")
				                  .and("{ $sort : { len : -1 } }")
				                  .and("{ $project: {\"_id\":1} }")
				                  .and("{ $limit :1 }")
				                  .as(IdResult.class).next();

		stats.setInstallationWithMaxEquipments(this.get(result.id));
		return stats;
	}

	public Installation get(String id) {
		return installations.findOne("{_id : '" + id + "'}").as(Installation.class);
	}

	public List<Installation> geoSearch(Context context) {
		float lng = Float.valueOf(context.query().get("lng"));
		float lat = Float.valueOf(context.query().get("lat"));
		int distance = Integer.valueOf(context.query().getInteger("distance"));
		String query = "{location : { $near : { $geometry : { type : \"Point\", coordinates : [ " + lng + ", " + lat + " ]}, $maxDistance : " + distance + "}}}";
		return Lists.newArrayList(installations.find(query).as(Installation.class).iterator());
	}

	public static class IdResult {
		@MongoId
		public String id;
	}

}
