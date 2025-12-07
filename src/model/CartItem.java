package model;

import javafx.beans.property.*;

public class CartItem {

    private StringProperty idCustomer;
    private StringProperty idProduct;
    private IntegerProperty count;

    public CartItem() {
        this.idCustomer = new SimpleStringProperty();
        this.idProduct = new SimpleStringProperty();
        this.count = new SimpleIntegerProperty();
    }

    public String getIdCustomer() { return idCustomer.get(); }
    public void setIdCustomer(String value) { idCustomer.set(value); }
    public StringProperty idCustomerProperty() { return idCustomer; }

    public String getIdProduct() { return idProduct.get(); }
    public void setIdProduct(String value) { idProduct.set(value); }
    public StringProperty idProductProperty() { return idProduct; }

    public int getCount() { return count.get(); }
    public void setCount(int value) { count.set(value); }
    public IntegerProperty countProperty() { return count; }
}
