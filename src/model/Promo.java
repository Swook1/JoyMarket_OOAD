package model;

/*
  Promo - model untuk kode promo/diskon
  Customer bisa input kode promo pas checkout
 */
public class Promo {
	private int idPromo;
    private String code;                // kode promo 
    private String headline;            // judul/deskripsi promo
    private double discountPercentage;  // persentase diskon 

    public Promo() {}

    public Promo(int idPromo, String code, String headline, double discountPercentage) {
        this.idPromo = idPromo;
        this.code = code;
        this.headline = headline;
        this.discountPercentage = discountPercentage;
    }

	public int getIdPromo() {
		return idPromo;
	}

	public void setIdPromo(int idPromo) {
		this.idPromo = idPromo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
    
    
}
