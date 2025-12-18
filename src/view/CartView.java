package view;

import controller.CartController;
import controller.CheckoutController;
import database.CartItemDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

import java.sql.ResultSet;

/*
  CartView - halaman shopping cart customer
  Nampilin item di cart, bisa update qty atau remove item
  Ada tombol checkout buat lanjut ke proses pembayaran
 */
public class CartView {

    private Stage stage;
    private Customer customer;

    private TableView<CartRow> table;
    private CartController cartController;
    private CartItemDAO cartDAO;

    public CartView(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
        this.cartController = new CartController();
        this.cartDAO = new CartItemDAO();
        initUI();
    }

    private void initUI() {
        table = new TableView<>();
        refreshTable();

        TableColumn<CartRow, String> colName = new TableColumn<>("Product");
        colName.setCellValueFactory(d -> d.getValue().nameProperty());

        TableColumn<CartRow, Number> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(d -> d.getValue().priceProperty());

        TableColumn<CartRow, Number> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(d -> d.getValue().qtyProperty());

        table.getColumns().addAll(colName, colPrice, colQty);

        TextField qtyField = new TextField();
        qtyField.setPromptText("New Quantity");

        Label msg = new Label();

        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Remove");
        Button checkoutBtn = new Button("Checkout");
        Button backBtn = new Button("Back");

        // ===== UPDATE CART =====
        updateBtn.setOnAction(e -> {
            CartRow row = table.getSelectionModel().getSelectedItem();
            if (row == null) {
                msg.setText("Select item first!");
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyField.getText());
            } catch (NumberFormatException ex) {
                msg.setText("Quantity must be numeric!");
                return;
            }

            msg.setText(
                cartController.updateCartItem(
                	customer.getIdUser(),
                    row.getIdProduct(),
                    qty
                )
            );
            qtyField.clear();
            refreshTable();
        });

        // ===== DELETE CART =====
        deleteBtn.setOnAction(e -> {
            CartRow row = table.getSelectionModel().getSelectedItem();
            if (row == null) {
                msg.setText("Select item first!");
                return;
            }

            msg.setText(
           		cartController.deleteCartItem(
            		    customer.getIdUser(),
            		    row.getIdProduct()
            		)
            );
            refreshTable();
        });

        // ===== CHECKOUT =====
        checkoutBtn.setOnAction(e -> {
            CheckoutController checkoutController = new CheckoutController();

            boolean success = checkoutController.processCheckout(customer);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Checkout Success");
                alert.setHeaderText("Checkout Completed");
                alert.setContentText("Your order has been successfully processed!");
                alert.showAndWait();

                new CustomerDashboardView(stage, customer);
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Checkout Failed");
                alert.setContentText("Stock not sufficient!");
                alert.show();
            }
        });


        // ===== BACK =====
        backBtn.setOnAction(e -> {
            new CustomerDashboardView(stage, customer);
        });

        HBox btnBox = new HBox(10, updateBtn, deleteBtn, checkoutBtn, backBtn);

        VBox root = new VBox(10, table, qtyField, btnBox, msg);
        root.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(root, 550, 400));
        stage.setTitle("Cart");
        stage.show();
    }

    private void refreshTable() {
        ObservableList<CartRow> data = FXCollections.observableArrayList();

        try {
            ResultSet rs = cartDAO.getCartItemsByCustomer(customer.getIdUser());
            while (rs.next()) {
                data.add(new CartRow(
                        rs.getInt("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("count")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(data);
    }
}
