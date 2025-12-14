package view;

import controller.CustomerController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

public class EditProfileView {

    private Stage stage;
    private Customer customer;

    public EditProfileView(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
        initialize();
    }

    private void initialize() {
        Label titleLabel = new Label("Edit Profile");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Full Name:");
        TextField nameField = new TextField(customer.getFullName());

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField(customer.getPhone());

        Label addressLabel = new Label("Address:");
        TextField addressField = new TextField(customer.getAddress());

        Button saveButton = new Button("Save");
        Button backButton = new Button("Back");

        Label messageLabel = new Label();

        saveButton.setOnAction(e -> {
            String fullName = nameField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            if (fullName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                messageLabel.setText("All fields must be filled!");
                return;
            }

            CustomerController controller = new CustomerController();
            boolean success = controller.updateProfile(
                    customer.getIdUser(),
                    fullName,
                    phone,
                    address
            );

            if (success) {
                messageLabel.setText("Profile updated successfully!");

                customer.setFullName(fullName);
                customer.setPhone(phone);
                customer.setAddress(address);
            } else {
                messageLabel.setText("Failed to update profile!");
            }
        });

        backButton.setOnAction(e -> {
            new CustomerDashboardView(stage, customer);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                nameLabel,
                nameField,
                phoneLabel,
                phoneField,
                addressLabel,
                addressField,
                saveButton,
                backButton,
                messageLabel
        );

        stage.setScene(new Scene(layout, 350, 350));
        stage.setTitle("Edit Profile");
        stage.show();
    }
}
