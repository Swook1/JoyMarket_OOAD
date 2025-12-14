package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView {

    private Stage stage;

    public RegisterView(Stage stage) {
        this.stage = stage;
        initialize();
    }

    private void initialize() {

        Label titleLabel = new Label("Register");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Full Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        Button registerButton = new Button("Register");
        Button backButton = new Button("Back to Login");

        Label messageLabel = new Label();

        registerButton.setOnAction(e -> {
            String fullName = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            // ================= VALIDATION =================
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()
                    || phone.isEmpty() || address.isEmpty()) {
                messageLabel.setText("All fields must be filled!");
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                messageLabel.setText("Invalid email format!");
                return;
            }

            if (password.length() < 5) {
                messageLabel.setText("Password must be at least 5 characters!");
                return;
            }

            if (!phone.matches("[0-9]+")) {
                messageLabel.setText("Phone must be numeric!");
                return;
            }
            // =================================================

            UserController controller = new UserController();
            boolean success = controller.register(
                    fullName,
                    email,
                    password,
                    phone,
                    address
            );

            if (success) {
                messageLabel.setText("Register success! Please login.");
            } else {
                messageLabel.setText("Email already exists!");
            }
        });

        backButton.setOnAction(e -> {
            new LoginView(stage);
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(
                titleLabel,
                nameField,
                emailField,
                passwordField,
                phoneField,
                addressField,
                registerButton,
                backButton,
                messageLabel
        );

        stage.setScene(new Scene(layout, 350, 400));
        stage.setTitle("Register");
        stage.show();
    }
}
