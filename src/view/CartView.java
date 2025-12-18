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
import model.Promo;

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
    
    // Promo & Total
    private TextField promoField;
    private Label totalLabel;
    private Label discountLabel;
    private Label finalTotalLabel;
    private Label promoMsg;
    private int currentPromoId = 0;
    private double currentDiscount = 0;

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

        // ===== PROMO CODE SECTION =====
        HBox promoBox = new HBox(10);
        promoField = new TextField();
        promoField.setPromptText("Enter Promo Code");
        promoField.setPrefWidth(150);
        
        Button applyPromoBtn = new Button("Apply Promo");
        promoMsg = new Label();
        
        applyPromoBtn.setOnAction(e -> {
            String code = promoField.getText();
            
            if (code == null || code.trim().isEmpty()) {
                // Reset ke no promo
                currentPromoId = 0;
                currentDiscount = 0;
                promoMsg.setText("No promo applied.");
                promoMsg.setStyle("-fx-text-fill: gray;");
                updateTotalLabels();
                return;
            }
            
            Promo promo = cartController.validatePromoCode(code);
            
            if (promo != null) {
                currentPromoId = promo.getIdPromo();
                currentDiscount = promo.getDiscountPercentage() / 100.0; // Convert 0-100 to 0-1
                promoMsg.setText("Promo applied: " + promo.getHeadline() + " (" + (int)promo.getDiscountPercentage() + "% OFF)");
                promoMsg.setStyle("-fx-text-fill: green;");
            } else {
                currentPromoId = 0;
                currentDiscount = 0;
                promoMsg.setText("Invalid promo code!");
                promoMsg.setStyle("-fx-text-fill: red;");
            }
            updateTotalLabels();
        });
        
        promoBox.getChildren().addAll(new Label("Promo Code:"), promoField, applyPromoBtn);
        
        // ===== TOTAL SECTION =====
        VBox totalBox = new VBox(5);
        totalBox.setStyle("-fx-padding: 10; -fx-background-color: #f5f5f5; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        totalLabel = new Label("Subtotal: Rp 0");
        totalLabel.setStyle("-fx-font-size: 14px;");
        
        discountLabel = new Label("Discount: Rp 0");
        discountLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;");
        
        finalTotalLabel = new Label("Total: Rp 0");
        finalTotalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        totalBox.getChildren().addAll(totalLabel, discountLabel, finalTotalLabel);
        updateTotalLabels();

        // ===== CHECKOUT =====
        checkoutBtn.setOnAction(e -> {
            CheckoutController checkoutController = new CheckoutController();
            
            // Hitung final total untuk validasi balance
            double subtotal = cartController.getTotalPrice(customer.getIdUser());
            double discountAmount = subtotal * currentDiscount;
            double finalTotal = subtotal - discountAmount;

            int result = checkoutController.processCheckout(customer, currentPromoId, finalTotal);
            
            if (result == CheckoutController.SUCCESS) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Checkout Success");
                alert.setHeaderText("Checkout Completed");
                alert.setContentText("Your order has been successfully processed!\nTotal paid: Rp " + String.format("%.0f", finalTotal));
                alert.showAndWait();

                new CustomerDashboardView(stage, customer);
            } else if (result == CheckoutController.BALANCE_NOT_SUFFICIENT) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Checkout Failed");
                alert.setHeaderText("Insufficient Balance");
                alert.setContentText("Your balance is not sufficient!\n\nYour balance: Rp " + String.format("%.0f", customer.getBalance()) + 
                                   "\nTotal price: Rp " + String.format("%.0f", finalTotal) +
                                   "\n\nPlease top up your balance first.");
                alert.show();
            } else if (result == CheckoutController.STOCK_NOT_SUFFICIENT) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Checkout Failed");
                alert.setHeaderText("Stock Not Sufficient");
                alert.setContentText("Some items in your cart exceed available stock!");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Checkout Failed");
                alert.setHeaderText("Order Failed");
                alert.setContentText("Failed to process your order. Please try again.");
                alert.show();
            }
        });


        // ===== BACK =====
        backBtn.setOnAction(e -> {
            new CustomerDashboardView(stage, customer);
        });

        HBox btnBox = new HBox(10, updateBtn, deleteBtn, checkoutBtn, backBtn);

        VBox root = new VBox(10, table, qtyField, btnBox, msg, promoBox, promoMsg, totalBox);
        root.setPadding(new javafx.geometry.Insets(15));

        stage.setScene(new Scene(root, 550, 500));
        stage.setTitle("Cart");
        stage.show();
    }
    
    // Update total labels berdasarkan cart dan promo
    private void updateTotalLabels() {
        double subtotal = cartController.getTotalPrice(customer.getIdUser());
        double discountAmount = subtotal * currentDiscount;
        double finalTotal = subtotal - discountAmount;
        
        totalLabel.setText(String.format("Subtotal: Rp %.0f", subtotal));
        discountLabel.setText(String.format("Discount: - Rp %.0f", discountAmount));
        finalTotalLabel.setText(String.format("Total: Rp %.0f", finalTotal));
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
        
        // Update total labels setiap refresh table
        if (totalLabel != null) {
            updateTotalLabels();
        }
    }
}
