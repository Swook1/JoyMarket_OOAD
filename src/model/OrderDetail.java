package model;

public class OrderDetail {
	private int idOrder;
    private int idProduct;
    private int qty;

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
