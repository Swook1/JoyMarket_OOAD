package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Customer;

public class CheckoutView {

    private Stage stage;
    private Customer customer;

    public CheckoutView(Stage stage, Customer customer) {
        this.stage = stage;
        this.customer = customer;
        initUI();
    }

    private void initUI() {
        Label title = new Label("Checkout");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label info = new Label("Checkout Success");

        Button backBtn = new Button("Back to Cart");
        backBtn.setOnAction(e -> {
            new CartView(stage, customer);
        });

        VBox root = new VBox(15, title, info, backBtn);
        root.setPadding(new javafx.geometry.Insets(20));

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Checkout");
        stage.show();
    }
}
