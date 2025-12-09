package model;

public class Courier extends User{
	private String vehicleType;
    private String vehiclePlate;

    public Courier() {}

    public Courier(int idUser, String fullName, String email, String password,
                   String phone, String address, String role,
                   String vehicleType, String vehiclePlate) {
        super(idUser, fullName, email, password, phone, address, role);
        this.vehicleType = vehicleType;
        this.vehiclePlate = vehiclePlate;
    }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public String getVehiclePlate() { return vehiclePlate; }
    public void setVehiclePlate(String vehiclePlate) { this.vehiclePlate = vehiclePlate; }
}
