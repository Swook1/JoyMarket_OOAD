package model;

/*
  CartItem - representasi item di shopping cart
  Nyimpen produk apa, berapa banyak, punya customer mana
 */
public class CartItem {
	private int idCustomer;  // customer yang punya cart ini
	private int idProduct;   // produk yang ada di cart
	private int count;       // jumlah produk
	public CartItem(int idCustomer, int idProduct, int count) {
		super();
		this.idCustomer = idCustomer;
		this.idProduct = idProduct;
		this.count = count;
	}
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	 
	
}
