package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.azshop.dao.IDetailDAO;
import com.azshop.models.DetailModel;

import com.azshop.connection.DBConnection;

public class DetailDAOImpl implements IDetailDAO {

	@Override
	public List<DetailModel> findDetailByProductID(int productID) {
		String sql = "SELECT\r\n" + "    d.ItemID,\r\n" + "    d.Content,\r\n" + "    d.Rating,\r\n"
				+ "    d.EvaluationDate,\r\n" + "    u.Avatar,\r\n"
				+ "    CONCAT(u.FirstName, ' ', u.LastName) AS Name\r\n" + "FROM\r\n" + "    DETAIL d\r\n" + "JOIN\r\n"
				+ "    AZShop.ORDER o ON d.OrderID = o.OrderID\r\n" + "JOIN\r\n"
				+ "    USER u ON u.UserID = o.CustomerID\r\n" + "JOIN ITEM i ON i.ItemID = d.ItemID\r\n"
				+ "WHERE i.ProductID=?;";
		List<DetailModel> listDetail = new ArrayList<>();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DetailModel Detail = new DetailModel();
				Detail.setAvatar(rs.getString("Avatar"));
				Detail.setContent(rs.getString("Content"));
				Detail.setEvaluationDate(rs.getDate("EvaluationDate"));
				Detail.setItemID(rs.getInt("ItemID"));
				Detail.setRating(rs.getInt("Rating"));
				Detail.setName(rs.getString("Name"));
				listDetail.add(Detail);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listDetail;
	}

	@Override
	public void updateDetail(DetailModel detail) {
		String sql = "UPDATE `DETAIL` SET Rating = ?, Content = ?, EvaluationDate = ? WHERE ItemID = ? AND OrderID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, detail.getRating());
			ps.setString(2, detail.getContent());
			ps.setDate(3, new java.sql.Date(detail.getEvaluationDate().getTime()));
			ps.setInt(4, detail.getItemID());
			ps.setInt(5, detail.getOrderID());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<DetailModel> listDetail(int orderID) {
		List<DetailModel> listDetail = new ArrayList<DetailModel>();
		String sql =  "SELECT  P.ProductID,P.Description, I.ItemID, O.OrderID, P.ProductName, I.Color, I.Size, D.Quantity, I.OriginalPrice, I.PromotionPrice, IM.Image\r\n"
					+ "FROM PRODUCT AS P \r\n"
					+ "			INNER JOIN ITEM I ON P.ProductID = I.ProductID \r\n"
					+ "			INNER JOIN DETAIL D on I.ItemID = D.ItemID\r\n"
					+ "			INNER JOIN `ORDER` O on O.OrderID = D.OrderID\r\n"
					+ "    		INNER JOIN (SELECT MIN(II.ItemImageID) AS ItemImageID, II.ItemID, MIN(II.Image) AS Image\r\n"
					+ "						FROM ITEMIMAGE II, ITEM IT\r\n"
					+ "						WHERE II.ItemID = IT.ItemID\r\n"
					+ "						GROUP BY II.ItemID) IM ON IM.ItemID = I.ItemID \r\n"
					+ "WHERE O.OrderID = ?";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				DetailModel detail = new DetailModel();
				detail.getProduct().setProductID(rs.getInt(1));
				detail.getProduct().setDescription(rs.getString(2));
				detail.setItemID(rs.getInt(3));
				detail.setOrderID(rs.getInt(4));
				detail.getProduct().setProductName(rs.getString(5));
				detail.getItem().setColor(rs.getString(6));
				detail.getItem().setSize(rs.getString(7));
				detail.setQuantity(rs.getInt(8));
				detail.getItem().setOriginalPrice(rs.getInt(9));
				detail.getItem().setPromotionPrice(rs.getInt(10));
				detail.getItem().setImage(rs.getString(11));
				
				listDetail.add(detail);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listDetail;
	}

	@Override
	public DetailModel findDetailByItemID(int orderID, int itemID) {
		String sql = "SELECT  P.ProductID, P.Description, I.ItemID, O.OrderID, P.ProductName, I.Color, I.Size, D.Quantity, I.OriginalPrice, I.PromotionPrice, IM.Image \r\n"
				+ "FROM PRODUCT AS P  INNER JOIN ITEM I ON P.ProductID = I.ProductID \r\n"
				+ "				   INNER JOIN DETAIL D on I.ItemID = D.ItemID \r\n"
				+ "				   INNER JOIN `ORDER` O on O.OrderID = D.OrderID \r\n"
				+ "				   INNER JOIN (SELECT MIN(II.ItemImageID) AS ItemImageID, II.ItemID, MIN(II.Image) AS Image \r\n"
				+ "								FROM ITEMIMAGE II, ITEM IT WHERE II.ItemID = IT.ItemID \r\n"
				+ "								GROUP BY II.ItemID) IM ON IM.ItemID = I.ItemID \r\n"
				+ "WHERE O.OrderID = ? and I.ItemID = ? ";
		DetailModel detail = new DetailModel();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderID);
			ps.setInt(2, itemID);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				detail.getProduct().setProductID(rs.getInt(1));
				detail.getProduct().setDescription(rs.getString(2));
				detail.setItemID(rs.getInt(3));
				detail.setOrderID(rs.getInt(4));
				detail.getProduct().setProductName(rs.getString(5));
				detail.getItem().setColor(rs.getString(6));
				detail.getItem().setSize(rs.getString(7));
				detail.setQuantity(rs.getInt(8));
				detail.getItem().setOriginalPrice(rs.getInt(9));
				detail.getItem().setPromotionPrice(rs.getInt(10));
				detail.getItem().setImage(rs.getString(11));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return detail;
	}
	@Override
	public List<List<Object>> listBestSeller() {
		List<List<Object>> listBestSeller = new ArrayList<List<Object>>();
		String sql = "SELECT P.ProductID, I.ItemID, P.ProductName, P.Description, I.OriginalPrice, I.PromotionPrice, IM.Image \r\n"
				   + "FROM PRODUCT AS P \r\n"
				   + "			INNER JOIN ITEM I ON P.ProductID = I.ProductID \r\n"
				   + "			INNER JOIN (SELECT itemID AS ItemID, COUNT(ItemID) AS SL\r\n"
				   + "						FROM DETAIL GROUP BY itemID\r\n"
				   + "						ORDER BY SL DESC LIMIT 10) AS D on I.ItemID = D.ItemID \r\n"
				   + "			INNER JOIN (SELECT MIN(II.ItemImageID) AS ItemImageID, II.ItemID, MIN(II.Image) AS Image \r\n"
				   + "						FROM ITEMIMAGE II, ITEM IT WHERE II.ItemID = IT.ItemID \r\n"
			       + "						GROUP BY II.ItemID) IM ON IM.ItemID = I.ItemID";
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				List<Object> row = new ArrayList<Object>();
				row.add(rs.getInt("ProductID"));
				row.add(rs.getInt("ItemID"));
				row.add(rs.getString("ProductName"));
				row.add(rs.getString("Description"));
				row.add(rs.getInt("OriginalPrice"));
				row.add(rs.getInt("PromotionPrice"));
				row.add(rs.getString("Image"));
				
				listBestSeller.add(row);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return listBestSeller;
	}
}