package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;

import java.sql.*;

public class ProductDatabase {
    private Connection connection;

    public ProductDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<Product> getAllProducts() {
        ObservableList<Product> list = FXCollections.observableArrayList();
        String query = "SELECT idProduct, name, price, stock, category FROM Product";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Product p = new Product();
                p.setIdProduct(rs.getString("idProduct"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setCategory(rs.getString("category"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get products!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertProduct(Product product) {
        String query = "INSERT INTO Product (idProduct, name, price, stock, category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, product.getIdProduct());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getCategory());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert product!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProductStock(String idProduct, int newStock) {
        String query = "UPDATE Product SET stock = ? WHERE idProduct = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, newStock);
            ps.setString(2, idProduct);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update product stock!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteProduct(String idProduct) {
        String query = "DELETE FROM Product WHERE idProduct = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idProduct);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete product!");
            e.printStackTrace();
            return false;
        }
    }
}
