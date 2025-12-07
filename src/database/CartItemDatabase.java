package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.CartItem;

import java.sql.*;

public class CartItemDatabase {
    private Connection connection;

    public CartItemDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<CartItem> getCartItemsByCustomer(String idCustomer) {
        ObservableList<CartItem> list = FXCollections.observableArrayList();
        String query = "SELECT idCustomer, idProduct, `count` FROM CartItem WHERE idCustomer = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartItem ci = new CartItem();
                    ci.setIdCustomer(rs.getString("idCustomer"));
                    ci.setIdProduct(rs.getString("idProduct"));
                    ci.setCount(rs.getInt("count"));
                    list.add(ci);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get cart items!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertCartItem(CartItem ci) {
        String query = "INSERT INTO CartItem (idCustomer, idProduct, `count`) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, ci.getIdCustomer());
            ps.setString(2, ci.getIdProduct());
            ps.setInt(3, ci.getCount());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert cart item!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCartItem(CartItem ci) {
        String query = "UPDATE CartItem SET `count` = ? WHERE idCustomer = ? AND idProduct = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, ci.getCount());
            ps.setString(2, ci.getIdCustomer());
            ps.setString(3, ci.getIdProduct());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update cart item!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCartItem(String idCustomer, String idProduct) {
        String query = "DELETE FROM CartItem WHERE idCustomer = ? AND idProduct = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete cart item!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearCart(String idCustomer) {
        String query = "DELETE FROM CartItem WHERE idCustomer = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to clear cart!");
            e.printStackTrace();
            return false;
        }
    }
}
