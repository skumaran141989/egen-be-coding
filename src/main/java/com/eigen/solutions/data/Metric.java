package com.eigen.solutions.data;


import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value = "metrics", noClassnameStored = true)
public class Metric {
	@Id
	private ObjectId id;
	private Long timeStamp;
	private Long value;
	
	public Long getTimeStamp() {
		return timeStamp;
	}

	public Long getValue() {
		return value;
	}

	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}