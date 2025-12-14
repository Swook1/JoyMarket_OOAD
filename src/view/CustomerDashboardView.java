package view;

import controller.CustomerController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

public class CustomerDashboardView {

    private Stage stage;
    private Customer customer;
    private CustomerController customerController;

    public CustomerDashboardView(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
        this.customerController = new CustomerController();

        initUI();
    }

    private void initUI() {

        Label titleLabel = new Label("Customer Dashboard");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Name   : " + customer.getFullName());
        Label emailLabel = new Label("Email  : " + customer.getEmail());
        Label phoneLabel = new Label("Phone  : " + customer.getPhone());
        Label addressLabel = new Label("Address: " + customer.getAddress());
        Label balanceLabel = new Label("Balance: " + customer.getBalance());

        Button editProfileBtn = new Button("Edit Profile");
        Button topUpBtn = new Button("Top Up Balance");
        Button logoutBtn = new Button("Logout");

        // === EVENT HANDLER ===
        editProfileBtn.setOnAction(e -> {
            new EditProfileView(stage, customer);
        });

        topUpBtn.setOnAction(e -> {
            new TopUpBalanceView(stage, customer);
        });

        logoutBtn.setOnAction(e -> {
            new LoginView(stage);
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(
                titleLabel,
                nameLabel,
                emailLabel,
                phoneLabel,
                addressLabel,
                balanceLabel,
                editProfileBtn,
                topUpBtn,
                logoutBtn
        );

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Customer Dashboard");
        stage.show();
    }
}
