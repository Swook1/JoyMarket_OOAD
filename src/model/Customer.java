package model;

/*
  Customer - extends dari User
  Nambahin field balance buat saldo customer
  Bisa top up dan dipake buat checkout
 */
public class Customer extends User {
    private double balance; // saldo customer

    public Customer() {}

    public Customer(int idUser, String fullName, String email, String password,
                    String phone, String address, String role, double balance) {
        super(idUser, fullName, email, password, phone, address, role);
        this.balance = balance;
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}