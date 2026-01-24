package com.bank.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	private static Connection conn;

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				String url = "jdbc:mysql://localhost:3306/bankmanagementsystem";
				String username = "root";
				String password = "2486";

				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(url, username, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
}