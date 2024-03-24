package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.IShipperDAO;
import com.azshop.models.UserModel;

public class ShipperDAOImpl implements IShipperDAO {

	@Override
	public List<UserModel> findAllShipper() {
		Connection conn = null;
		String sql = "Select UserID, FirstName, LastName, Address, Gender, Phone, DoB, CID, Avatar, Area, Email from AZShop.USER where Type=2";
		List<UserModel> listShipper = new ArrayList<UserModel>();
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				UserModel shipper = new UserModel();

				shipper.setUserID(rs.getInt("UserID"));
				shipper.setFirstName(rs.getString("FirstName"));
				shipper.setLastName(rs.getString("LastName"));
				shipper.setAddress(rs.getString("Address"));
				shipper.setGender(rs.getInt("Gender"));
				shipper.setPhone(rs.getString("Phone"));
				shipper.setDob(rs.getDate("DoB"));
				shipper.setCid(rs.getString("CID"));
				shipper.setAvatar(rs.getString("Avatar"));
				shipper.setArea(rs.getString("Area"));
				shipper.setEmail(rs.getString("Email"));

				listShipper.add(shipper);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listShipper;
	}

	@Override
	public void updateShipper(UserModel model) {
		Connection conn = null;
		String sql = "Update AZShop.USER set FirstName=?, LastName=?, Address=?, Gender=?, Phone=?, DoB=?, CID=?, Avatar=?, Area=?, Email=? where UserID=?";
		try {
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			// gan gia tri tham so
			ps.setString(1, model.getFirstName());
			ps.setString(2, model.getLastName());
			ps.setString(3, model.getAddress());
			ps.setInt(4, model.getGender());
			ps.setString(5, model.getPhone());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(6, sdf.format(model.getDob()));
			ps.setString(7, model.getCid());
			ps.setString(8, model.getAvatar());
			ps.setString(9, model.getArea());
			ps.setString(10, model.getEmail());
			ps.setInt(11, model.getUserID());

			ps.executeUpdate();// thuc thi cau query va tra ve Resultset
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public UserModel findOne(int id) {
		Connection conn = null;
		String sql = "Select * from AZShop.USER where UserID=?";
		UserModel shipper = new UserModel();

		try {
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				shipper.setUserID(rs.getInt("UserID"));
				shipper.setFirstName(rs.getString("FirstName"));
				shipper.setLastName(rs.getString("LastName"));
				shipper.setAddress(rs.getString("Address"));
				shipper.setGender(rs.getInt("Gender"));
				shipper.setPhone(rs.getString("Phone"));
				shipper.setDob(rs.getDate("DoB"));
				shipper.setCid(rs.getString("CID"));
				shipper.setAvatar(rs.getString("Avatar"));
				shipper.setArea(rs.getString("Area"));
				shipper.setEmail(rs.getString("Email"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return shipper;
	}

	@Override
	public void deleteShipper(int id) {
		Connection conn = null;
		String sql = "DELETE from AZShop.USER where UserID=?";
		try {
			conn = DBConnection.getConnection();// ket noi csdl
			PreparedStatement ps = conn.prepareStatement(sql);// nem cau lenh sql bang phat bieu prepare
			// gan gia tri tham so
			ps.setInt(1, id);

			ps.executeUpdate();// thuc thi cau query va tra ve Resultset
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void insertShipper(UserModel model) {
		Connection conn = null;
		String sql = "Insert into AZShop.USER(UserID, FirstName, LastName, Address, Gender, Phone, DoB, CID, Avatar, Area, Email, Type) Values (?,?,?,?,?,?,?,?,?,?,?,2)";
		try {
			conn = DBConnection.getConnection();// ket noi csdl
			PreparedStatement ps = conn.prepareStatement(sql);// nem cau lenh sql bang phat bieu prepare
			// gan gia tri tham so

			ps.setInt(1, model.getUserID());
			ps.setString(2, model.getFirstName());
			ps.setString(3, model.getLastName());
			ps.setString(4, model.getAddress());
			ps.setInt(5, model.getGender());
			ps.setString(6, model.getPhone());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ps.setString(7, sdf.format(model.getDob()));
			ps.setString(8, model.getCid());
			ps.setString(9, model.getAvatar());
			ps.setString(10, model.getArea());
			ps.setString(11, model.getEmail());

			ps.executeUpdate();// thuc thi cau query va tra ve Resultset
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
