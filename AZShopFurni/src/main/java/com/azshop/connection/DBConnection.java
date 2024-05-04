package com.azshop.connection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
	private static String DB_URL = "jdbc:mysql://localhost:3306/azshop";
	private static String USER_NAME = "root";
	private static String PASSWORD = "15242635";

	private static Connection con;
	
	public static Connection getConnection() throws IOException {
		con = null;
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			con = (Connection) DriverManager.getConnection(DB_URL,USER_NAME,PASSWORD);
		} catch (Exception ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
		return (con);
	}
}

