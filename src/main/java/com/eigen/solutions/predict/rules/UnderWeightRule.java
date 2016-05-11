package com.eigen.solutions.predict.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.eigen.solutions.data.Alerts;
import com.eigen.solutions.data.Metric;
import com.eigen.solutions.mongo.connection.MorphiaConnection;
import com.eigen.solutions.predict.BaseWeight;

@Rule(name = "UnderWeightRule", description = "Alert only weight drops 10%")
public class UnderWeightRule {
	private Metric metric;

	public UnderWeightRule(Metric metric) {
		this.metric = metric;
	}

	@Condition
	public boolean when() {

	return  metric.getValue() <= BaseWeight.getDecreasedBasesWeight();
	}

	@Action
	public void then() throws Exception {
		Alerts alert = new Alerts();
		alert.setTimeStamp(metric.getTimeStamp());
		alert.setOverWeight(false);
		alert.setUnderWeight(true);
		MorphiaConnection.getConnection().save(alert);

	}
}
