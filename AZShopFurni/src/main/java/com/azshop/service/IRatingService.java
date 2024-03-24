package com.azshop.service;

import java.util.List;

import com.azshop.models.submodels.RatingModel;

public interface IRatingService {
	List<RatingModel> findRatinglByProductID(int productID);

}
