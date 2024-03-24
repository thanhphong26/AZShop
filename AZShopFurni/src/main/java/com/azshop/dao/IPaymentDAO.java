package com.azshop.dao;

import java.util.List;

import com.azshop.models.PaymentModel;

public interface IPaymentDAO {
	List<PaymentModel> findAllPayment();
	PaymentModel findPaymentByID (int orderID);
	boolean insertPayment(PaymentModel pay);
	boolean updatePayment(PaymentModel pay);
	boolean deletePayment(int orderID);
}
