import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.crypto.Cipher;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

public class Client {

	Socket socket;
	BufferedReader in;
	PrintWriter out;
	private String addr = "172.28.174.181"; //l'adresse par défaut
	private int port = 1027;
	private String pseudo = "";
	private int localport;
	
	public static Vector<ProprieteClient> clientConnected = new Vector<ProprieteClient>() ;
	public static Vector<ProprieteGroup> groupConnected = new Vector<ProprieteGroup>();
	//private Vector<Groupe> groupes = new Vector<Groupe>();

	public Client(){
	}
	
	public static int port() throws Exception{
		ServerSocket server = new ServerSocket(0);
		int port = server.getLocalPort();
		System.out.println(" PORT CHOISI :"+port);
		return port;
	    }
	
	public ProprieteClient Trouver2(String pseudo){
		for(int i=0;i<clientConnected.size();i++){
			ProprieteClient data = clientConnected.get(i);
			if(data.getPseudo().equals(pseudo) ){
				return data; 
			}
		}
		return null;
	}
	public static Vector<ProprieteClient> getClient(){return clientConnected;}
	public Vector<ProprieteGroup> getGroup() { return groupConnected;}
	public int getPort() { return port;}
	public String getAddr() {return addr;}
	public String getPseudo() {return pseudo;}
	public int getLocalPort() {return localport;}
	public Socket getSocket() {return socket;}
	//public Vector<Groupe> getGoupes() {return groupes; }

	public void setLocalport(int l) {this.localport = l;}
	public void setAddr(String addr) {this.addr = addr;}
	public void setPseudo(String pseudo) {this.pseudo = pseudo;}
	
	
	
	public void ClientConnected (int LocalPort){
		if(clientConnected.size()==0){
			System.out.print("0 clients connectes");
			return;
		}

		System.out.println("Liste Client Connectés :");
		for(int i=0;i<clientConnected.size();i++){
			ProprieteClient c =  clientConnected.get(i);
			String pseudo = c.getPseudo();
			String adr = c.getAddr();
			int p = c.getPort();
			if(LocalPort != p ){
				// && !this.getAddr().equals(addr)
				System.out.println("n°" + i +" : "+ pseudo +
						"  | Adresse : " + adr + 
						"  | Port : " + p); 
			}
		}
	}
	public void GroupConnected (){
		if(groupConnected.size()==0){
			System.out.print("0 Groupes présents");
			return;
		}

		System.out.println("Liste Groupes Présents :");
		for(int i=0;i<groupConnected.size();i++){
			ProprieteGroup c =  groupConnected.get(i);
			String nom = c.getNom();
			String adr = c.getAddressMulticast();
			int p = c.getPortMulticast();
			System.out.println("n°" + i +" : "+ nom +
					"  | Adresse : " + adr + 
					"  | Port : " + p); 
		}
	}
	


	public static void main(String[] args){
		Socket socket = null;
		Client client = null ;
		DatagramSocket ds = null ;

		//Usage java TP1.Client [ipServeur] pseudo
		if(args.length == 2  && isValidPseudo(args[1]) ){
			client = new Client() ;
			client.setPseudo(args[1]);
		}else{ 
			System.out.println("Usage Incorrect ! java TP1.Client ipServeur pseudo (6 caractères) ");
			System.exit(0);
		}

		try {

			// Initialiser la socket client sur l'addr et le port 1024
			//int p = port();
			//client.setLocalport(p);
			socket = new Socket(args[0], client.getPort());
			System.out.println("Pseudo :"+  client.getPseudo() + "\n" +
					"\tHost => "+socket.getInetAddress().getHostAddress() + "\n" +
					"\tPort => "+socket.getLocalPort());

			client.setLocalport(socket.getLocalPort());

			ds = new DatagramSocket(client.getLocalPort());

		} catch (Exception e){
			System.out.println("le serveur n'est pas lancé [Connection refused] !!");
		}


		//recevoir la reponse from server
		RecepteurClientTCP recep = new RecepteurClientTCP(socket,client);
		recep.setName("RecepeteurClientTCP");
		//envoie une requete to serveur
		EmetteurClientTCP emetteur = new EmetteurClientTCP(socket,client);
		emetteur.setName("EmetteurClientTCP");


		RecepteurClientUDP recepteur = new RecepteurClientUDP(ds);
		recepteur.start();

		emetteur.start();
		recep.start();


	}

	private static boolean isValidPseudo(String p) {
		if(p.length() != 6) return false;
		return true;
	}
	

	
/*
	public static Vector<ClientProperties> getClients() {
		return vectClients;
	}

	public ClientProperties getClientByPseudo(String psd){
		for(int i=0;i<getClients().size();i++){
			ClientProperties c = getClients().get(i);
			if(c.getPseudo().equals(psd) ){
				return c; 
			}
		}
		return null;
	}

*/	

	/**
	 * Cette méthode vérifie que le client passé en argument existe bien dans le vecteur de clients
	 * @param c: l'objet dont on veut connaître l'existence
	 * @return
	 */
	/*
	  public static boolean existeClient(ClientProperties c) {
	 
		for(int i=0;i< getClients().size();i++){
			ClientProperties cp =  getClients().get(i);
			String addr = cp.getAddr(); int p = cp.getPort();
			if( addr.equals(c.getAddr()) && p == c.getPort() )
				return true;
		}
		return false;
	}
*/



}


