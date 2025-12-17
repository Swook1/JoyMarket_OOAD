package controller;

import java.util.ArrayList;

import database.DeliveryDAO;
import model.Delivery;

public class CourierController {

    private DeliveryDAO deliveryDAO;

    public CourierController() {
        deliveryDAO = new DeliveryDAO();
    }

    // =====================
    // UPDATE DELIVERY STATUS
    // =====================
    // Soal: Status must be Pending / In Progress / Delivered [cite: 70]
    public String updateDeliveryStatus(int idOrder, int idCourier, String newStatus) {
        
        // 1. Validasi Status Selection
        if (newStatus == null || newStatus.isEmpty()) {
            return "Status must be selected!";
        }

        // Validasi manual string matching (Tanpa Regex)
        boolean isValidStatus = newStatus.equals("Pending") || 
                                newStatus.equals("In Progress") || 
                                newStatus.equals("Delivered");

        if (!isValidStatus) {
            return "Status must be either Pending, In Progress, or Delivered.";
        }

        // 2. Update ke Database
        boolean isSuccess = deliveryDAO.editDeliveryStatus(idOrder, idCourier, newStatus);

        if (isSuccess) {
            return "Success update delivery status!";
        } else {
            return "Failed to update status.";
        }
    }
    
    public ArrayList<Delivery> getDeliveriesByCourier(int idCourier) {
        return deliveryDAO.getDeliveriesByCourier(idCourier);
    }

}