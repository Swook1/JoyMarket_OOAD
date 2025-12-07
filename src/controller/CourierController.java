package controller;

import database.CourierDatabase;
import javafx.collections.ObservableList;
import model.Courier;

public class CourierController {

    private CourierDatabase courierDb = new CourierDatabase();

    public ObservableList<Courier> getAllCouriers() {
        return courierDb.getAllCouriers();
    }

    public boolean addCourier(Courier c) {
        return courierDb.insertCourier(c);
    }

    public boolean assignCourier(String idOrder, String idCourier) {
        return courierDb.assignCourierToOrder(idOrder, idCourier);
    }

    public boolean updateDeliveryStatus(String idOrder, String status) {
        return courierDb.updateDeliveryStatus(idOrder, status);
    }

    public boolean deleteCourier(String idCourier) {
        return courierDb.deleteCourier(idCourier);
    }
}
