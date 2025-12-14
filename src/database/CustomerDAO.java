package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.*;

public class CustomerDAO {

	public Customer getCustomer(int id) {

	    String sql = "SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.balance " +
	    	    "FROM users u JOIN customers c ON u.idUser = c.idCustomer " +
	    	    "WHERE u.idUser = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	     

	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            System.out.println("Customer made");
	            return new Customer(
	                    rs.getInt("idUser"),
	                    rs.getString("fullName"),
	                    rs.getString("email"),
	                    rs.getString("password"),
	                    rs.getString("phone"),
	                    rs.getString("address"),
	                    rs.getString("role"),
	                    rs.getDouble("balance")
	            );
	        } else {
	            System.out.println("No customer found with id: " + id);
	        }

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public boolean topUpBalance(int id, double increaseBalance) {
		String query = "UPDATE customers SET balance = balance + ? WHERE idCustomer = ?";
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setDouble(1, increaseBalance);
			ps.setInt(2, id);
			
			int rowsAffected = ps.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			System.out.println("Top up user balance failed!");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean createCustomer(int idUser) {
	    String sql = "INSERT INTO customers (idCustomer, balance) VALUES (?, ?)";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, idUser);   
	        ps.setDouble(2, 0);     

	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        System.out.println("Create customer failed!");
	        e.printStackTrace();
	    }

	    return false;
	}


}
