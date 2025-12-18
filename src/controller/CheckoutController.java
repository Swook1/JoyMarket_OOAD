package controller;

import database.CartItemDAO;
import database.ProductDAO;
import model.CartItem;
import model.Customer;

import java.sql.ResultSet;

/*
  CheckoutController - handle proses checkout
  Validasi stok dulu, kalo cukup baru kurangin stok & clear cart
  Return true kalo berhasil, false kalo stok ga cukup
 */
public class CheckoutController {

    private CartItemDAO cartDAO = new CartItemDAO();
    private ProductDAO productDAO = new ProductDAO();

    // Proses checkout: validasi stok -> kurangi stok -> clear cart
    public boolean processCheckout(Customer customer) {

        var cartItems = cartDAO.getCartItemsForCheckout(customer.getIdUser());

        for (CartItem item : cartItems) {
            int idProduct = item.getIdProduct();
            int qty = item.getCount();

            var product = productDAO.getProduct(idProduct);

            // VALIDASI STOCK
            if (product.getStock() < qty) {
                return false;
            }
        }

        // KURANGI STOCK (SETELAH SEMUA VALID)
        for (CartItem item : cartItems) {
            var product = productDAO.getProduct(item.getIdProduct());
            productDAO.updateStock(
                    item.getIdProduct(),
                    product.getStock() - item.getCount()
            );
        }

        // CLEAR CART
        cartDAO.clearCart(customer.getIdUser());
        return true;
    }

}
