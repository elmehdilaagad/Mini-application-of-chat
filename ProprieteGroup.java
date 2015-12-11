
public class ProprieteGroup {
	private String nom;
	private String addressMulticast;
	private int portMulticast;
	
	
	
	public ProprieteGroup(String n, String a, int p){
		
		nom = n;
		addressMulticast = a;
		portMulticast = p;
	}



	public String getNom() {
		
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getAddressMulticast() {
		return addressMulticast;
	}



	public void setAddressMulticast(String addressMulticast) {
		this.addressMulticast = addressMulticast;
	}



	public int getPortMulticast() {
		return portMulticast;
	}



	public void setPortMulticast(int portMulticast) {
		this.portMulticast = portMulticast;
	}
	
	
}
