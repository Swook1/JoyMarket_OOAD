package controller;

import database.UserDatabase;
import database.CustomerDatabase;
import model.Customer;
import model.User;

public class AuthController {

    private UserDatabase userDb = new UserDatabase();
    private CustomerDatabase customerDb = new CustomerDatabase();

    public boolean register(String id, String fullName, String email, String password, String phone, String address) {

        // Cek email duplicate
        if (userDb.emailExist(email)) {
            System.out.println("Email already exists!");
            return false;
        }

        // Insert ke tabel User
        User user = new User();
        user.setIdUser(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole("Customer");

        boolean userInserted = userDb.insertUser(user);

        if (!userInserted) return false;

        // Insert ke tabel Customer
        Customer c = new Customer();
        c.setIdUser(id);
        c.setBalance(0);

        return customerDb.insertCustomer(c);
    }

    public User login(String email, String password) {
        for (User u : userDb.getUsers()) {
            if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        System.out.println("Login failed!");
        return null;
    }
}
