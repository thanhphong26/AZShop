package com.azshop.service;

import java.util.List;

import com.azshop.models.UserModel;

public interface ISellerService {
	List<UserModel> findAllSeller();
	void updateSeller(UserModel model);
	UserModel findOne(int id);
	void deleteSeller(int id);
	void insertSeller(UserModel model);
	int createSellerID();
	List<UserModel> findBestSeller();
}
