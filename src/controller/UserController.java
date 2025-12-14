package controller;

import database.CustomerDAO;
import database.UserDAO;
import model.User;

public class UserController {

    private UserDAO userDAO;
    private CustomerDAO customerDAO;

    public UserController() {
        userDAO = new UserDAO();
        customerDAO = new CustomerDAO();
    }

    // =====================
    // LOGIN
    // =====================
    public User login(String email, String password) {

        if (email == null || email.isEmpty()) {
            System.out.println("Email must be filled!");
            return null;
        }

        if (password == null || password.isEmpty()) {
            System.out.println("Password must be filled!");
            return null;
        }

        User user = userDAO.login(email, password);

        if (user == null) {
            System.out.println("Login failed!");
            return null;
        }

        System.out.println("Login success!");
        return user;
    }

    // =====================
    // REGISTER
    // =====================
    public boolean register(String fullName, String email, String password, String phone, String address) {
    	UserDAO userDAO = new UserDAO();

    	// validasi email unique 
    	if (userDAO.emailExist(email)) {
    		return false;
    	}

		User user = new User();
		user.setFullName(fullName);
		user.setEmail(email);
		user.setPassword(password);
		user.setPhone(phone);
		user.setAddress(address);
		user.setRole("Customer");
		
		return userDAO.register(user);
	}

}
