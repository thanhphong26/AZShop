package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.IUserDAO;
import com.azshop.models.AccountModel;
import com.azshop.models.UserModel;

public class UserDAOImpl implements IUserDAO {

	@Override
	public UserModel getInfoUser(int userID) {
		UserModel user = new UserModel();
		String sql = "SELECT * FROM USER WHERE UserID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user.setUserID(rs.getInt("UserID"));
				user.setFirstName(rs.getString("FirstName"));
				user.setLastName(rs.getString("LastName"));
				user.setAddress(rs.getString("Address"));
				user.setGender(rs.getInt("Gender"));
				user.setPhone(rs.getString("Phone"));
				user.setDob(rs.getDate("Dob"));
				user.setCid(rs.getString("Cid"));
				user.setAvatar(rs.getString("Avatar"));
				user.setType(rs.getInt("Type"));
				user.setKpi(rs.getInt("KPI"));
				user.setArea(rs.getString("Area"));
				user.setEmail(rs.getString("Email"));
			}
			conn.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void updateUser(UserModel user) {
		String sql = "UPDATE USER SET FirstName = ?, LastName = ?, Address = ?, Gender = ?, "
					+ "Phone = ?, DoB = ?, Cid = ?, Avatar = ? WHERE UserID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getAddress());
			ps.setInt(4, user.getGender());
			ps.setString(5, user.getPhone());
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(6, formatter.format(user.getDob()));
			ps.setString(7, user.getCid());
			ps.setString(8, user.getAvatar());
			ps.setInt(9, user.getUserID());
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateAvatar(int userID, String avatar) {
		String sql = "UPDATE USER SET Avatar = ? WHERE UserID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, avatar);
			ps.setInt(2, userID);
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public AccountModel getInfAccount(int userID) {
		AccountModel account = new AccountModel();
		String sql = "SELECT * FROM ACCOUNT WHERE USERID= ? ";
		
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs= ps.executeQuery();
			while(rs.next())
			{				
				account.setUserID(rs.getInt("UserID"));
				account.setUserName(rs.getString("UserName"));
				account.setPassword(rs.getString("Password"));
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}		
		return account;
	}

	@Override
	public void updateAccount(AccountModel account) {
		String sql = "UPDATE ACCOUNT SET Password = ? WHERE UserID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getPassword());
			ps.setInt(2, account.getUserID());
			ps.executeUpdate();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
}
