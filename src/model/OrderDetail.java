package model;

import javafx.beans.property.*;

public class OrderDetail {

    private StringProperty idOrder;
    private StringProperty idProduct;
    private IntegerProperty qty;

    public OrderDetail() {
        this.idOrder = new SimpleStringProperty();
        this.idProduct = new SimpleStringProperty();
        this.qty = new SimpleIntegerProperty();
    }

    public String getIdOrder() { return idOrder.get(); }
    public void setIdOrder(String value) { idOrder.set(value); }
    public StringProperty idOrderProperty() { return idOrder; }

    public String getIdProduct() { return idProduct.get(); }
    public void setIdProduct(String value) { idProduct.set(value); }
    public StringProperty idProductProperty() { return idProduct; }

    public int getQty() { return qty.get(); }
    public void setQty(int value) { qty.set(value); }
    public IntegerProperty qtyProperty() { return qty; }
}
