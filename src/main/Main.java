package main;

import controller.*;
import database.*;
import model.*;

import java.sql.Connection;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        System.out.println("===== START TEST CONTROLLERS / DAO / MODELS (FULL TEST WITH CLEANUP) =====");

        // --- Inisialisasi controllers & DAOs ---
        AuthController authCtrl = new AuthController();
        ProductController productCtrl = new ProductController();
        CartController cartCtrl = new CartController();
        CheckoutController checkoutCtrl = new CheckoutController();
        OrderController orderCtrl = new OrderController();
        CourierController courierCtrl = new CourierController();

        UserDatabase userDb = new UserDatabase();
        CustomerDatabase custDb = new CustomerDatabase();
        ProductDatabase prodDb = new ProductDatabase();
        CartItemDatabase cartDb = new CartItemDatabase();
        OrderDatabase orderDb = new OrderDatabase();
        CourierDatabase courierDb = new CourierDatabase();

        // Test identifiers (fixed for easy inspection)
        String userId = "U_TEST1";
        String email = "testuser1@example.com";
        String prodId = "P_TEST1";
        String orderId = "O_TEST1";
        String courierId = "CR_TEST1";

        try {
            // -------------------------
            // 1) REGISTER (ensure user + customer)
            // -------------------------
            System.out.println("\n-- REGISTER (ensure user + customer) --");
            boolean reg = authCtrl.register(userId, "Test User 1", email, "pass123", "081234567890", "Jl. Test 1");
            System.out.println("register returned: " + reg);

            // Ensure Customer row exists (parent for CartItem)
            boolean customerExists = custDb.getCustomers().stream().anyMatch(c -> c.getIdUser().equals(userId));
            if (!customerExists) {
                System.out.println("Customer row missing — inserting customer row now.");
                Customer ctemp = new Customer();
                ctemp.setIdUser(userId);
                ctemp.setBalance(0.0);
                boolean insertedCust = custDb.insertCustomer(ctemp);
                System.out.println("insertCustomer returned: " + insertedCust);
            } else {
                System.out.println("Customer row exists.");
            }

            // -------------------------
            // 2) PRODUCT CRUD (ensure product exists)
            // -------------------------
            System.out.println("\n-- ENSURE PRODUCT EXISTS --");
            boolean prodExists = prodDb.getAllProducts().stream().anyMatch(p -> p.getIdProduct().equals(prodId));
            if (!prodExists) {
                System.out.println("Product missing — inserting product row now.");
                Product ptemp = new Product();
                ptemp.setIdProduct(prodId);
                ptemp.setName("Test Product 1");
                ptemp.setPrice(50000.0);
                ptemp.setStock(10);
                ptemp.setCategory("TestCat");
                boolean insertedProd = productCtrl.addProduct(ptemp);
                System.out.println("insertProduct returned: " + insertedProd);
            } else {
                System.out.println("Product exists.");
            }

            // -------------------------
            // 3) ADD TO CART (child of Customer & Product)
            // -------------------------
            System.out.println("\n-- ADD TO CART --");
            boolean addedCart = cartCtrl.addToCart(userId, prodId, 2);
            System.out.println("addToCart returned: " + addedCart);

            System.out.println("\n-- LIST CART ITEMS --");
            cartCtrl.getCartItems(userId).forEach(ci -> {
                System.out.println("cartItem: customer=" + ci.getIdCustomer() + " product=" + ci.getIdProduct() + " count=" + ci.getCount());
            });

            // -------------------------
            // 4) CHECK CUSTOMER BALANCE (top up if needed)
            // -------------------------
            System.out.println("\n-- CHECK CUSTOMER BALANCE --");
            Customer cust = custDb.getCustomers().stream().filter(c -> c.getIdUser().equals(userId)).findFirst().orElse(null);
            if (cust == null) {
                System.out.println("Customer not found (unexpected). Aborting checkout.");
            } else {
                System.out.println("Current balance: " + cust.getBalance());
                // Ensure enough balance for checkout (simple heuristic)
                double requiredBalance = 200000.0;
                if (cust.getBalance() < requiredBalance) {
                    System.out.println("Top-up needed. Updating balance to " + requiredBalance);
                    boolean up = custDb.updateBalance(userId, requiredBalance);
                    System.out.println("updateBalance returned: " + up);
                } else {
                    System.out.println("Balance sufficient for test.");
                }
            }

            // -------------------------
            // 5) CHECKOUT (creates OrderHeader + OrderDetail via controllers/DAOs)
            // -------------------------
            System.out.println("\n-- CHECKOUT --");
            boolean checkoutOk = checkoutCtrl.checkout(orderId, userId, null);
            System.out.println("checkout returned: " + checkoutOk);

            // Verify order exists before assigning courier
            boolean orderExists = orderDb.getOrdersByCustomer(userId).stream().anyMatch(o -> o.getIdOrder().equals(orderId));
            System.out.println("orderExists after checkout: " + orderExists);

            // -------------------------
            // 6) COURIER (ensure courier exists then assign to order)
            // -------------------------
            if (!orderExists) {
                System.out.println("Order header not found — skipping courier assignment.");
            } else {
                System.out.println("\n-- ENSURE COURIER EXISTS --");
                boolean courierExists = courierDb.getAllCouriers().stream().anyMatch(c -> c.getIdCourier().equals(courierId));
                if (!courierExists) {
                    System.out.println("Courier missing — inserting courier row now.");
                    Courier ctemp = new Courier();
                    ctemp.setIdCourier(courierId);
                    ctemp.setVehicleType("Motor");
                    ctemp.setVehiclePlate("B1234CR");
                    boolean insertedCourier = courierCtrl.addCourier(ctemp);
                    System.out.println("insertCourier returned: " + insertedCourier);
                } else {
                    System.out.println("Courier exists.");
                }

                System.out.println("\n-- ASSIGN COURIER TO ORDER --");
                boolean assigned = courierCtrl.assignCourier(orderId, courierId);
                System.out.println("assignCourier returned: " + assigned);

                System.out.println("\n-- UPDATE DELIVERY STATUS --");
                boolean delStatus = courierCtrl.updateDeliveryStatus(orderId, "On Delivery");
                System.out.println("updateDeliveryStatus returned: " + delStatus);
            }

            // -------------------------
            // 7) SHOW ORDER HISTORY
            // -------------------------
            System.out.println("\n-- ORDER HISTORY --");
            orderCtrl.getOrderHistory(userId).forEach(oh -> {
                System.out.println("order: " + oh.getIdOrder() + " | status=" + oh.getStatus() + " | total=" + oh.getTotalAmount());
            });

            // -------------------------
            // 8) CLEANUP (ordered, best-effort)
            // -------------------------
            System.out.println("\n-- CLEANUP (ordered, best-effort) --");
            Connection con = null;
            try {
                con = DatabaseConnection.getConnection();

                // 1) Delete OrderDetail by orderId
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM OrderDetail WHERE idOrder = ?")) {
                    ps.setString(1, orderId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted OrderDetail rows: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete OrderDetail!");
                    e.printStackTrace();
                }

                // 2) Delete Delivery rows related to orderId
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM Delivery WHERE idOrder = ?")) {
                    ps.setString(1, orderId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted Delivery rows for order: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete Delivery by order!");
                    e.printStackTrace();
                }

                // 3) Delete OrderHeader
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM OrderHeader WHERE idOrder = ?")) {
                    ps.setString(1, orderId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted OrderHeader rows: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete OrderHeader!");
                    e.printStackTrace();
                }

                // 4) Clear cart items (just in case)
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM CartItem WHERE idCustomer = ?")) {
                    ps.setString(1, userId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted CartItem rows: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to clear CartItem!");
                    e.printStackTrace();
                }

                // 5) Delete OrderDetail rows that reference product (in case product used in other orders)
                //    If you prefer to keep historical order details, SKIP this step.
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM OrderDetail WHERE idProduct = ?")) {
                    ps.setString(1, prodId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted OrderDetail rows for product: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete OrderDetail by product!");
                    e.printStackTrace();
                }

                // 6) Delete Product
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM Product WHERE idProduct = ?")) {
                    ps.setString(1, prodId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted Product rows: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete Product!");
                    e.printStackTrace();
                }

                // 7) Delete Delivery rows referencing this courier (so we can drop courier)
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM Delivery WHERE idCourier = ?")) {
                    ps.setString(1, courierId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted Delivery rows for courier: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete Delivery by courier!");
                    e.printStackTrace();
                }

                // 8) Delete Courier
                try (java.sql.PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM Courier WHERE idCourier = ?")) {
                    ps.setString(1, courierId);
                    int d = ps.executeUpdate();
                    System.out.println("Deleted Courier rows: " + d);
                } catch (Exception e) {
                    System.out.println("Failed to delete Courier!");
                    e.printStackTrace();
                }

            } catch (Exception e) {
                System.out.println("Cleanup unexpected error");
                e.printStackTrace();
            }

            System.out.println("\n===== TEST FINISHED (check DB for results) =====");

        } catch (Exception e) {
            System.out.println("Unexpected error during tests!");
            e.printStackTrace();
        }
    }
}
