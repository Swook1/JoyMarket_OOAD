package controller;

import database.CartItemDAO;
import database.PromoDAO;
import model.CartItem;
import model.Promo;

import java.sql.ResultSet;

/*
  CartController - handle business logic untuk cart
  Add, update, delete cart item
  Validasi qty harus > 0
 */
public class CartController {

    private CartItemDAO cartDAO;
    private PromoDAO promoDAO;

    public CartController() {
        cartDAO = new CartItemDAO();
        promoDAO = new PromoDAO();
    }

    // ADD PRODUCT TO CART
    public String addToCart(int idCustomer, int idProduct, int qty) {
        if (qty <= 0) {
            return "Quantity must be greater than 0!";
        }

        CartItem cart = new CartItem(idCustomer, idProduct, qty);
        boolean success = cartDAO.addToCart(cart);

        return success ? "Product added to cart!" : "Failed to add product.";
    }

    // UPDATE CART ITEM
    public String updateCartItem(int idCustomer, int idProduct, int qty) {
        if (qty <= 0) {
            return "Quantity must be greater than 0!";
        }

        CartItem cart = new CartItem(idCustomer, idProduct, qty);
        boolean success = cartDAO.updateCount(cart, qty);

        return success ? "Cart updated!" : "Failed to update cart.";
    }

    // DELETE CART ITEM
    public String deleteCartItem(int idCustomer, int idProduct) {
        CartItem cart = new CartItem(idCustomer, idProduct, 0);
        boolean success = cartDAO.deleteCartItem(cart);

        return success ? "Item removed from cart!" : "Failed to delete item.";
    }
    
    // VALIDATE PROMO CODE
    public Promo validatePromoCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null; // No promo, return null (will use idPromo = 0)
        }
        return promoDAO.getPromoByCode(code.trim());
    }
    
    // GET TOTAL PRICE dari cart
    public double getTotalPrice(int idCustomer) {
        double total = 0;
        try {
            ResultSet rs = cartDAO.getCartItemsByCustomer(idCustomer);
            while (rs.next()) {
                double price = rs.getDouble("price");
                int qty = rs.getInt("count");
                total += price * qty;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}