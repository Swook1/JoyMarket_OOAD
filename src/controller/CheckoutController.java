package controller;

import database.CartItemDAO;
import database.CustomerDAO;
import database.OrderDetailDAO;
import database.OrderHeaderDAO;
import database.ProductDAO;
import database.PromoDAO;
import model.CartItem;
import model.Customer;
import model.Promo;

import java.util.ArrayList;

public class CheckoutController {

    private CartItemDAO cartDAO = new CartItemDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderHeaderDAO orderHeaderDAO = new OrderHeaderDAO();
    private OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    private PromoDAO promoDAO = new PromoDAO();
    
    // Checkout result codes
    public static final int SUCCESS = 0;
    public static final int STOCK_NOT_SUFFICIENT = 1;
    public static final int BALANCE_NOT_SUFFICIENT = 2;
    public static final int ORDER_FAILED = 3;

    public int processCheckout(Customer customer, int idPromo, double finalTotal) {
        var cartItems = cartDAO.getCartItemsForCheckout(customer.getIdUser());

        // VALIDASI STOCK DULU
        for (CartItem item : cartItems) {
            var product = productDAO.getProduct(item.getIdProduct());
            if (product.getStock() < item.getCount()) {
                return STOCK_NOT_SUFFICIENT;
            }
        }
        
        // VALIDASI BALANCE CUKUP
        if (customer.getBalance() < finalTotal) {
            return BALANCE_NOT_SUFFICIENT;
        }

        // CREATE ORDER HEADER dengan idPromo
        int orderId = orderHeaderDAO.createOrderAndGetId(customer.getIdUser(), idPromo);
        
        if (orderId == -1) {
            return ORDER_FAILED;
        }

        // CREATE ORDER DETAILS
        for (CartItem item : cartItems) {
            var product = productDAO.getProduct(item.getIdProduct());
            
            orderDetailDAO.createOrderDetail(
				orderId,
				item.getIdProduct(),
				item.getCount()
			);
            
            // KURANGI STOCK
            productDAO.updateStock(
                item.getIdProduct(),
                product.getStock() - item.getCount()
            );
        }
        
        // KURANGI BALANCE CUSTOMER
        double newBalance = customer.getBalance() - finalTotal;
        customerDAO.updateBalance(customer.getIdUser(), newBalance);
        customer.setBalance(newBalance); // Update object customer juga

        // CLEAR CART
        cartDAO.clearCart(customer.getIdUser());
        return SUCCESS;
    }
}
