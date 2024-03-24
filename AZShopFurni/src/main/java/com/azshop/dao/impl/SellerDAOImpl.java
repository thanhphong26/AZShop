package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.ISellerDAO;
import com.azshop.models.UserModel;

public class SellerDAOImpl implements ISellerDAO {

	@Override
	public List<UserModel> findAllSeller() {
		Connection conn = null;
		String sql = "Select UserID, FirstName, LastName, Address, Gender, Phone, DoB, CID, Avatar, KPI, Email from AZShop.USER where Type=1";
		List<UserModel> listSeller = new ArrayList<UserModel>();
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				UserModel seller = new UserModel();

				seller.setUserID(rs.getInt("UserID"));
				seller.setFirstName(rs.getString("FirstName"));
				seller.setLastName(rs.getString("LastName"));
				seller.setAddress(rs.getString("Address"));
				seller.setGender(rs.getInt("Gender"));
				seller.setPhone(rs.getString("Phone"));
				seller.setDob(rs.getDate("DoB"));
				seller.setCid(rs.getString("CID"));
				seller.setAvatar(rs.getString("Avatar"));
				seller.setKpi(rs.getInt("KPI"));
				seller.setEmail(rs.getString("Email"));

				listSeller.add(seller);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listSeller;
	}

	@Override
	public void updateSeller(UserModel model) {
		Connection conn = null;
		String sql = "Update AZShop.USER set FirstName=?, LastName=?, Address=?, Gender=?, Phone=?, DoB=?, CID=?, Avatar=?, KPI=?, Email=? where UserID=?";
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
			ps.setInt(9, model.getKpi());
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
		UserModel seller = new UserModel();

		try {
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				seller.setUserID(rs.getInt("UserID"));
				seller.setFirstName(rs.getString("FirstName"));
				seller.setLastName(rs.getString("LastName"));
				seller.setAddress(rs.getString("Address"));
				seller.setGender(rs.getInt("Gender"));
				seller.setPhone(rs.getString("Phone"));
				seller.setDob(rs.getDate("DoB"));
				seller.setCid(rs.getString("CID"));
				seller.setAvatar(rs.getString("Avatar"));
				seller.setKpi(rs.getInt("KPI"));
				seller.setEmail(rs.getString("Email"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return seller;
	}

	@Override
	public void deleteSeller(int id) {
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
	public void insertSeller(UserModel model) {
		Connection conn = null;
		String sql = "Insert into AZShop.USER(UserID, FirstName, LastName, Address, Gender, Phone, DoB, CID, Avatar, KPI, Email, Type) Values (?,?,?,?,?,?,?,?,?,?,?,1)";
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
			ps.setInt(10, model.getKpi());
			ps.setString(11, model.getEmail());

			ps.executeUpdate();// thuc thi cau query va tra ve Resultset
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<UserModel> findBestSeller() {
		Connection conn = null;
		String sql = "Select AZShop.USER.*, count(AZShop.DETAIL.ItemID) as SL\r\n"
				+ "from AZShop.USER \r\n"
				+ "join AZShop.ORDER on AZShop.USER.UserID = AZShop.ORDER.SellerID\r\n"
				+ "join AZShop.DETAIL on AZShop.ORDER.OrderID = AZShop.DETAIL.OrderID\r\n"
				+ "where AZShop.USER.Type=1 AND AZShop.ORDER.Status=4\r\n"
				+ "GROUP BY AZShop.USER.UserID\r\n"
				+ "ORDER BY SL DESC LIMIT 10;";
		List<UserModel> listSeller = new ArrayList<UserModel>();
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				UserModel seller = new UserModel();

				seller.setUserID(rs.getInt("UserID"));
				seller.setFirstName(rs.getString("FirstName"));
				seller.setLastName(rs.getString("LastName"));
				seller.setAddress(rs.getString("Address"));
				seller.setGender(rs.getInt("Gender"));
				seller.setPhone(rs.getString("Phone"));
				seller.setDob(rs.getDate("DoB"));
				seller.setCid(rs.getString("CID"));
				seller.setAvatar(rs.getString("Avatar"));
				seller.setKpi(rs.getInt("KPI"));
				seller.setEmail(rs.getString("Email"));

				listSeller.add(seller);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listSeller;
	}

}
