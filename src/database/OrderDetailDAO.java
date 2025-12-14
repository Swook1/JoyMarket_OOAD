package database;

import java.sql.*;
import java.time.LocalDate;

import model.*;

public class OrderDetailDAO {

	public boolean createOrderDetail(int idOrder, int idProduct, int qty) {
        String sql = "INSERT INTO orderdetail VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, 1);
            ps.setInt(2, idProduct);
            ps.setInt(3, qty);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public OrderDetail getOrderDetail(int idOrder, int idProduct) {
        String sql = "SELECT * FROM orderdetail WHERE idOrder = ? AND idProduct = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrder);
            ps.setInt(2, idProduct);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new OrderDetail(
                        rs.getInt("idOrder"),
                        rs.getInt("idProduct"),
                        rs.getInt("qty")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public Customer getCustomerOrderDetail(int idOrder, int idProduct) {

	    String sql = "SELECT idCustomer FROM orderheader WHERE idOrder = ?";

	    try {
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);

	        ps.setInt(1, idOrder);
	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            int customerId = rs.getInt("idCustomer");

	            // Reuse your existing CustomerDAO
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
}
