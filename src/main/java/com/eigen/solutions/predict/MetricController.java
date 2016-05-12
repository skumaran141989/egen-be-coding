package com.eigen.solutions.predict;

import java.util.List;

import javax.validation.Valid;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eigen.solutions.data.Metric;
import com.eigen.solutions.mongo.connection.MorphiaConnection;
import com.eigen.solutions.predict.rules.OverWeightRule;
import com.eigen.solutions.predict.rules.UnderWeightRule;
import com.eigen.solutions.utility.BaseWeight;

@RestController
@RequestMapping(value = "/metric")
public class MetricController {

	private static Long id = 0L;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public void create(@RequestBody @Valid Metric input) {

		if (id == 0L) {
			id++;
			BaseWeight.setWeight(input.getValue());
		}

		MorphiaConnection.getConnection().save(input);

		UnderWeightRule underWeight = new UnderWeightRule(input);
		OverWeightRule overWeight = new OverWeightRule(input);

		RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine().build();

		rulesEngine.registerRule(underWeight);
		rulesEngine.registerRule(overWeight);

		rulesEngine.fireRules();

	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public List<Metric> read() {

		List<Metric> list = MorphiaConnection.getConnection().createQuery(Metric.class).order("timeStamp").asList();

		return list;

	}

	@RequestMapping(value = "/readWithin", method = RequestMethod.GET)
	public List<Metric> read(@RequestParam(value = "start") Long start, @RequestParam(value = "end") Long end) {

		Query<Metric> query = MorphiaConnection.getConnection().createQuery(Metric.class);
		query.and(query.criteria("timeStamp").greaterThanOrEq(start), query.criteria("timeStamp").lessThanOrEq(end));
		List<Metric> list = query.order("timeStamp").asList();

		return list;

	}
}
