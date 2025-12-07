package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;

public class CustomerDatabase {
    private Connection connection;

    public CustomerDatabase() {
        connection = DatabaseConnection.getConnection();
    }

    public ObservableList<Customer> getCustomers() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String query = "SELECT c.idCustomer, c.balance, u.fullName, u.email, u.password, u.phone, u.address, u.role " +
                       "FROM Customer c JOIN `User` u ON c.idCustomer = u.idUser";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                Customer c = new Customer();
                c.setIdUser(rs.getString("idCustomer"));
                c.setBalance(rs.getDouble("balance"));
                c.setFullName(rs.getString("fullName"));
                c.setEmail(rs.getString("email"));
                c.setPassword(rs.getString("password"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                c.setRole(rs.getString("role"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get customers!");
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertCustomer(Customer customer) {
        String query = "INSERT INTO Customer (idCustomer, balance) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, customer.getIdUser());
            ps.setDouble(2, customer.getBalance());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to insert customer!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        // update Customer table only (balance). If you want update User fields, use UserDatabase.updateUser
        String query = "UPDATE Customer SET balance = ? WHERE idCustomer = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, customer.getBalance());
            ps.setString(2, customer.getIdUser());
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to update customer!");
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBalance(String idCustomer, double newBalance) {
        String query = "UPDATE Customer SET balance = ? WHERE idCustomer = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, idCustomer);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Failed to update balance!");
            e.printStackTrace();
            return false;
        }
    }

    
    public boolean deleteCustomer(String idCustomer) {
        String query = "DELETE FROM Customer WHERE idCustomer = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            int row = ps.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println("Failed to delete customer!");
            e.printStackTrace();
            return false;
        }
    }
}
