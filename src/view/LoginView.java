package view;

import controller.UserController;
import database.CustomerDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;
import model.User;

public class LoginView {

    private Stage stage;
    private UserController userController;

    public LoginView(Stage stage) {
        this.stage = stage;
        this.userController = new UserController();

        Label title = new Label("Login");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Label messageLabel = new Label();

        Button loginBtn = new Button("Login");
        Button registerBtn = new Button("Register"); 

        // LOGIN ACTION
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            User user = userController.login(email, password);

            if (user != null) {
                messageLabel.setText("Login success!");
                if (user.getRole().equalsIgnoreCase("Customer")) {
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer customer = customerDAO.getCustomer(user.getIdUser());

                    if (customer != null) {
                        new CustomerDashboardView(stage, customer);
                    }
                }
                else if (user.getRole().equalsIgnoreCase("Admin")) {
                    // Arahkan ke Admin Dashboard
                    new AdminDashboardView(stage, user);
                } 
                else if (user.getRole().equalsIgnoreCase("Courier")) {
                    // Arahkan ke Courier Dashboard
                    new CourierDashboardView(stage, user);
                }

            } else {
                messageLabel.setText("Invalid email or password");
            }
        });

        // REGISTER ACTION
        registerBtn.setOnAction(e -> {
            new RegisterView(stage);
        });

        VBox root = new VBox(10,
                title,
                emailField,
                passwordField,
                loginBtn,
                registerBtn,
                messageLabel
        );

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle("Login");
        stage.show();
    }
}
