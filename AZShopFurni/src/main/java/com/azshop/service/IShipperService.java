package com.azshop.service;

import java.util.List;

import com.azshop.models.UserModel;

public interface IShipperService {
	List<UserModel> findAllShipper();

	void updateShipper(UserModel model);

	UserModel findOne(int id);

	void deleteShipper(int id);

	void insertShipper(UserModel model);

	int createShipperID();
}
