package com.eigen.solutions.predict.rules;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.eigen.solutions.data.Alerts;
import com.eigen.solutions.data.Metric;
import com.eigen.solutions.mongo.connection.MorphiaConnection;
import com.eigen.solutions.predict.BaseWeight;

@Rule(name = "OverWeightRule", description = "Alert only weight shoots 10%")
public class OverWeightRule {

	private Metric metric;

	public OverWeightRule(Metric metric) {
		this.metric = metric;
	}

	@Condition
	public boolean when() {
         System.out.println(BaseWeight.getIncreasedBasesWeight()+"::"+metric.getValue());
		return  metric.getValue() >= BaseWeight.getIncreasedBasesWeight();
			
	}

	@Action
	public void then() throws Exception {
		 System.out.println("overweight");
		Alerts alert = new Alerts();
		alert.setTimeStamp(metric.getTimeStamp());
		alert.setOverWeight(true);
		alert.setUnderWeight(false);
		MorphiaConnection.getConnection().save(alert);

	}
}
