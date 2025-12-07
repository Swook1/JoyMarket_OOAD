package controller;

import database.OrderDatabase;
import javafx.collections.ObservableList;
import model.OrderHeader;

public class OrderController {

    private OrderDatabase orderDb = new OrderDatabase();

    public ObservableList<OrderHeader> getOrderHistory(String idCustomer) {
        return orderDb.getOrdersByCustomer(idCustomer);
    }

    public ObservableList<OrderHeader> getAllOrders() {
        return orderDb.getAllOrders();
    }

    public boolean updateOrderStatus(String idOrder, String newStatus) {
        return orderDb.updateOrderStatus(idOrder, newStatus);
    }

    public boolean deleteOrder(String idOrder) {
        return orderDb.deleteOrder(idOrder);
    }
}
