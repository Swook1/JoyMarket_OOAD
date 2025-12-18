package controller;

import java.util.ArrayList;

import database.CourierDAO;
import database.DeliveryDAO;
import database.OrderHeaderDAO;
import database.ProductDAO;
import model.Courier;
import model.OrderHeader;
import model.Product;

/*
  AdminController - handle logic khusus admin
  Edit stock produk, assign courier ke order
  Admin punya akses penuh ke management produk & delivery
 */
public class AdminController {

    private ProductDAO productDAO;
    private DeliveryDAO deliveryDAO;
    private OrderHeaderDAO orderHeaderDAO = new OrderHeaderDAO();
    private CourierDAO courierDAO = new CourierDAO();

    public AdminController() {
        productDAO = new ProductDAO();
        deliveryDAO = new DeliveryDAO();
    }

    // EDIT PRODUCT STOCK
    public String editProductStock(int idProduct, String stockInput) {
        
        // Validasi Numeric
        int newStock = -1;
        try {
            newStock = Integer.parseInt(stockInput);
        } catch (NumberFormatException e) {
            return "Stock must be numeric!";
        }

        // Validasi Non-Negative
        if (newStock < 0) {
            return "Stock cannot be negative!";
        }

        // Update ke Database
        boolean isSuccess = productDAO.updateStock(idProduct, newStock);
        
        if (isSuccess) {
            return "Success update stock!";
        } else {
            return "Failed to update stock (Product ID may not exist).";
        }
    }

    // ASSIGN COURIER
    public String assignCourierToOrder(int idOrder, int idCourier) {
        
        if (idOrder <= 0 || idCourier <= 0) {
            return "Invalid Order or Courier selection.";
        }

        // Assign Courier 
        boolean isSuccess = deliveryDAO.createDelivery(idOrder, idCourier);

        if (isSuccess) {
            // UPDATE STATUS ORDERHEADER 
            orderHeaderDAO.updateStatus(idOrder, "In Progress"); 
            
            return "Success assign courier!";
        } else {
            return "Failed to assign courier.";
        }
    }
    
    // Helper buat ambil data product 
    public Product getProductDetail(int idProduct) {
        return productDAO.getProduct(idProduct);
    }
    
    public java.util.ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
    
    // Ambil semua order yang belum di-assign courier
    public ArrayList<OrderHeader> getAllUnassignedOrders() {
        return orderHeaderDAO.getAllUnassignedOrders();
    }
    
    // Ambil semua courier
    public ArrayList<Courier> getAllCouriers() {
        return courierDAO.getAllCouriers();
    }
}