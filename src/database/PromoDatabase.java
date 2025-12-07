package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Promo;

import java.sql.*;

public class PromoDatabase {
    private Connection connection;

    public PromoDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<Promo> getAllPromos() {
        ObservableList<Promo> list = FXCollections.observableArrayList();
        String query = "SELECT idPromo, code, headline, discountPercentage FROM Promo";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Promo p = new Promo();
                p.setIdPromo(rs.getString("idPromo"));
                p.setCode(rs.getString("code"));
                p.setHeadline(rs.getString("headline"));
                p.setDiscountPercentage(rs.getDouble("discountPercentage"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get promos!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertPromo(Promo promo) {
        String query = "INSERT INTO Promo (idPromo, code, headline, discountPercentage) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, promo.getIdPromo());
            ps.setString(2, promo.getCode());
            ps.setString(3, promo.getHeadline());
            ps.setDouble(4, promo.getDiscountPercentage());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert promo!");
            e.printStackTrace();
            return false;
        }
    }

    public Promo getPromoByCode(String code) {
        String query = "SELECT idPromo, code, headline, discountPercentage FROM Promo WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Promo p = new Promo();
                    p.setIdPromo(rs.getString("idPromo"));
                    p.setCode(rs.getString("code"));
                    p.setHeadline(rs.getString("headline"));
                    p.setDiscountPercentage(rs.getDouble("discountPercentage"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get promo by code!");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletePromo(String idPromo) {
        String query = "DELETE FROM Promo WHERE idPromo = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idPromo);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete promo!");
            e.printStackTrace();
            return false;
        }
    }
}
