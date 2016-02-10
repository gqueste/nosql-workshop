package nosql.workshop.batch.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;

import java.io.*;
import java.util.*;

public class CsvToMongoDb {

	public static MongoClient mongoClient = new MongoClient("localhost", 27017);
	public static MongoDatabase mongoDatabase = mongoClient.getDatabase("nosql-workshop");

	public static void main(String[] args) {

		createCollection("installations");
		MongoCollection mongoCollection = mongoDatabase.getCollection("installations");

		try (InputStream inputStream = CsvToMongoDb.class.getResourceAsStream("/batch/csv/installations.csv");
		     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			reader.lines()
					.skip(1)
					.filter(line -> line.length() > 0)
					.map(line -> line.split("\",\""))
					.forEach(columns -> {
						mongoCollection.insertOne(new Document()
								                          .append("_id", cleanData(columns[1]))
								                          .append("nom", cleanData(columns[0]))
								                          .append("adresse", new Document()
										                                             .append("numero", cleanData(columns[6]))
										                                             .append("voie", cleanData(columns[7]))
										                                             .append("lieuDit", cleanData(columns[5]))
										                                             .append("codePostal", cleanData(columns[4]))
										                                             .append("commune", cleanData(columns[2])))
								                          .append("location", new Document()
										                                              .append("type", "Point")
										                                              .append("coordinates", Arrays.asList(Float.parseFloat(cleanData(columns[10])), Float.parseFloat(cleanData(columns[9])))))
								                          .append("multiCommune", parseMultiCommune(cleanData(columns[16])))
								                          .append("nbPlacesParking", parseIntV2(cleanData(columns[17])))
								                          .append("nbPlacesParkingHandicapes", parseIntV2(cleanData(columns[18])))
								                          .append("dateMiseAJourFiche", cleanData(columns[28]))
						);

					});
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		System.out.println("MAJ equipements");

		try (InputStream inputStream = CsvToMongoDb.class.getResourceAsStream("/batch/csv/equipements.csv");
		     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			reader.lines()
					.skip(1)
					.filter(line -> line.length() > 0)
					.map(line -> line.split(","))
					.forEach(columns -> {
						BasicDBObject query = new BasicDBObject("_id", cleanData(columns[2]));
						mongoCollection.findOneAndUpdate(query, new Document().append("$push", new Document().append("equipements", new Document()
								                                                                                                            .append("numero", cleanData(columns[4]))
								                                                                                                            .append("nom", cleanData(columns[3]))
								                                                                                                            .append("type", cleanData(columns[8]))
								                                                                                                            .append("famille", cleanData(columns[10])))
						), new FindOneAndUpdateOptions().upsert(false));

					});
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		Map<String, Set<String>> mapCorrespondanceData = new HashMap<>();

		System.out.println("Activite");

		try (InputStream inputStream = CsvToMongoDb.class.getResourceAsStream("/batch/csv/activites.csv");
		     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			reader.lines()
					.skip(1)
					.filter(line -> line.length() > 0)
					.map(line -> line.split(","))
					.forEach(columns -> {
						String equipmentId = cleanData(columns[2]);
						Document searchQuery = new Document("equipements", new Document("$elemMatch",new Document("numero",equipmentId)));
						String activite = cleanData(columns[5]);
						Document updateQuery = new Document("$addToSet", new Document("equipements.$.activites",activite));
						mongoCollection.updateOne(searchQuery, updateQuery);

					});
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

	}

	private static void createCollection(String name) {
		MongoCollection mongoCollection = mongoDatabase.getCollection(name);
		if (mongoCollection == null) {
			mongoDatabase.createCollection(name);
		}
	}

	private static String cleanData(String data) {
		return data.matches("\".*\"") ? data.substring(1, data.length() - 1).trim() : data.trim();
	}

	private static boolean parseMultiCommune(String s) {
		return s.equals("Oui");
	}

	private static int parseIntV2(String integer) {
		return integer.isEmpty() ? 0 : Integer.parseInt(integer);
	}
}
