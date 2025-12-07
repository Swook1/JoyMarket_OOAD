package controller;

import database.CartItemDatabase;
import database.CustomerDatabase;
import database.OrderDatabase;
import database.ProductDatabase;
import javafx.collections.ObservableList;
import model.CartItem;
import model.OrderDetail;
import model.OrderHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckoutController {

    private CartItemDatabase cartDb = new CartItemDatabase();
    private CustomerDatabase customerDb = new CustomerDatabase();
    private ProductDatabase productDb = new ProductDatabase();
    private OrderDatabase orderDb = new OrderDatabase();

    public boolean checkout(String idOrder, String idCustomer, String idPromo) {

        ObservableList<CartItem> cart = cartDb.getCartItemsByCustomer(idCustomer);

        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return false;
        }

        double total = 0;
        List<OrderDetail> details = new ArrayList<>();

        // Hitung total dan buat OrderDetail list
        for (CartItem ci : cart) {

            var product = productDb.getAllProducts().stream()
                    .filter(p -> p.getIdProduct().equals(ci.getIdProduct()))
                    .findFirst()
                    .orElse(null);

            if (product == null) {
                System.out.println("Product not found while checkout!");
                return false;
            }

            total += product.getPrice() * ci.getCount();

            OrderDetail od = new OrderDetail();
            od.setIdOrder(idOrder);
            od.setIdProduct(ci.getIdProduct());
            od.setQty(ci.getCount());
            details.add(od);
        }

        // Ambil saldo customer
        var cust = customerDb.getCustomers().stream()
                .filter(c -> c.getIdUser().equals(idCustomer))
                .findFirst()
                .orElse(null);

        if (cust == null) {
            System.out.println("Customer not found!");
            return false;
        }

        if (cust.getBalance() < total) {
            System.out.println("Insufficient balance!");
            return false;
        }

        // Buat OrderHeader
        OrderHeader oh = new OrderHeader();
        oh.setIdOrder(idOrder);
        oh.setIdCustomer(idCustomer);
        oh.setIdPromo(idPromo);
        oh.setStatus("Pending");
        oh.setOrderedAt(new Date());
        oh.setTotalAmount(total);

        // Update balance
        double newBalance = cust.getBalance() - total;

        // ==============
        // Insert Order
        // ==============
        boolean headerOK = orderDb.insertOrderHeader(oh);
        if (!headerOK) return false;

        for (OrderDetail od : details) {
            boolean detailOK = orderDb.insertOrderDetail(od);
            if (!detailOK) return false;
        }

        // Update balance
        customerDb.updateBalance(idCustomer, newBalance);

        // Clear cart
        cartDb.clearCart(idCustomer);

        return true;
    }
}
