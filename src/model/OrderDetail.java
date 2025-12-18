package model;

/*
  OrderDetail - detail produk yang ada di sebuah order
  Satu order bisa punya banyak order detail (1 to many dengan OrderHeader)
  Nyimpen produk apa aja yang dibeli dan berapa qty nya
 */
public class OrderDetail {
	private int idOrder;     // order mana yang punya detail ini
    private int idProduct;   // produk apa yang dibeli
    private int qty;         // jumlah produk yang dibeli

    public OrderDetail() {}

    public OrderDetail(int idOrder, int idProduct, int qty) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.qty = qty;
    }

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
    
    
}
