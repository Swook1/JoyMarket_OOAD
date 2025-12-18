package main;

import java.sql.Connection;

import database.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.LoginView;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
		new Main();
	}

	@Override
	public void start(Stage stage) {
		// TODO Auto-generated method stub
		new LoginView(stage);
	}

	public Main() {
		
//		Sedikit dokumentasi aja dari gw di Main()
//		DBConnection di package database, terus ini semua dokumentasi kecil cara panggil model sama DAO nya
//		Notes : buat setiap id gw ubah dari String jadi int (gatau dibolehin atau gk tapi yasudahlah)

//		User user = new User(0, "Melvin Sven Kho", "melvin@gmail.com", "melvin1432", "0852", "Batam", "customer");
//		boolean success = userDAO.register(user);

//		if (success) {
//		    System.out.println("User registered with ID: " + user.getIdUser());
//		} else {
//		    System.out.println("Registration failed");
//		}
//		CustomerDAO customerDAO = new CustomerDAO();
//		Customer customer = customerDAO.getCustomer(10);

//		userDAO.updateProfile(10, "Rayyan", "Rayyan@gmail.com");
//		if (customer != null) {
//	        System.out.println("Customer Found: " + customer.getFullName() + customer.getAddress());
//	        System.out.println("Balance: " + customer.getBalance());
//	    } else {
//	        System.out.println("Customer not found");
//	    }
		
//		Coba buat dan get product
//		Product product = new Product(0, "Playstation", 50, 10, "Gaming");
//		ProductDAO productDAO = new ProductDAO();
//		Product product = productDAO.getProduct(1);

//		Coba buat cartitem, updateCount dan delete
//		CartItem item = new CartItem(10, 1, 6);
//		System.out.println(item.getIdCustomer());
//		System.out.println(item.getIdProduct());
//		CartItemDAO cartDAO = new CartItemDAO();
//		cartDAO.addToCart(item);
//		cartDAO.updateCount(item, 1);
//		cartDAO.deleteCartItem(item);
		
//		Coba buat admin
//		UserDAO userDAO = new UserDAO();
//		User adminUser = new User(0, "Admin", "admin@gmail.com", "admin123", "0823", "Batam", "admin");
//		boolean success = userDAO.register(adminUser);
		
//		coba getAdmin
//		UserDAO userDAO = new UserDAO();
//		AdminDAO adminDAO = new AdminDAO();
//		
//		Admin admin = adminDAO.getAdmin(12);
//		System.out.println(admin.getEmergencyContact());
		
//		Coba buat courier
//		UserDAO userDAO = new UserDAO();
//		User courier = new User(0, "HondaCourier", "honda@gmail.com", "honda123", "0812", "Bali", "courier");
//		boolean success = userDAO.register(courier);
		
//		Coba get courier
//		UserDAO userDAO = new UserDAO();
//		CourierDAO courierDAO = new CourierDAO();
//		
//		Courier courier = courierDAO.getCourier(13);
//		System.out.println(courier.getVehicleType());
		
// 		Coba get promo
//		PromoDAO promoDAO = new PromoDAO();
//		Promo promo = promoDAO.getPromo(1);
//		System.out.println(promo.getHeadline());
		
//		Coba Create order header, edit order header, get order header, get customer from order header
//		OrderHeaderDAO orderHeaderDAO = new OrderHeaderDAO();
//		orderHeaderDAO.createOrder(9, 1);
//		orderHeaderDAO.editOrderHeaderStatus(1, "Finished");
//		System.out.println(orderHeaderDAO.getOrderHeader(1).getStatus());
//		Customer customer = orderHeaderDAO.getCustomerOrderHeader(1);
//		System.out.println(customer.getFullName());
		
//		Coba get, edit, create delivery
//		DeliveryDAO deliveryDAO = new DeliveryDAO();
//		deliveryDAO.createDelivery(1, 13);
//		deliveryDAO.editDeliveryStatus(1, 13, "Pending");
//		Delivery delivery = deliveryDAO.getDelivery(1, 13);
//		System.out.println(delivery.getStatus());
		
//		Coba create, get order detail, get customer from order detail
//		OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
//		orderDetailDAO.createOrderDetail(1, 1, 2);
//		OrderDetail orderDetail = orderDetailDAO.getOrderDetail(1, 1);
//		System.out.println(orderDetail.getQty());
//		Customer customerDetail = orderDetailDAO.getCustomerOrderDetail(1, 1);
//		System.out.println(customerDetail.getBalance());
		
	}
}
