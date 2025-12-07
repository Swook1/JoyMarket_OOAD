package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Courier extends User {

    private StringProperty idCourier;
    private StringProperty vehicleType;
    private StringProperty vehiclePlate;

    public Courier() {
        super();
        this.idCourier = new SimpleStringProperty();
        this.vehicleType = new SimpleStringProperty();
        this.vehiclePlate = new SimpleStringProperty();
        setRole("Courier");
    }

    public String getIdCourier() { return idCourier.get(); }
    public void setIdCourier(String value) { idCourier.set(value); }
    public StringProperty idCourierProperty() { return idCourier; }

    public String getVehicleType() { return vehicleType.get(); }
    public void setVehicleType(String value) { vehicleType.set(value); }
    public StringProperty vehicleTypeProperty() { return vehicleType; }

    public String getVehiclePlate() { return vehiclePlate.get(); }
    public void setVehiclePlate(String value) { vehiclePlate.set(value); }
    public StringProperty vehiclePlateProperty() { return vehiclePlate; }
}
