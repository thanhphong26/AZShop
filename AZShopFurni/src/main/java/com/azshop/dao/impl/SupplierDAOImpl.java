package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.ISupplierDAO;
import com.azshop.models.SupplierModel;

public class SupplierDAOImpl implements ISupplierDAO {
	Connection conn = null;

	@Override
	public SupplierModel findOne(int id) {
		SupplierModel model = new SupplierModel();
		String sql = "Select * from SUPPLIER where SupplierID=?";
		try {
			new DBConnection();
			conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				model.setSupplierID(rs.getInt("SupplierID"));
				model.setSupplierName(rs.getString("SupplierName"));	
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	public static void main(String[] args) {
		ISupplierDAO supplierDAO = new SupplierDAOImpl();

		SupplierModel model = supplierDAO.findOne(101);
	}
    
    @Override
	public List<SupplierModel> findAll() {
		String sql = "SELECT * FROM SUPPLIER";
		List<SupplierModel> listSupplier = new ArrayList<>();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SupplierModel supplier = new SupplierModel();
				supplier.setSupplierID(rs.getInt(1));
				supplier.setSupplierName(rs.getString(2));
				listSupplier.add(supplier);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listSupplier;
	}
}
