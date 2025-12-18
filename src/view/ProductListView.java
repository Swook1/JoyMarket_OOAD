package view;

import controller.CartController;
import database.ProductDAO;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import model.User;

/*
  ProductListView - halaman browse produk
  Nampilin semua produk dalam table, customer bisa pilih & add to cart
  Ada validasi qty harus valid & ga boleh melebihi stock
 */
public class ProductListView {

    private Stage stage;
    private User customer;
    private ProductDAO productDAO;
    private CartController cartController;

    public ProductListView(Stage stage, User customer) {
        this.stage = stage;
        this.customer = customer;
        productDAO = new ProductDAO();
        cartController = new CartController();
        initUI();
    }

    private void initUI() {
        TableView<Product> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(productDAO.getAllProducts()));

        TableColumn<Product, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(d -> new javafx.beans.property.SimpleStringProperty(d.getValue().getName()));

        TableColumn<Product, Number> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(d -> new javafx.beans.property.SimpleDoubleProperty(d.getValue().getPrice()));

        TableColumn<Product, Number> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(d -> new javafx.beans.property.SimpleIntegerProperty(d.getValue().getStock()));

        table.getColumns().addAll(colName, colPrice, colStock);

        TextField qtyField = new TextField();
        qtyField.setPromptText("Qty");

        Label msg = new Label();

        Button addBtn = new Button("Add to Cart");
        Button backBtn = new Button("Back");

        addBtn.setOnAction(e -> {
            Product p = table.getSelectionModel().getSelectedItem();

            if (p == null) {
                msg.setText("Please select a product!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            if (qtyField.getText().isEmpty()) {
                msg.setText("Quantity must be filled!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException ex) {
                msg.setText("Quantity must be numeric!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            if (qty <= 0) {
                msg.setText("Quantity must be greater than 0!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            if (qty > p.getStock()) {
                msg.setText("Quantity exceeds available stock!");
                msg.setStyle("-fx-text-fill: red;");
                return;
            }

            msg.setText(
                cartController.addToCart(customer.getIdUser(), p.getIdProduct(), qty)
            );
            msg.setStyle("-fx-text-fill: green;");
            qtyField.clear();
        });

        backBtn.setOnAction(e -> {
            new CustomerDashboardView(stage, (model.Customer) customer);
        });

        HBox btnBox = new HBox(10, addBtn, backBtn);

        VBox root = new VBox(10, table, qtyField, btnBox, msg);
        stage.setScene(new Scene(root, 550, 450));
        stage.setTitle("Product List");
        stage.show();
    }
}
