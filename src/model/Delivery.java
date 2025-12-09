package model;

public class Delivery {
	
	private int idDelivery;
    private int idOrder;
    private int idCourier;
    private String status;
	public Delivery(int idDelivery, int idOrder, int idCourier, String status) {
		super();
		this.idDelivery = idDelivery;
		this.idOrder = idOrder;
		this.idCourier = idCourier;
		this.status = status;
	}
	public int getIdDelivery() {
		return idDelivery;
	}
	public void setIdDelivery(int idDelivery) {
		this.idDelivery = idDelivery;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	public int getIdCourier() {
		return idCourier;
	}
	public void setIdCourier(int idCourier) {
		this.idCourier = idCourier;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
