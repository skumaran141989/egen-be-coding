package com.eigen.solutions.mongo.connection;

import java.net.UnknownHostException;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class MorphiaConnection {

	public static Datastore getConnection() {
		Datastore ds = null;
		try {
			Morphia morphia = new Morphia();
			morphia.map(com.eigen.solutions.data.Metric.class);
			String URI = System.getProperty("MONGO_URI", "127.0.0.1:27017");
			String dbName = System.getProperty("DB_NAME", "weightPredict");
			ds = morphia.createDatastore(new MongoClient(URI), dbName);

		} catch (UnknownHostException ex) {
			System.out.println("Unable to Connect!");
		}
		return ds;
	}

}
