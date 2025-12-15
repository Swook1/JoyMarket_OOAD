package view;

import controller.CourierController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class CourierDashboardView {

    private Stage stage;
    private User courier;
    private CourierController courierController;

    public CourierDashboardView(Stage stage, User courier) {
        this.stage = stage;
        this.courier = courier;
        this.courierController = new CourierController();
        
        initUI();
    }

    private void initUI() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        // Header
        Label title = new Label("Courier Dashboard");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label nameLabel = new Label("Courier: " + courier.getFullName());

        // Form Update Status
        // Idealnya pake TableView, tapi ini versi simple input ID manual biar gak error 
        // kalau Controller lu belum ada method getAllDeliveries().
        Label lblForm = new Label("Update Delivery Status");
        lblForm.setStyle("-fx-font-weight: bold;");
        
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Enter Order ID to update");
        
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Pending", "In Progress", "Delivered");
        statusCombo.setPromptText("Select New Status");
        
        Button updateBtn = new Button("Update Status");
        Label msgLabel = new Label();
        
        updateBtn.setOnAction(e -> {
            String idText = orderIdField.getText();
            String status = statusCombo.getValue();
            
            if(idText.isEmpty() || status == null) {
                msgLabel.setText("Please fill Order ID and Status!");
                return;
            }
            
            try {
                int idOrder = Integer.parseInt(idText);
                // Panggil Controller
                String result = courierController.updateDeliveryStatus(idOrder, courier.getIdUser(), status);
                msgLabel.setText(result);
            } catch (NumberFormatException ex) {
                msgLabel.setText("Order ID must be numeric!");
            }
        });
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginView(stage));

        root.getChildren().addAll(
            title, nameLabel, new Separator(),
            lblForm, orderIdField, statusCombo, updateBtn, msgLabel,
            new Separator(), logoutBtn
        );

        stage.setScene(new Scene(root, 400, 450));
        stage.setTitle("Courier Dashboard");
        stage.show();
    }
}