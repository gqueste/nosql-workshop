package nosql.workshop.batch.mongodb;

import com.mongodb.MongoClient;

public class LauncherBatch {

	static MongoClient mongoClient;

	public void run(){
	}

	public static void main(String[] args) {
		new LauncherBatch().run();
	}

	public static MongoClient getMongoClientInstance() {
		if(mongoClient==null){
			mongoClient = new MongoClient("localhost", 27017);
		}
		return mongoClient;
	}
}
