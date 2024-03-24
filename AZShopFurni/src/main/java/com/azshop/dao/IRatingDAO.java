package com.azshop.dao;

import java.util.List;

import com.azshop.models.submodels.RatingModel;

public interface IRatingDAO {

	List<RatingModel> findRatinglByProductID(int productID);

}