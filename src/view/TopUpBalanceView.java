package view;

import controller.CustomerController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

/*
  TopUpBalanceView - halaman top up saldo customer
  Input jumlah yang mau di-top up, saldo langsung bertambah
  Validasi amount harus > 0 & numeric
 */
public class TopUpBalanceView {

    private Stage stage;
    private Customer customer;

    public TopUpBalanceView(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
        initialize();
    }

    private void initialize() {
        Label titleLabel = new Label("Top Up Balance");

        Label currentBalanceLabel = new Label(
                "Current Balance: Rp " + customer.getBalance()
        );

        Label amountLabel = new Label("Top Up Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button topUpButton = new Button("Top Up");
        Button backButton = new Button("Back");

        Label messageLabel = new Label();

        topUpButton.setOnAction(e -> {
            // Validasi: Amount must be filled
            if (amountField.getText().isEmpty() || amountField.getText().trim().isEmpty()) {
                messageLabel.setText("Amount must be filled!");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Validasi: Must be numeric
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
            } catch (NumberFormatException ex) {
                messageLabel.setText("Amount must be numeric!");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Validasi: Minimum 10.000
            if (amount < 10000) {
                messageLabel.setText("Minimum top up amount is Rp10.000!");
                messageLabel.setStyle("-fx-text-fill: red;");
                return;
            }

            // Process top up
            CustomerController controller = new CustomerController();
            boolean success = controller.topUpBalance(customer, amount);

            if (success) {
                messageLabel.setText("Top up successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                customer.setBalance(customer.getBalance() + amount);
                currentBalanceLabel.setText(
                        "Current Balance: Rp" + String.format("%,.0f", customer.getBalance())
                );
                amountField.clear();
            } else {
                messageLabel.setText("Top up failed!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        });

        backButton.setOnAction(e -> {
            new CustomerDashboardView(stage, customer);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                currentBalanceLabel,
                amountLabel,
                amountField,
                topUpButton,
                backButton,
                messageLabel
        );

        stage.setScene(new Scene(layout, 350, 300));
        stage.setTitle("Top Up Balance");
        stage.show();
    }
}
