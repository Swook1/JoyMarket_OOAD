package model;

import java.sql.Date;

/*
  OrderHeader - data utama dari sebuah order/pesanan
  Isinya info customer, promo yang dipake, status, tanggal order
  Detail produknya ada di OrderDetail (relasi 1 to many)
 */
public class OrderHeader {
	private int idOrder;
    private int idCustomer;
    private int idPromo;        // ID promo yang dipake 
    private String status;      // status 
    private Date orderDate;     // tanggal order dibuat

    public OrderHeader() {}

    public OrderHeader(int idOrder, int idCustomer, int idPromo,
                       String status, Date orderDate) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idPromo = idPromo;
        this.status = status;
        this.orderDate = orderDate;
    }

	public int getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdPromo() {
		return idPromo;
	}

	public void setIdPromo(int idPromo) {
		this.idPromo = idPromo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
    
    
}
