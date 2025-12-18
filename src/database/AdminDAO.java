package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;

/*
  AdminDAO - Data Access Object untuk table admins
  Get admin data lengkap (join users + admins)
 */
public class AdminDAO {
	// Ambil data admin berdasarkan ID
	public Admin getAdmin(int id) {

	    String sql = "SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, a.emergencyContact " +
	    	    "FROM users u JOIN admins a ON u.idUser = a.idAdmin " +
	    	    "WHERE u.idUser = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	        
	        System.out.println("Connected to DB: " + con.getCatalog());
	        System.out.println("Finding admin ID: " + id);

	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return new Admin(
	                    rs.getInt("idUser"),
	                    rs.getString("fullName"),
	                    rs.getString("email"),
	                    rs.getString("password"),
	                    rs.getString("phone"),
	                    rs.getString("address"),
	                    rs.getString("role"),
	                    rs.getString("emergencyContact")
	            );
	        }

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
