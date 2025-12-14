package view;

import controller.CustomerController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

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
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

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
            try {
                double amount = Double.parseDouble(amountField.getText());

                // VALIDATION 
                if (amount <= 0) {
                    messageLabel.setText("Amount must be greater than 0!");
                    return;
                }

                CustomerController controller = new CustomerController();
                boolean success = controller.topUpBalance(customer, amount);

                if (success) {
                    messageLabel.setText("Top up successful!");
                    customer.setBalance(customer.getBalance() + amount);
                    currentBalanceLabel.setText(
                            "Current Balance: Rp " + customer.getBalance()
                    );
                    amountField.clear();
                } else {
                    messageLabel.setText("Top up failed!");
                }

            } catch (NumberFormatException ex) {
                messageLabel.setText("Amount must be a number!");
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
