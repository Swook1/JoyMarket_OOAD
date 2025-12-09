package database;

import java.sql.*;

import model.*;

public class CartItemDAO {
	 public boolean addToCart(CartItem cart) {
	        String sql = "INSERT INTO cartitems VALUES (?, ?, ?) "
	                   + "ON DUPLICATE KEY UPDATE count = count + VALUES(count)";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, cart.getIdCustomer());
	            ps.setInt(2, cart.getIdProduct());
	            ps.setInt(3, cart.getCount());

	            return ps.executeUpdate() > 0;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	 }
	 
	 public boolean deleteCartItem(CartItem cart) {
		 String sql = "DELETE FROM cartitems WHERE idCustomer = ? AND idProduct = ?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, cart.getIdCustomer());
	            ps.setInt(2, cart.getIdProduct());

	            return ps.executeUpdate() > 0;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	   }
	 
	 
	 public boolean updateCount(CartItem cart, int count) {
	        String sql = "UPDATE cartitems SET count = ? WHERE idCustomer = ? AND idProduct = ?";

	        try (Connection con = DBConnection.getConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, count);
	            ps.setInt(2, cart.getIdCustomer());
	            ps.setInt(3, cart.getIdProduct());

	            return ps.executeUpdate() > 0;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	 }
}
