package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Courier;

import java.sql.*;

public class CourierDatabase {
    private Connection connection;

    public CourierDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<Courier> getAllCouriers() {
        ObservableList<Courier> list = FXCollections.observableArrayList();
        String query = "SELECT idCourier, vehicleType, vehiclePlate FROM Courier";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Courier c = new Courier();
                c.setIdCourier(rs.getString("idCourier"));
                c.setVehicleType(rs.getString("vehicleType"));
                c.setVehiclePlate(rs.getString("vehiclePlate"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get couriers!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertCourier(Courier courier) {
        String query = "INSERT INTO Courier (idCourier, vehicleType, vehiclePlate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, courier.getIdCourier());
            ps.setString(2, courier.getVehicleType());
            ps.setString(3, courier.getVehiclePlate());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert courier!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignCourierToOrder(String idOrder, String idCourier) {
        // if delivery row exists UPDATE else INSERT
        String check = "SELECT idOrder FROM Delivery WHERE idOrder = ?";
        try (PreparedStatement ps = connection.prepareStatement(check)) {
            ps.setString(1, idOrder);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String update = "UPDATE Delivery SET idCourier = ?, status = ? WHERE idOrder = ?";
                    try (PreparedStatement pu = connection.prepareStatement(update)) {
                        pu.setString(1, idCourier);
                        pu.setString(2, "Assigned");
                        pu.setString(3, idOrder);
                        int r = pu.executeUpdate();
                        return r > 0;
                    }
                } else {
                    String insert = "INSERT INTO Delivery (idOrder, idCourier, status) VALUES (?, ?, ?)";
                    try (PreparedStatement pi = connection.prepareStatement(insert)) {
                        pi.setString(1, idOrder);
                        pi.setString(2, idCourier);
                        pi.setString(3, "Assigned");
                        int r = pi.executeUpdate();
                        return r > 0;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to assign courier to order!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDeliveryStatus(String idOrder, String status) {
        String query = "UPDATE Delivery SET status = ? WHERE idOrder = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setString(2, idOrder);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update delivery status!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCourier(String idCourier) {
        String query = "DELETE FROM Courier WHERE idCourier = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCourier);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete courier!");
            e.printStackTrace();
            return false;
        }
    }
}
