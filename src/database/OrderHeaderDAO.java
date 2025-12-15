package database;

import java.sql.*;
import java.time.*;

import model.*;

public class OrderHeaderDAO {
	public boolean createOrder(int idCustomer, int idPromo) {
        String sql = "INSERT INTO OrderHeader VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, 1);
            ps.setInt(2, idCustomer);
            ps.setInt(3, idPromo);
            ps.setString(4, "Unfinished");
            ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public boolean editOrderHeaderStatus(int idOrder, String status) {
        String sql = "UPDATE OrderHeader SET status = ? WHERE idOrder = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idOrder);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public OrderHeader getOrderHeader(int idOrder) {
        String sql = "SELECT * FROM OrderHeader WHERE idOrder = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrder);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new OrderHeader(
                        rs.getInt("idOrder"),
                        rs.getInt("idCustomer"),
                        rs.getInt("idPromo"),
                        rs.getString("status"),
                        rs.getDate("orderDate")             
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public Customer getCustomerOrderHeader(int idOrder) {

	    String sql = "SELECT idCustomer FROM orderheader WHERE idOrder = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, idOrder);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int customerId = rs.getInt("idCustomer");
	            CustomerDAO dao = new CustomerDAO();
	            return dao.getCustomer(customerId);
	        }

	        rs.close();
	        ps.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public boolean updateStatus(int idOrder, String newStatus) {
	    // Pastikan nama tabel sesuai DB lu (orderheader atau orderheaders)
	    String sql = "UPDATE orderheader SET status = ? WHERE idOrder = ?";

	    try (java.sql.Connection con = DBConnection.getConnection();
	         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, newStatus);
	        ps.setInt(2, idOrder);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
