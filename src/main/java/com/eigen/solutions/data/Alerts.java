package com.eigen.solutions.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "alerts", noClassnameStored = true)
public class Alerts {
	@Id
	private ObjectId id;
	private Long timeStamp;
	private Boolean underWeight;
	private Boolean overWeight;

	public Long getTimeStamp() {
		return timeStamp;
	}

	public Boolean getUnderWeight() {
		return underWeight;
	}

	public Boolean getOverWeight() {
		return overWeight;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setUnderWeight(Boolean underWeight) {
		this.underWeight = underWeight;
	}

	public void setOverWeight(Boolean overWeight) {
		this.overWeight = overWeight;
	}

}