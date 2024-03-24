package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IRatingDAO;
import com.azshop.dao.impl.RatingDAOImpl;
import com.azshop.models.submodels.RatingModel;
import com.azshop.service.IRatingService;

public class RatingServiceImpl implements IRatingService{
	IRatingDAO ratingDAO = new RatingDAOImpl();

	@Override
	public List<RatingModel> findRatinglByProductID(int productID) {
		return ratingDAO.findRatinglByProductID(productID);
	}

}
