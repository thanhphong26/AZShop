package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.ICartDAO;
import com.azshop.models.CartModel;

public class CartDAOImpl implements ICartDAO {
	Connection conn = null;

	@Override
	public void insert(CartModel model) {
		String sql = "Insert into CART (CustomerID, ItemID, Quantity) values (?,?,?)";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, model.getCustomerID());
			ps.setInt(2, model.getItemID());
			ps.setInt(3, model.getQuantity());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(CartModel model) {
		String sql = "Update CART set Quantity = ? where CustomerID=? and ItemID=?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, model.getQuantity());
			ps.setInt(2, model.getCustomerID());
			ps.setInt(3, model.getItemID());

			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int customerID, int itemID) {
		String sql = "Delete from CART where CustomerID=? and ItemID=?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			ps.setInt(2, itemID);
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAllByCustomerID(int customerID) {
		String sql = "Delete from CART where CustomerID=?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public CartModel findOne(int customerID, int itemID) {
		CartModel cart = new CartModel();
		String sql = "Select * from CART where CustomerID=? and ItemID=?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, customerID);
			ps.setInt(2, itemID);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cart.setCustomerID(rs.getInt("CustomerID"));
				cart.setItemID(rs.getInt("ItemID"));
				cart.setQuantity(rs.getInt("Quantity"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cart;
	}

	@Override
	public List<CartModel> findAll() {
		List<CartModel> listCart = new ArrayList<CartModel>();
		String sql = "SELECT\r\n" + "    c.*,\r\n" + "    i.Color,\r\n" + "    i.Size,\r\n"
				+ "    i.PromotionPrice,\r\n" + "    p.productID,\r\n" + "    p.ProductName,\r\n"
				+ "    i.PromotionPrice * c.Quantity AS TotalPrice,\r\n" + "    ii.Image\r\n" + "FROM\r\n"
				+ "    CART c\r\n" + "JOIN\r\n" + "    ITEM i ON c.ItemID = i.ItemID\r\n" + "JOIN\r\n"
				+ "    PRODUCT p ON i.ProductID = p.ProductID\r\n" + "JOIN\r\n" + "    (\r\n" + "        SELECT\r\n"
				+ "            ItemID,\r\n" + "            Image,\r\n"
				+ "            ROW_NUMBER() OVER (PARTITION BY ItemID ORDER BY ItemImageID) AS ImageRank\r\n"
				+ "        FROM\r\n" + "            ITEMIMAGE\r\n"
				+ "    ) ii ON i.ItemID = ii.ItemID AND ii.ImageRank = 1;\r\n" + "";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CartModel cart = new CartModel();
				cart.setCustomerID(rs.getInt("CustomerID"));
				cart.setItemID(rs.getInt("ItemID"));
				cart.setQuantity(rs.getInt("Quantity"));
				cart.setColor(rs.getString("Color"));
				cart.setSize(rs.getString("Size"));
				cart.setPromotionPrice(rs.getInt("PromotionPrice"));
				cart.setProductName(rs.getString("ProductName"));
				cart.setTotalPrice(rs.getInt("TotalPrice"));
				cart.setImage(rs.getString("Image"));
				cart.setProductID(rs.getInt("ProductID"));
				listCart.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCart;
	}

	public List<CartModel> findByCustomerId(int customerId) {
		List<CartModel> listCart = new ArrayList<CartModel>();
		String sql = "SELECT\r\n" + "    c.*,\r\n" + "    i.Color,\r\n" + "    i.Size,\r\n" + "    CASE\r\n"
				+ "        WHEN i.PromotionPrice != 0 THEN i.PromotionPrice\r\n" + "        ELSE i.OriginalPrice\r\n"
				+ "    END AS PromotionPrice,\r\n" + "    p.productID,\r\n" + "    p.ProductName,\r\n"
				+ "    (CASE WHEN i.PromotionPrice != 0 THEN i.PromotionPrice ELSE i.OriginalPrice END) * c.Quantity AS TotalPrice,\r\n"
				+ "    ii.Image\r\n" + "FROM\r\n" + "    CART c\r\n" + "JOIN\r\n"
				+ "    ITEM i ON c.ItemID = i.ItemID\r\n" + "JOIN\r\n"
				+ "    PRODUCT p ON i.ProductID = p.ProductID\r\n" + "JOIN\r\n" + "    (\r\n" + "        SELECT\r\n"
				+ "            ItemID,\r\n" + "            Image,\r\n"
				+ "            ROW_NUMBER() OVER (PARTITION BY ItemID ORDER BY ItemImageID) AS ImageRank\r\n"
				+ "        FROM\r\n" + "            ITEMIMAGE\r\n"
				+ "    ) ii ON i.ItemID = ii.ItemID AND ii.ImageRank = 1\r\n" + "WHERE\r\n"
				+ "    c.CustomerID = ?;\r\n" + "";

		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, customerId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				CartModel cart = new CartModel();
				cart.setCustomerID(rs.getInt("CustomerID"));
				cart.setItemID(rs.getInt("ItemID"));
				cart.setQuantity(rs.getInt("Quantity"));
				cart.setColor(rs.getString("Color"));
				cart.setSize(rs.getString("Size"));
				cart.setPromotionPrice(rs.getInt("PromotionPrice"));
				cart.setProductName(rs.getString("ProductName"));
				cart.setTotalPrice(rs.getInt("TotalPrice"));
				cart.setImage(rs.getString("Image"));
				cart.setProductID(rs.getInt("ProductID"));
				listCart.add(cart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listCart;
	}
}