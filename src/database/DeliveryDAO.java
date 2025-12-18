package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.*;
import java.util.ArrayList;

import model.*;

/*
  DeliveryDAO - Data Access Object untuk table delivery
  Handle create delivery, update status, get delivery by order/courier
  Delivery di-assign ke courier untuk dikirim ke customer
 */
public class DeliveryDAO {
	// Bikin delivery baru, di-assign ke courier tertentu
	// Status awal: Pending
	public boolean createDelivery(int idOrder, int idCourier) {
	    String sql = "INSERT INTO Delivery (idOrder, idCourier, status) VALUES (?, ?, ?)";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idOrder);
	        ps.setInt(2, idCourier);
	        ps.setString(3, "Pending");

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	// Update status delivery (Pending -> In Transit -> Delivered)
	// Dipanggil dari CourierDashboard
	public boolean editDeliveryStatus(int idOrder, int idCourier, String status) {
        String sql = "UPDATE Delivery SET status = ? WHERE idOrder = ? AND idCourier = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, idOrder);
            ps.setInt(3, idCourier);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	// Get delivery spesifik berdasarkan order dan courier
	public Delivery getDelivery(int idOrder, int idCourier) {
        String sql = "SELECT * FROM Delivery WHERE idOrder = ? AND idCourier = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idOrder);
            ps.setInt(2, idCourier);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Delivery(
                        rs.getInt("idDelivery"),
                        rs.getInt("idOrder"),
                        rs.getInt("idCourier"),
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	// Ambil semua delivery yang di-handle sama courier tertentu
	// Buat ditampilin di courier dashboard
	public ArrayList<Delivery> getDeliveriesByCourier(int idCourier) {
	    ArrayList<Delivery> list = new ArrayList<>();

	    String sql = "SELECT * FROM delivery WHERE idCourier = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idCourier);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Delivery d = new Delivery(
	                rs.getInt("idDelivery"),
	                rs.getInt("idOrder"),
	                rs.getInt("idCourier"),
	                rs.getString("status")
	            );
	            list.add(d);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}




}
