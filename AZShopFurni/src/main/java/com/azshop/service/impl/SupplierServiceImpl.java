package com.azshop.service.impl;

import com.azshop.dao.ISupplierDAO;
import com.azshop.dao.impl.SupplierDAOImpl;
import com.azshop.models.SupplierModel;
import com.azshop.service.ISupplierService;

public class SupplierServiceImpl implements ISupplierService{
	ISupplierDAO supplierDAO = new SupplierDAOImpl();

	@Override
	public SupplierModel findOne(int id) {
		return supplierDAO.findOne(id);
	}

}