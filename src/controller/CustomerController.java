package controller;

import database.CustomerDAO;
import database.UserDAO;
import model.Customer;

public class CustomerController {

    private CustomerDAO customerDAO;
    private UserDAO userDAO;

    public CustomerController() {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO(); 
    }

    // =====================
    // UPDATE PROFILE
    // =====================
    public boolean updateProfile(int idUser, String fullName, String phone, String address) {
        UserDAO userDAO = new UserDAO();
        return userDAO.updateProfile(idUser, fullName, phone, address);
    }

    // =====================
    // TOP UP BALANCE
    // =====================
    public boolean topUpBalance(Customer customer, double amount) {
        CustomerDAO customerDAO = new CustomerDAO();
        return customerDAO.topUpBalance(customer.getIdUser(), amount);
    }

}
