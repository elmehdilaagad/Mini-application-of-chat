
public class Groupe {

	private String nom;
	private String multicastAdress;
	private static int nombreUtilisateurs;
	private int port;
	
	
	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getMulticastAdress() {
		return multicastAdress;
	}


	public void setMulticastAdress(String multicastAdress) {
		this.multicastAdress = multicastAdress;
	}


	public static int getNombreUtilisateurs() {
		return nombreUtilisateurs;
	}


	public static void setNombreUtilisateurs(int nombreUtilisateurs) {
		Groupe.nombreUtilisateurs = nombreUtilisateurs;
	}


	public Groupe(String nom, String adress, int pp){
		this.nom = nom;
		this.multicastAdress = adress;
		Groupe.nombreUtilisateurs = 0;
		this.port = pp;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
	
	

	
	
}
