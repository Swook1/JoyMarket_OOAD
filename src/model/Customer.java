package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Customer extends User {

    private DoubleProperty balance;

    public Customer() {
        super();
        this.balance = new SimpleDoubleProperty();
        setRole("Customer"); 
    }

    public double getBalance() { return balance.get(); }
    public void setBalance(double value) { balance.set(value); }
    public DoubleProperty balanceProperty() { return balance; }
}
