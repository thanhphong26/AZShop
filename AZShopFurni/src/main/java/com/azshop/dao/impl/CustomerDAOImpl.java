package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.ICustomerDAO;
import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;

public class CustomerDAOImpl implements ICustomerDAO {

	@Override
	public List<UserModel> getAllCustomer() {
		String sql = "SELECT * FROM USER WHERE Type=0";
		List<UserModel> listCustomer = new ArrayList<>();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserModel customer = new UserModel();
				customer.setUserID(rs.getInt(1));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setAddress(rs.getString(4));
				customer.setGender(rs.getInt(5));
				customer.setPhone(rs.getString(6));
				customer.setDob(rs.getDate(7));
				customer.setCid(rs.getString(8));
				customer.setAvatar(rs.getString(9));
				customer.setType(rs.getInt(10));
				customer.setKpi(rs.getInt(11));
				customer.setArea(rs.getString(12));
				customer.setEmail(rs.getString(13));
				listCustomer.add(customer);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listCustomer;
	}

	@Override
	public UserModel getOneCustomer(int id) {
		String sql = "SELECT * FROM USER WHERE UserID = ?";
		UserModel customer = new UserModel();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				customer.setUserID(rs.getInt(1));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setAddress(rs.getString(4));
				customer.setGender(rs.getInt(5));
				customer.setPhone(rs.getString(6));
				customer.setDob(rs.getDate(7));
				customer.setCid(rs.getString(8));
				customer.setAvatar(rs.getString(9));
				customer.setType(rs.getInt(10));
				customer.setKpi(rs.getInt(11));
				customer.setArea(rs.getString(12));
				customer.setEmail(rs.getString(13));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return customer;
	}

	@Override
	public boolean insertCustomer(UserModel customer) {
		String sql = "INSERT INTO USER VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ? , ?)";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customer.getUserID());
			ps.setString(2, customer.getFirstName());
			ps.setString(3, customer.getLastName());
			ps.setString(4, customer.getAddress());
			ps.setInt(5, customer.getGender());
			ps.setString(6, customer.getPhone());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(7, sdf.format(customer.getDob()));
			ps.setString(8, customer.getCid());
			ps.setString(9, customer.getAvatar());
			ps.setInt(10, customer.getType());
			ps.setInt(11,customer.getKpi());
			ps.setString(12, customer.getArea());
			ps.setString(13, customer.getEmail());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateCustomer(UserModel customer) {
		String sql = "UPDATE USER SET FirstName = ?, LastName = ?, Address = ?, Gender = ?, "
				+ "Phone = ?, DoB = ?, Cid = ?, Avatar = ? , Email = ? WHERE UserID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getFirstName());
			ps.setString(2, customer.getLastName());
			ps.setString(3, customer.getAddress());
			ps.setInt(4, customer.getGender());
			ps.setString(5, customer.getPhone());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(6, sdf.format(customer.getDob()));
			ps.setString(7, customer.getCid());
			ps.setString(8, customer.getAvatar());
			ps.setString(9, customer.getEmail());
			ps.setInt(10, customer.getUserID());
			ps.executeUpdate();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteCustomer(UserModel customerMd) {		
		String sql = "DELETE FROM `AZShop`.`USER` WHERE (`UserID` = ?)";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerMd.getUserID());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

}
