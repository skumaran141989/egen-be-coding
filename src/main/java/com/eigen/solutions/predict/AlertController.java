package com.eigen.solutions.predict;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eigen.solutions.data.Alerts;
import com.eigen.solutions.mongo.connection.MorphiaConnection;

@RestController
@RequestMapping(value = "/alert")
public class AlertController {

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Alerts> read() throws UnknownHostException {

		List<Alerts> list = MorphiaConnection.getConnection().createQuery(Alerts.class).asList();

		return list;

	}

	@RequestMapping(value = "/readWithin", method = RequestMethod.GET)
	public List<Alerts> read(@RequestParam(value = "start") Long start, @RequestParam(value = "end") Long end) {

		Query<Alerts> query = MorphiaConnection.getConnection().createQuery(Alerts.class);
		query.and(query.criteria("timeStamp").greaterThanOrEq(start), query.criteria("timeStamp").lessThanOrEq(end));
		List<Alerts> list = query.asList();

		return list;

	}
}
