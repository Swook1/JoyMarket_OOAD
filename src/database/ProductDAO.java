package database;

import java.sql.*;

import model.*;

/*
  ProductDAO - Data Access Object untuk table products
  Operasi CRUD produk: get by ID, update stock, get all products
  Stok berkurang otomatis pas checkout, bisa diupdate manual juga
 */
public class ProductDAO {
	// Ambil data 1 produk berdasarkan ID
	public Product getProduct(int idProduct) {
        String sql = "SELECT * FROM products WHERE idProduct = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProduct);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	// Update stok produk 
	public boolean updateStock(int idProduct, int stock) {
        String sql = "UPDATE products SET stock = ? WHERE idProduct = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, stock);
            ps.setInt(2, idProduct);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	// Ambil semua produk yang ada di database
	// Return ArrayList buat ditampilin di ProductListView
	public java.util.ArrayList<Product> getAllProducts() {
	    java.util.ArrayList<Product> productList = new java.util.ArrayList<>();
	    String sql = "SELECT * FROM products";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            productList.add(new Product(
	                rs.getInt("idProduct"),
	                rs.getString("name"),
	                rs.getDouble("price"),
	                rs.getInt("stock"),
	                rs.getString("category")
	            ));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return productList;
	}
}
