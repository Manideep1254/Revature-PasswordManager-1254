package com.revature1254.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

	private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // Oracle
																				// 10g
	private static final String USER = "c##usera";
	private static final String PASSWORD = "rpm1254";

	public static Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

