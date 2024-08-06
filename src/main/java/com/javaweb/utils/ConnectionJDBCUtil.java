package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBCUtil {
	static final String DB_URL = "jdbc:mysql://localhost:3307/estatebasic";
	static final String USERNAME = "root";
	static final String PASSWORD = "admin";

	public static Connection getConnectionJdbc() {
		try {
			Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
