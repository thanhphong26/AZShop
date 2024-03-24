package com.azshop.dao;

import java.util.List;

import com.azshop.models.CartModel;

public interface ICartDAO {
	void insert(CartModel model);

	void update(CartModel model);

	void delete(int customerID, int itemID);

	void deleteAllByCustomerID(int customerID);

	CartModel findOne(int customerID, int itemID);

	List<CartModel> findAll();

	List<CartModel> findByCustomerId(int customerId);

}