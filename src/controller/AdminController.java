package controller;

import database.DeliveryDAO;
import database.OrderHeaderDAO;
import database.ProductDAO;
import model.Product;

public class AdminController {

    private ProductDAO productDAO;
    private DeliveryDAO deliveryDAO;
    private OrderHeaderDAO orderHeaderDAO = new OrderHeaderDAO();

    public AdminController() {
        productDAO = new ProductDAO();
        deliveryDAO = new DeliveryDAO();
    }

    // =====================
    // EDIT PRODUCT STOCK
    // =====================
    // Soal: Stock cannot be negative, Must be numeric [cite: 62]
    public String editProductStock(int idProduct, String stockInput) {
        
        // 1. Validasi Numeric (Tanpa Regex)
        int newStock = -1;
        try {
            newStock = Integer.parseInt(stockInput);
        } catch (NumberFormatException e) {
            return "Stock must be numeric!";
        }

        // 2. Validasi Non-Negative
        if (newStock < 0) {
            return "Stock cannot be negative!";
        }

        // 3. Update ke Database
        boolean isSuccess = productDAO.updateStock(idProduct, newStock);
        
        if (isSuccess) {
            return "Success update stock!";
        } else {
            return "Failed to update stock (Product ID may not exist).";
        }
    }

    // =====================
    // ASSIGN COURIER
    // =====================
    // Soal: Courier must be selected, Must exist in DB [cite: 66]
    public String assignCourierToOrder(int idOrder, int idCourier) {
        
        if (idOrder <= 0 || idCourier <= 0) {
            return "Invalid Order or Courier selection.";
        }

        // 1. Assign Kurir (Insert ke Delivery)
        boolean isSuccess = deliveryDAO.createDelivery(idOrder, idCourier);

        if (isSuccess) {
            // 2. UPDATE STATUS ORDERHEADER (Ini Logic Tambahannya)
            // Kita ubah statusnya jadi "In Progress" atau "Assigned" biar gak Pending lagi
            orderHeaderDAO.updateStatus(idOrder, "In Progress"); 
            
            return "Success assign courier!";
        } else {
            return "Failed to assign courier.";
        }
    }
    
    // Helper buat ambil data product (dipakai View nanti)
    public Product getProductDetail(int idProduct) {
        return productDAO.getProduct(idProduct);
    }
    
    public java.util.ArrayList<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
}