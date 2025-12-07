package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static Connection connection;
	
	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/joymarket", "root", "");
			} catch (SQLException e) {
				System.out.println("Connection to Database Failed!");
				e.printStackTrace();
			}
		}
		return connection;
	}
}
