package controller;

import database.CartItemDatabase;
import javafx.collections.ObservableList;
import model.CartItem;

public class CartController {

    private CartItemDatabase cartDb = new CartItemDatabase();

    public ObservableList<CartItem> getCartItems(String idCustomer) {
        return cartDb.getCartItemsByCustomer(idCustomer);
    }

    public boolean addToCart(String idCustomer, String idProduct, int count) {
        CartItem ci = new CartItem();
        ci.setIdCustomer(idCustomer);
        ci.setIdProduct(idProduct);
        ci.setCount(count);
        return cartDb.insertCartItem(ci);
    }

    public boolean updateCartItem(String idCustomer, String idProduct, int count) {
        CartItem ci = new CartItem();
        ci.setIdCustomer(idCustomer);
        ci.setIdProduct(idProduct);
        ci.setCount(count);
        return cartDb.updateCartItem(ci);
    }

    public boolean removeCartItem(String idCustomer, String idProduct) {
        return cartDb.deleteCartItem(idCustomer, idProduct);
    }

    public boolean clearCart(String idCustomer) {
        return cartDb.clearCart(idCustomer);
    }
}
