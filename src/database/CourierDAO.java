package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;

/*
  CourierDAO - Data Access Object untuk table couriers
  Get courier data lengkap (join users + couriers)
 */
public class CourierDAO {
	// Ambil data courier berdasarkan ID
	public Courier getCourier(int id) {

	    String sql = "SELECT u.idUser, u.fullName, u.email, u.password, u.phone, u.address, u.role, c.vehicleType, c.vehiclePlate " +
	    	    "FROM users u JOIN couriers c ON u.idUser = c.idCourier " +
	    	    "WHERE u.idUser = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, id);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            return new Courier(
	                    rs.getInt("idUser"),
	                    rs.getString("fullName"),
	                    rs.getString("email"),
	                    rs.getString("password"),
	                    rs.getString("phone"),
	                    rs.getString("address"),
	                    rs.getString("role"),
	                    rs.getString("vehicleType"),
	                    rs.getString("vehiclePlate")
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
