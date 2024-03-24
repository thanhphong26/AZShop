package com.azshop.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.azshop.connection.DBConnection;
import com.azshop.dao.IRatingDAO;
import com.azshop.models.submodels.RatingModel;

public class RatingDAOImpl implements IRatingDAO {

	@Override
	public List<RatingModel> findRatinglByProductID(int productID) {
		String sql = "SELECT Rating, COUNT(*) AS RatingCount\r\n" + "FROM DETAIL d\r\n"
				+ "JOIN ITEM i ON i.ItemID = d.ItemID\r\n" + "WHERE Rating IN (1, 2, 3, 4, 5) and i.ProductID = ?\r\n"
				+ "GROUP BY Rating ORDER BY Rating DESC;";
		List<RatingModel> listRating = new ArrayList<>();
		try {
			new DBConnection();
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, productID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				RatingModel rating = new RatingModel();
				rating.setNumOfStar(rs.getInt("Rating"));
				rating.setNumOfRating(rs.getInt("RatingCount"));

				listRating.add(rating);
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return listRating;
	}

}
