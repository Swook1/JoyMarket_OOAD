package view;

import controller.CourierController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Delivery;
import model.User;

/*
  CourierDashboardView - dashboard courier
  Nampilin list delivery yang di-assign ke courier ini
  Bisa update status delivery (Pending -> In Progress -> Delivered)
 */
public class CourierDashboardView {

    private Stage stage;
    private User courier;
    private CourierController controller;

    private TableView<Delivery> tableDelivery;
    private ComboBox<String> statusCombo;
    private Label msgLabel;

    public CourierDashboardView(Stage stage, User courier) {
        this.stage = stage;
        this.courier = courier;
        this.controller = new CourierController();
        initUI();
    }

    private void initUI() {
        BorderPane root = new BorderPane();

        // ================= TOP BAR =================
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");

        Label welcome = new Label("Welcome, Courier " + courier.getFullName());
        welcome.setStyle("-fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginView(stage));

        topBar.getChildren().addAll(welcome, spacer, logoutBtn);
        root.setTop(topBar);

        // ================= CENTER =================
        TabPane tabPane = new TabPane();
        Tab deliveryTab = new Tab("My Deliveries");
        deliveryTab.setClosable(false);
        deliveryTab.setContent(createDeliveryTab());

        tabPane.getTabs().add(deliveryTab);
        root.setCenter(tabPane);

        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("JoymarKet - Courier Dashboard");
        stage.show();
    }

    private VBox createDeliveryTab() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label title = new Label("My Delivery List");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // ===== TABLE =====
        tableDelivery = new TableView<>();

        TableColumn<Delivery, Integer> colOrderId =
                new TableColumn<>("Order ID");
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
        colOrderId.setPrefWidth(100);

        TableColumn<Delivery, String> colStatus =
                new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(150);

        tableDelivery.getColumns().addAll(colOrderId, colStatus);
        refreshTable();

        // ===== FORM =====
        statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Pending", "In Progress", "Delivered");
        statusCombo.setPromptText("Select Status");

        Button updateBtn = new Button("Update Status");
        msgLabel = new Label();

        updateBtn.setOnAction(e -> handleUpdate());

        HBox form = new HBox(10, statusCombo, updateBtn);
        layout.getChildren().addAll(title, tableDelivery, form, msgLabel);

        return layout;
    }

    private void handleUpdate() {
        Delivery selected = tableDelivery.getSelectionModel().getSelectedItem();

        if (selected == null) {
            msgLabel.setText("Please select a delivery first!");
            msgLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        String status = statusCombo.getValue();
        String res = controller.updateDeliveryStatus(
                selected.getIdOrder(),
                courier.getIdUser(),
                status
        );

        msgLabel.setText(res);
        msgLabel.setStyle(res.startsWith("Success") ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        refreshTable();
    }

    private void refreshTable() {
        tableDelivery.setItems(
            FXCollections.observableArrayList(
                controller.getDeliveriesByCourier(courier.getIdUser())
            )
        );
    }
}
