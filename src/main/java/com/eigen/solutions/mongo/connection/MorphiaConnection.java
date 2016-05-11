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
			ds = morphia.createDatastore(new MongoClient("127.0.0.1:27017"), "weightPredict");

		} catch (UnknownHostException ex) {
			System.out.println("Unable to Connect!");
		}
		return ds;
	}
}
