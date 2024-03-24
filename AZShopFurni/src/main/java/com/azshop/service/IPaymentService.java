package com.azshop.service;

import java.util.List;

import com.azshop.models.PaymentModel;

public interface IPaymentService {
	List<PaymentModel> findAllPayment();
	PaymentModel findPaymentByID (int orderID);
	boolean insertPayment(PaymentModel pay);
	boolean updatePayment(PaymentModel pay);
	boolean deletePayment(int orderID);
}
