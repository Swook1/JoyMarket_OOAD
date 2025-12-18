package view;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Product;
import model.User;

/*
  AdminDashboardView - dashboard admin
  Ada 2 tab: Manage Products (edit stock) & Assign Orders (assign courier)
  Pake TableView buat nampilin data
 */
public class AdminDashboardView {

    private Stage stage;
    private User admin;
    private AdminController adminController;
    
    private TableView<Product> tableProduct;
    private TextField stockField; 

    public AdminDashboardView(Stage stage, User admin) {
        this.stage = stage;
        this.admin = admin;
        this.adminController = new AdminController();
        
        initUI();
    }

    private void initUI() {
        BorderPane root = new BorderPane();
        
        // === TOP BAR ===
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0;");
        
        Label welcomeLabel = new Label("Welcome, Admin " + admin.getFullName());
        welcomeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginView(stage));
        
        topBar.getChildren().addAll(welcomeLabel, spacer, logoutBtn);
        root.setTop(topBar);

        // === CENTER (TabPane) ===
        TabPane tabPane = new TabPane();
        
        // Tab 1: Manage Products
        Tab tabProduct = new Tab("Manage Products");
        tabProduct.setContent(createProductTab());
        tabProduct.setClosable(false);
        
        // Tab 2: Assign Orders
        Tab tabOrder = new Tab("Assign Orders");
        tabOrder.setContent(createOrderTab());
        tabOrder.setClosable(false);
        
        tabPane.getTabs().addAll(tabProduct, tabOrder);
        root.setCenter(tabPane);

        // Final Setup
        Scene scene = new Scene(root, 700, 500); 
        stage.setScene(scene);
        stage.setTitle("JoymarKet - Admin Dashboard");
        stage.show();
    }

    // TAB 1: PRODUCT MANAGEMENT
    private VBox createProductTab() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label title = new Label("Product Stock Management");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // 1. Setup Table
        tableProduct = new TableView<>();
        
        // Kolom ID
        TableColumn<Product, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idProduct"));
        colId.setPrefWidth(50);
        
        // Kolom Name
        TableColumn<Product, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(200);
        
        // Kolom Category 
        TableColumn<Product, String> colCat = new TableColumn<>("Category");
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCat.setPrefWidth(100);
        
        // Kolom Stock
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(80);
        
        tableProduct.getColumns().addAll(colId, colName, colCat, colStock);
        
        refreshTable();

        // 2. Form Update
        HBox formBox = new HBox(10);
        formBox.setPadding(new Insets(10, 0, 0, 0));
        
        stockField = new TextField();
        stockField.setPromptText("Enter new stock qty");
        
        Button updateBtn = new Button("Update Stock");
        Label msgLabel = new Label();
        
        // Logic Update
        updateBtn.setOnAction(e -> {
            Product selected = tableProduct.getSelectionModel().getSelectedItem();
            
            // Validasi seleksi
            if (selected == null) {
                msgLabel.setText("Please select a product first!");
                msgLabel.setStyle("-fx-text-fill: red;");
                return;
            }
            
            // Panggil Controller
            String result = adminController.editProductStock(selected.getIdProduct(), stockField.getText());
            msgLabel.setText(result);
            
            // Cek sukses/gagal via String return dari controller
            if (result.startsWith("Success")) {
                msgLabel.setStyle("-fx-text-fill: green;");
                refreshTable(); // REFRESH TABEL BIAR STOCK UPDATE
                stockField.clear();
            } else {
                msgLabel.setStyle("-fx-text-fill: red;");
            }
        });

        formBox.getChildren().addAll(new Label("New Stock:"), stockField, updateBtn);
        layout.getChildren().addAll(title, tableProduct, formBox, msgLabel);
        
        return layout;
    }

    // Method helper buat ambil ulang data dari DB
    private void refreshTable() {
        // Mengambil list terbaru dari controller dan masukin ke tabel
        tableProduct.setItems(FXCollections.observableArrayList(adminController.getAllProducts()));
    }

    // TAB 2: ORDER ASSIGNMENT
    private VBox createOrderTab() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));

        Label title = new Label("Assign Order to Courier");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label info = new Label("Use this form to assign a courier to an existing order.");
        
        // Form Input
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("Order ID");
        orderIdField.setMaxWidth(300);
        
        TextField courierIdField = new TextField();
        courierIdField.setPromptText("Courier ID");
        courierIdField.setMaxWidth(300);
        
        Button assignBtn = new Button("Assign Courier");
        Label msgLabel = new Label();
        
        assignBtn.setOnAction(e -> {
            try {
                if (orderIdField.getText().isEmpty() || courierIdField.getText().isEmpty()) {
                    msgLabel.setText("All fields must be filled!");
                    msgLabel.setStyle("-fx-text-fill: red;");
                    return;
                }

                int idOrder = Integer.parseInt(orderIdField.getText());
                int idCourier = Integer.parseInt(courierIdField.getText());
                
                String res = adminController.assignCourierToOrder(idOrder, idCourier);
                msgLabel.setText(res);
                
                if (res.startsWith("Success")) {
                    msgLabel.setStyle("-fx-text-fill: green;");
                    orderIdField.clear();
                    courierIdField.clear();
                } else {
                    msgLabel.setStyle("-fx-text-fill: red;");
                }
                
            } catch (NumberFormatException ex) {
                msgLabel.setText("ID must be numeric!");
                msgLabel.setStyle("-fx-text-fill: red;");
            }
        });

        layout.getChildren().addAll(title, info, 
            new Label("Order ID:"), orderIdField, 
            new Label("Courier ID:"), courierIdField, 
            assignBtn, msgLabel
        );
        return layout;
    }
}