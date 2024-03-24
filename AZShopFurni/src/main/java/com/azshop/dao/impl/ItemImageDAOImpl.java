package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.IItemImageDAO;
import com.azshop.dao.IProductDAO;
import com.azshop.models.ItemImageModel;
import com.azshop.models.ProductModel;

public class ItemImageDAOImpl implements IItemImageDAO {
	Connection conn = null;
	
	@Override
	public List<ItemImageModel> findByProductID(int id) {
		
		String sql = "Select * from ITEMIMAGE join ITEM on ITEMIMAGE.ItemID = ITEM.ItemID where ITEM.ItemID=?";
		List<ItemImageModel> list = new ArrayList<ItemImageModel>();
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ItemImageModel model = new ItemImageModel();
				model.setItemimageID(rs.getInt("ItemImageID"));
				model.setItemID(rs.getInt("ItemID"));
				model.setImage(rs.getString("Image"));
				list.add(model);
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		IItemImageDAO itemImageDAO = new ItemImageDAOImpl();
		List<ItemImageModel> model = itemImageDAO.findByProductID(101004);
	}
    
    @Override
	public void insertItemImage(ItemImageModel model) {
		String sql = "Insert into ITEMIMAGE values (?,?,?)";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,model.getItemimageID());
			ps.setInt(2, model.getItemID());
			ps.setString(3, model.getImage());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteItemImage(int IteamId) {
		String sql = "Delete from ITEMIMAGE where ItemID = ?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, IteamId);
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateItemImage(ItemImageModel model) {
		String sql = "Update ITEMIMAGE Set Image = ? where ItemImageID = ?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, model.getImage());
			ps.setString(3, model.getImage());
			ps.executeUpdate();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
