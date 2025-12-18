package controller;

import java.util.ArrayList;

import database.DeliveryDAO;
import model.Delivery;

/*
  CourierController - handle logic khusus courier
  Update status delivery (Pending -> In Progress -> Delivered)
  Validasi status harus sesuai enum yang ditentukan
 */
public class CourierController {

    private DeliveryDAO deliveryDAO;

    public CourierController() {
        deliveryDAO = new DeliveryDAO();
    }

    // UPDATE DELIVERY STATUS
    // Status harus Pending / In Progress / Delivered
    public String updateDeliveryStatus(int idOrder, int idCourier, String newStatus) {
        
        // Validasi Status Selection
        if (newStatus == null || newStatus.isEmpty()) {
            return "Status must be selected!";
        }

        // Validasi manual string matching 
        boolean isValidStatus = newStatus.equals("Pending") || 
                                newStatus.equals("In Progress") || 
                                newStatus.equals("Delivered");

        if (!isValidStatus) {
            return "Status must be either Pending, In Progress, or Delivered.";
        }

        // Update ke Database
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