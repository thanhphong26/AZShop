package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.ICartDAO;
import com.azshop.dao.impl.CartDAOImpl;

import com.azshop.models.CartModel;

import com.azshop.service.ICartService;

public class CartServiceImpl implements ICartService {

	ICartDAO cartDAO = new CartDAOImpl();

	@Override
	public void insert(CartModel model) {
		cartDAO.insert(model);

	}

	@Override
	public void update(CartModel model) {
		cartDAO.update(model);

	}

	@Override
	public void delete(int customerID, int itemID) {
		cartDAO.delete(customerID, itemID);

	}

	@Override
	public CartModel findOne(int customerID, int itemID) {
		return cartDAO.findOne(customerID, itemID);
	}

	@Override
	public List<CartModel> findAll() {
		return cartDAO.findAll();
	}

	@Override
	public void deleteAllByCustomerID(int customerID) {
		cartDAO.deleteAllByCustomerID(customerID);

	}

	@Override
	public List<CartModel> findByCustomerId(int customerId) {
		return cartDAO.findByCustomerId(customerId);
	}
}
