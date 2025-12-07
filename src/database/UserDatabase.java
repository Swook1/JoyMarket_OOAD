package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabase {
    private Connection connection;

    public UserDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT idUser, fullName, email, password, phone, address, role FROM `User`";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                User u = new User();
                u.setIdUser(rs.getString("idUser"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get users!");
            e.printStackTrace();
        }
        return users;
    }

    public boolean insertUser(User user) {
        String query = "INSERT INTO `User` (idUser, fullName, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getIdUser());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getRole());

            int rowAffected = ps.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert user!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        String query = "UPDATE `User` SET fullName = ?, email = ?, password = ?, phone = ?, address = ?, role = ? WHERE idUser = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getIdUser());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Update user failed!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String idUser) {
        String query = "DELETE FROM `User` WHERE idUser = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idUser);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Delete user failed!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExist(String email) {
        String query = "SELECT 1 FROM `User` WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Check email exists failed!");
            e.printStackTrace();
        }
        return false;
    }
}
