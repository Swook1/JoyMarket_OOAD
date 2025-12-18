package model;

/*
  Admin - extends dari User
  Punya akses khusus ke dashboard admin
  Ada emergency contact kalo kenapa-kenapa
 */
public class Admin extends User{
	private String emergencyContact; // kontak darurat admin
	
	public Admin() {}

	public Admin(int idUser, String fullName, String email, String password,
            String phone, String address, String role, String emergencyContact) {
   super(idUser, fullName, email, password, phone, address, role);
   this.emergencyContact = emergencyContact;
}

	public String getEmergencyContact() {
		return emergencyContact;
	}

	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	
}
