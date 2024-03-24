package com.azshop.models.submodels;

public class RatingModel {
	private int numOfStar;
	private int numOfRating;

	public int getNumOfStar() {
		return numOfStar;
	}

	public void setNumOfStar(int numOfStar) {
		this.numOfStar = numOfStar;
	}

	public int getNumOfRating() {
		return numOfRating;
	}

	public void setNumOfRating(int numOfRating) {
		this.numOfRating = numOfRating;
	}

	public RatingModel(int numOfStar, int numOfRating) {
		super();
		this.numOfStar = numOfStar;
		this.numOfRating = numOfRating;
	}

	public RatingModel() {
		super();
	}
}
