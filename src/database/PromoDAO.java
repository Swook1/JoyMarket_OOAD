package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.*;

/*
  PromoDAO - Data Access Object untuk table promo
  Get promo berdasarkan ID, dipake pas proses checkout
 */
public class PromoDAO {
	// Ambil data promo berdasarkan ID
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
	
	// Ambil data promo berdasarkan code
	public Promo getPromoByCode(String code) {
        String sql = "SELECT * FROM promo WHERE code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);
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