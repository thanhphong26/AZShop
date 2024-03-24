package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.IPaymentDAO;
import com.azshop.models.OrderModel;
import com.azshop.models.PaymentModel;

public class PaymentDAOImpl implements IPaymentDAO {

	@Override
	public List<PaymentModel> findAllPayment() {
		List<PaymentModel> listPayment = new ArrayList<PaymentModel>();
		String sql = "SELECT * FROM AZShop.PAYMENT";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				PaymentModel payment = new PaymentModel();
				payment.setOrderID(rs.getInt("OrderID"));
				payment.setMethod(rs.getInt("Method"));
				payment.setTime(rs.getTimestamp("Time"));
				payment.setBank(rs.getString("Bank"));
				payment.setCardOwner(rs.getString("CardOwner"));
				payment.setAccountNumber(rs.getString("AccountNumber"));
				payment.setStatus(rs.getInt("Status"));
				listPayment.add(payment);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listPayment;
	}

	@Override
	public PaymentModel findPaymentByID(int orderID) {
		PaymentModel payment = new PaymentModel();
		String sql = "SELECT * FROM AZShop.PAYMENT WHERE orderID = ? ";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				payment.setOrderID(rs.getInt("OrderID"));
				payment.setMethod(rs.getInt("Method"));
				payment.setTime(rs.getTimestamp("Time"));
				payment.setBank(rs.getString("Bank"));
				payment.setCardOwner(rs.getString("CardOwner"));
				payment.setAccountNumber(rs.getString("AccountNumber"));
				payment.setStatus(rs.getInt("Status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payment;
	}

	@Override
	public boolean updatePayment(PaymentModel pay) {
		String sql = "UPDATE `AZShop`.`PAYMENT` SET `Method` = ? , `Time` = ? , `Bank` = ? , `CardOwner` = ? , `AccountNumber` = ? , `Status` = ? WHERE (`OrderID` = ? )";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, pay.getMethod());
			ps.setTimestamp(2, pay.getTime());
			ps.setString(3, pay.getBank());
			ps.setString(4, pay.getCardOwner());
			ps.setString(5, pay.getAccountNumber());
			ps.setInt(6, pay.getStatus());
			ps.setInt(7, pay.getOrderID());
			ps.executeUpdate();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insertPayment(PaymentModel pay) {
		String sql = "INSERT INTO `AZShop`.`PAYMENT` (`OrderID`, `Method`, `Time`, `Bank`, `CardOwner`, `AccountNumber`, `Status`) VALUES ( ? , ? , ? , ? , ? , ? , ?)";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, pay.getOrderID());
			ps.setInt(2, pay.getMethod());
			ps.setTimestamp(3, pay.getTime());
			ps.setString(4, pay.getBank());
			ps.setString(5, pay.getCardOwner());
			ps.setString(6, pay.getAccountNumber());
			ps.setInt(7, pay.getStatus());
			 ps.executeUpdate();
			conn.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePayment(int orderID) {
		String sql = "DELETE FROM `AZShop`.`PAYMENT` WHERE (`OrderID` = ? );";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderID);
			ps.executeUpdate();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
