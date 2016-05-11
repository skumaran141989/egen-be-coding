package com.eigen.solutions.predict;

public class BaseWeight {

	private static double baseWeightIncrease;
	private static double baseWeightDecrease;

	public static void setWeight(Long baseWeight) {
		baseWeightIncrease = (double) baseWeight + (baseWeight * 0.1);
		baseWeightDecrease = (double) baseWeight - (baseWeight * 0.1);
	}

	public static double getIncreasedBasesWeight() {
		return baseWeightIncrease;
	}

	public static double getDecreasedBasesWeight() {
		return baseWeightDecrease;
	}

}
