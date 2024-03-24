package com.azshop.dao;

import java.util.List;

import com.azshop.models.UserModel;

public interface ISellerDAO {
	List<UserModel> findAllSeller();
	void updateSeller(UserModel model);
	UserModel findOne(int id);
	void deleteSeller(int id);
	void insertSeller(UserModel model);
	List<UserModel> findBestSeller();
}
