package com.azshop.service.impl;

import java.util.List;

import com.azshop.dao.IPaymentDAO;
import com.azshop.dao.impl.PaymentDAOImpl;
import com.azshop.models.PaymentModel;
import com.azshop.service.IPaymentService;

public class PaymentServiceImpl implements IPaymentService{
	IPaymentDAO paymentDAO = new PaymentDAOImpl();
	@Override
	public List<PaymentModel> findAllPayment() {
		// TODO Auto-generated method stub
		return paymentDAO.findAllPayment();
	}

	@Override
	public PaymentModel findPaymentByID(int orderID) {
		// TODO Auto-generated method stub
		return paymentDAO.findPaymentByID(orderID);
	}

	@Override
	public boolean insertPayment(PaymentModel pay) {
		// TODO Auto-generated method stub
		return paymentDAO.insertPayment(pay);
	}

	@Override
	public boolean updatePayment(PaymentModel pay) {
		// TODO Auto-generated method stub
		return paymentDAO.updatePayment(pay);
	}

	@Override
	public boolean deletePayment(int orderID) {
		// TODO Auto-generated method stub
		return paymentDAO.deletePayment(orderID);
	}

}
