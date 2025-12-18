package model;

/*
  Product - model buat data produk yang dijual
  Ada nama, harga, stok, sama kategori
 */
public class Product {
    private int idProduct;
    private String name;
    private double price;
    private int stock;         // jumlah stok yang tersedia
    private String category;   // kategori produk 
	public Product(int idProduct, String name, double price, int stock, String category) {
		super();
		this.idProduct = idProduct;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.category = category;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
    
    
}
