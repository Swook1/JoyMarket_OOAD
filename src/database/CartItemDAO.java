package database;

import java.sql.*;
import java.util.ArrayList;

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
	 
	 public ResultSet getCartItemsByCustomer(int idCustomer) {
		    String sql =
		        "SELECT c.idProduct, p.name, p.price, c.count " +
		        "FROM cartitems c " +
		        "JOIN products p ON c.idProduct = p.idProduct " +
		        "WHERE c.idCustomer = ?";

		    try {
		        Connection con = DBConnection.getConnection();
		        PreparedStatement ps = con.prepareStatement(sql);
		        ps.setInt(1, idCustomer);
		        return ps.executeQuery();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null;
		}
	 
	// ===============================
	// FOR CHECKOUT PROCESS
	// ===============================

	 public ArrayList<CartItem> getCartItemsForCheckout(int idCustomer) {
		    ArrayList<CartItem> items = new ArrayList<>();

		    String sql = "SELECT idProduct, count FROM cartitems WHERE idCustomer = ?";

		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        ps.setInt(1, idCustomer);
		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            items.add(new CartItem(
		                    idCustomer,
		                    rs.getInt("idProduct"),
		                    rs.getInt("count")
		            ));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return items;
		}


	public boolean clearCart(int idCustomer) {
	    String sql = "DELETE FROM cartitems WHERE idCustomer = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, idCustomer);
	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
