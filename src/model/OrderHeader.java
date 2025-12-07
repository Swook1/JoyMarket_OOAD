package model;

import javafx.beans.property.*;
import java.util.Date;

public class OrderHeader {

    private StringProperty idOrder;
    private StringProperty idCustomer;
    private StringProperty idPromo;
    private StringProperty status;
    private ObjectProperty<Date> orderedAt;
    private DoubleProperty totalAmount;

    public OrderHeader() {
        this.idOrder = new SimpleStringProperty();
        this.idCustomer = new SimpleStringProperty();
        this.idPromo = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.orderedAt = new SimpleObjectProperty<>();
        this.totalAmount = new SimpleDoubleProperty();
    }

    public String getIdOrder() { return idOrder.get(); }
    public void setIdOrder(String value) { idOrder.set(value); }
    public StringProperty idOrderProperty() { return idOrder; }

    public String getIdCustomer() { return idCustomer.get(); }
    public void setIdCustomer(String value) { idCustomer.set(value); }
    public StringProperty idCustomerProperty() { return idCustomer; }

    public String getIdPromo() { return idPromo.get(); }
    public void setIdPromo(String value) { idPromo.set(value); }
    public StringProperty idPromoProperty() { return idPromo; }

    public String getStatus() { return status.get(); }
    public void setStatus(String value) { status.set(value); }
    public StringProperty statusProperty() { return status; }

    public Date getOrderedAt() { return orderedAt.get(); }
    public void setOrderedAt(Date value) { orderedAt.set(value); }
    public ObjectProperty<Date> orderedAtProperty() { return orderedAt; }

    public double getTotalAmount() { return totalAmount.get(); }
    public void setTotalAmount(double value) { totalAmount.set(value); }
    public DoubleProperty totalAmountProperty() { return totalAmount; }
}
