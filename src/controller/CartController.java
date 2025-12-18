package controller;

import database.CartItemDAO;
import model.CartItem;

public class CartController {

    private CartItemDAO cartDAO;

    public CartController() {
        cartDAO = new CartItemDAO();
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
}
