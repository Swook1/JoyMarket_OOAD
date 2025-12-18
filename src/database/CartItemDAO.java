package database;

import java.sql.*;
import java.util.ArrayList;

import model.*;

/*
  CartItemDAO - Data Access Object untuk table cartitems
  Handle semua operasi cart: add, delete, update, get items
  Pake ON DUPLICATE KEY UPDATE biar kalo produk udah ada di cart, qty nya ditambahin aja
 */
public class CartItemDAO {
	 // Tambahin produk ke cart
	 // Kalo produk udah ada, qty nya bakal ditambahin
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
	 
	 // Hapus item dari cart
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
	 
	 // Update jumlah qty produk di cart
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
	 
	 // Ambil semua cart items milik customer tertentu
	 // Join dengan products buat dapetin nama dan harga produk
	 // Return ResultSet buat ditampilin di CartView
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
	 
	// FOR CHECKOUT PROCESS
	 // Ambil cart items dalam bentuk ArrayList (buat proses checkout)
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

	// Hapus semua cart items customer (dipake setelah checkout berhasil)
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
