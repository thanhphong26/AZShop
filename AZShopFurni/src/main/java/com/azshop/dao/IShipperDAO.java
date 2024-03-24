package com.azshop.dao;

import java.util.List;

import com.azshop.models.UserModel;

public interface IShipperDAO {
	List<UserModel> findAllShipper();

	void updateShipper(UserModel model);

	UserModel findOne(int id);

	void deleteShipper(int id);

	void insertShipper(UserModel model);
}
