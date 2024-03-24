package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IShipperDAO;
import com.azshop.dao.impl.ShipperDAOImpl;
import com.azshop.models.UserModel;
import com.azshop.service.IShipperService;

public class ShipperServiceImpl implements IShipperService {

	IShipperDAO shipperDAO = new ShipperDAOImpl();

	@Override
	public List<UserModel> findAllShipper() {
		return shipperDAO.findAllShipper();
	}

	@Override
	public void updateShipper(UserModel model) {
		shipperDAO.updateShipper(model);

	}

	@Override
	public UserModel findOne(int id) {
		return shipperDAO.findOne(id);
	}

	@Override
	public void deleteShipper(int id) {
		shipperDAO.deleteShipper(id);

	}

	@Override
	public void insertShipper(UserModel model) {
		shipperDAO.insertShipper(model);

	}

	@Override
	public int createShipperID() {
		List<UserModel> listShipper = shipperDAO.findAllShipper();
		int id = listShipper.get(listShipper.size() - 1).getUserID();
		return id + 1;
	}

}
