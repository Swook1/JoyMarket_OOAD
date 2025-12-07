package controller;

import database.ProductDatabase;
import javafx.collections.ObservableList;
import model.Product;

public class ProductController {

    private ProductDatabase productDb = new ProductDatabase();

    public ObservableList<Product> getAllProducts() {
        return productDb.getAllProducts();
    }

    public boolean addProduct(Product p) {
        return productDb.insertProduct(p);
    }

    public boolean updateStock(String idProduct, int newStock) {
        return productDb.updateProductStock(idProduct, newStock);
    }

    public boolean deleteProduct(String idProduct) {
        return productDb.deleteProduct(idProduct);
    }
}
