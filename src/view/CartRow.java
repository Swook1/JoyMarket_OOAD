package view;

import javafx.beans.property.*;

public class CartRow {
    private int idProduct;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty qty;

    public CartRow(int idProduct, String name, double price, int qty) {
        this.idProduct = idProduct;
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.qty = new SimpleIntegerProperty(qty);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }
}
