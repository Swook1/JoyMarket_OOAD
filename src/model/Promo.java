package model;

import javafx.beans.property.*;

public class Promo {

    private StringProperty idPromo;
    private StringProperty code;
    private StringProperty headline;
    private DoubleProperty discountPercentage;

    public Promo() {
        this.idPromo = new SimpleStringProperty();
        this.code = new SimpleStringProperty();
        this.headline = new SimpleStringProperty();
        this.discountPercentage = new SimpleDoubleProperty();
    }

    public String getIdPromo() { return idPromo.get(); }
    public void setIdPromo(String value) { idPromo.set(value); }
    public StringProperty idPromoProperty() { return idPromo; }

    public String getCode() { return code.get(); }
    public void setCode(String value) { code.set(value); }
    public StringProperty codeProperty() { return code; }

    public String getHeadline() { return headline.get(); }
    public void setHeadline(String value) { headline.set(value); }
    public StringProperty headlineProperty() { return headline; }

    public double getDiscountPercentage() { return discountPercentage.get(); }
    public void setDiscountPercentage(double value) { discountPercentage.set(value); }
    public DoubleProperty discountPercentageProperty() { return discountPercentage; }
}
