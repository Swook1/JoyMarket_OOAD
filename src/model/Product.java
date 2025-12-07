package model;

import javafx.beans.property.*;

public class Product {

    private StringProperty idProduct;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty stock;
    private StringProperty category;

    public Product() {
        this.idProduct = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleDoubleProperty();
        this.stock = new SimpleIntegerProperty();
        this.category = new SimpleStringProperty();
    }

    public String getIdProduct() { return idProduct.get(); }
    public void setIdProduct(String value) { idProduct.set(value); }
    public StringProperty idProductProperty() { return idProduct; }

    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    public double getPrice() { return price.get(); }
    public void setPrice(double value) { price.set(value); }
    public DoubleProperty priceProperty() { return price; }

    public int getStock() { return stock.get(); }
    public void setStock(int value) { stock.set(value); }
    public IntegerProperty stockProperty() { return stock; }

    public String getCategory() { return category.get(); }
    public void setCategory(String value) { category.set(value); }
    public StringProperty categoryProperty() { return category; }
}
