package view;

import javafx.beans.property.*;

/*
  CartRow - helper class buat represent 1 row di table cart
  Pake JavaFX Property biar bisa binding ke TableView
  Nyimpen id produk, nama, harga, qty
 */
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
