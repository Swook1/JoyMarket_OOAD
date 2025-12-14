package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDAO {
	
	public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("idUser"),
                        rs.getString("fullName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("role")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public boolean register(User user) {

	    String userSQL = "INSERT INTO users(fullName, email, password, phone, address, role) "
	                   + "VALUES (?, ?, ?, ?, ?, ?)";

	    String customerSQL = "INSERT INTO customers(idCustomer, balance) VALUES (?, 0)";
	    
	    String adminSQL = "INSERT INTO admins(idAdmin, emergencyContact) VALUES (?, ?)";
	    
	    String courierSQL = "INSERT INTO couriers(idCourier, vehicleType, vehiclePlate) VALUES (?, ?, ?)";

	    Connection con = null;

	    try {
	        con = DBConnection.getConnection();
	        con.setAutoCommit(false); // Start transaction

	        // Insert into USERS
	        PreparedStatement psUser = con.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS);

	        psUser.setString(1, user.getFullName());
	        psUser.setString(2, user.getEmail());
	        psUser.setString(3, user.getPassword());
	        psUser.setString(4, user.getPhone());
	        psUser.setString(5, user.getAddress());
	        psUser.setString(6, user.getRole());

	        int rows = psUser.executeUpdate();

	        if (rows == 0) {
	            con.rollback();
	            return false;
	        }

	        // Get generated idUser
	        ResultSet rs = psUser.getGeneratedKeys();
	        int generatedId = -1;

	        if (rs.next()) {
	            generatedId = rs.getInt(1);
	            user.setIdUser(generatedId);
	        } else {
	            con.rollback();
	            return false;
	        }

	        // If the role is customer → insert into CUSTOMERS table
	        if (user.getRole().equalsIgnoreCase("customer")) {

	            PreparedStatement psCustomer = con.prepareStatement(customerSQL);
	            psCustomer.setInt(1, generatedId);

	            int customerRows = psCustomer.executeUpdate();

	            if (customerRows == 0) {
	                con.rollback();
	                return false;
	            }
	        }
	        
	        // else if admin table
	        else if(user.getRole().equalsIgnoreCase("admin")) {
	        	PreparedStatement psAdmin = con.prepareStatement(adminSQL);
	        	psAdmin.setInt(1, generatedId);
	        	psAdmin.setString(2, "1432");
	        	
	        	int adminRows = psAdmin.executeUpdate();
	        	if (adminRows == 0) {
	                con.rollback();
	                return false;
	            }
	        }
	        
	        // else if courier table
	        
	        else if(user.getRole().equalsIgnoreCase("courier")) {
	        	PreparedStatement psCourier = con.prepareStatement(courierSQL);
	        	psCourier.setInt(1, generatedId);
	        	psCourier.setString(2, "Honda");
	        	psCourier.setString(3, "67");
	        	
	        	int adminRows = psCourier.executeUpdate();
	        	if (adminRows == 0) {
	                con.rollback();
	                return false;
	            }
	        }

	       
	        con.commit();
	        con.setAutoCommit(true);

	        return true;

	    } catch (Exception e) {
	        try {
	            if (con != null) con.rollback();  
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public boolean updateProfile(int idUser, String fullName, String phone, String address) {
	    String query = "UPDATE users SET fullName = ?, phone = ?, address = ? WHERE idUser = ?";
	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(query);
	        ps.setString(1, fullName);
	        ps.setString(2, phone);
	        ps.setString(3, address);
	        ps.setInt(4, idUser);

	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        System.out.println("Update profile failed!");
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean emailExist(String email) {
        String query = "SELECT idUser FROM users WHERE email = ?";
        
        try {
        	Connection con = DBConnection.getConnection();
        	PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Check email exist failed!");
            e.printStackTrace();
        }
        return false;
    }
}
