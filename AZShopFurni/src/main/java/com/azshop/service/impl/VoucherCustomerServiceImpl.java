package com.azshop.service.impl;
import com.azshop.dao.IVoucherCustomerDAO;
import com.azshop.dao.impl.VoucherCustomerDAOImpl;
import com.azshop.service.IVoucherCustomerService;

public class VoucherCustomerServiceImpl implements IVoucherCustomerService{
	IVoucherCustomerDAO voucherCustomerDAO = new VoucherCustomerDAOImpl();
	
	@Override
	public void insertVoucherCustomer(int voucherId, int customerId) {
		voucherCustomerDAO.insertVoucherCustomer(voucherId, customerId); 
	}
	
}
