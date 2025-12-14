package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				try {
					connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joymarket", "root", "");
					System.out.println("Database connected");
					
				} catch (SQLException e) {
					System.out.println("Get Connection Database Failed!");
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			System.out.println("Get Connection Database Failed!");
			e.printStackTrace();
		}
		return connection;
	}
}
