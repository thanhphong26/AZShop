package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.ISearchHistoryDAO;
import com.azshop.models.SearchHistoryModel;

public class SearchHistoryDAOImpl implements ISearchHistoryDAO{
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	@Override
	public List<SearchHistoryModel> getHistorySearchByCID(int customerID) {
		List<SearchHistoryModel> listHistorySearch=new ArrayList<SearchHistoryModel>();
		String sql="select * from SEARCHHISTORY where CustomerID=?";
		try {
			new DBConnection();
			conn=DBConnection.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, customerID);
			rs=ps.executeQuery();
			while(rs.next()) {
				SearchHistoryModel model=new SearchHistoryModel();
				model.setCustomerID(rs.getInt("customerID"));
				model.setCreatedAt(rs.getTimestamp("createdAt"));
				model.setHistory(rs.getString("history"));
				listHistorySearch.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listHistorySearch;
	}
	@Override
	public void insertSearchHistory(SearchHistoryModel model) {
		String sql="insert into SEARCHHISTORY(CustomerID, CreatedAt, History) values(?,?,?)";
		try {
			new DBConnection();
			conn=DBConnection.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, model.getCustomerID());
			ps.setTimestamp(2, new Timestamp(model.getCreatedAt().getTime()));
			ps.setString(3, model.getHistory());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
