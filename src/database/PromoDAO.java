package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;

public class PromoDAO {
	public Promo getPromo(int idPromo) {
        String sql = "SELECT * FROM promo WHERE idPromo = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPromo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Promo(
                        rs.getInt("idPromo"),
                        rs.getString("code"),
                        rs.getString("headline"),
                        rs.getDouble("discountPercentage")              
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
