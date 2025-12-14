package database;

import java.sql.*;

import model.*;

public class ProductDAO {
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
}
