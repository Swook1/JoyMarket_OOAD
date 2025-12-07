package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.OrderDetail;
import model.OrderHeader;

import java.sql.*;

public class OrderDatabase {
    private Connection connection;

    public OrderDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<OrderHeader> getOrdersByCustomer(String idCustomer) {
        ObservableList<OrderHeader> list = FXCollections.observableArrayList();
        String query = "SELECT idOrder, idCustomer, idPromo, status, orderedAt, totalAmount FROM OrderHeader WHERE idCustomer = ? ORDER BY orderedAt DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderHeader oh = new OrderHeader();
                    oh.setIdOrder(rs.getString("idOrder"));
                    oh.setIdCustomer(rs.getString("idCustomer"));
                    oh.setIdPromo(rs.getString("idPromo"));
                    oh.setStatus(rs.getString("status"));
                    oh.setOrderedAt(rs.getTimestamp("orderedAt"));
                    oh.setTotalAmount(rs.getDouble("totalAmount"));
                    list.add(oh);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get orders by customer!");
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<OrderHeader> getAllOrders() {
        ObservableList<OrderHeader> list = FXCollections.observableArrayList();
        String query = "SELECT idOrder, idCustomer, idPromo, status, orderedAt, totalAmount FROM OrderHeader ORDER BY orderedAt DESC";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                OrderHeader oh = new OrderHeader();
                oh.setIdOrder(rs.getString("idOrder"));
                oh.setIdCustomer(rs.getString("idCustomer"));
                oh.setIdPromo(rs.getString("idPromo"));
                oh.setStatus(rs.getString("status"));
                oh.setOrderedAt(rs.getTimestamp("orderedAt"));
                oh.setTotalAmount(rs.getDouble("totalAmount"));
                list.add(oh);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get all orders!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertOrderHeader(OrderHeader oh) {
        String query = "INSERT INTO OrderHeader (idOrder, idCustomer, idPromo, status, orderedAt, totalAmount) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, oh.getIdOrder());
            ps.setString(2, oh.getIdCustomer());
            ps.setString(3, oh.getIdPromo());
            ps.setString(4, oh.getStatus());
            if (oh.getOrderedAt() != null) ps.setTimestamp(5, new Timestamp(oh.getOrderedAt().getTime()));
            else ps.setTimestamp(5, null);
            ps.setDouble(6, oh.getTotalAmount());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert order header!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertOrderDetail(OrderDetail od) {
        String query = "INSERT INTO OrderDetail (idOrder, idProduct, qty) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, od.getIdOrder());
            ps.setString(2, od.getIdProduct());
            ps.setInt(3, od.getQty());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert order detail!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateOrderStatus(String idOrder, String status) {
        String query = "UPDATE OrderHeader SET status = ? WHERE idOrder = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, idOrder);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update order status!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String idOrder) {
        String query = "DELETE FROM OrderHeader WHERE idOrder = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idOrder);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete order!");
            e.printStackTrace();
            return false;
        }
    }
}
