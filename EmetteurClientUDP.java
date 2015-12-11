import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class EmetteurClientUDP extends Thread {

	private DatagramSocket serverSocket;
	private int port = 0;
	private String ia = "";
	private String chaine = "";
	private String pseudo = "" ;
	private Scanner sc = new Scanner(System.in);
	private String nameGroupe;
	private Client client;
	private ProprieteClient propriete;



	public EmetteurClientUDP(ProprieteClient c , Client cl ){
		this.client = cl;
		this.propriete = c;
	}
	
	public static void JoinEmTCP() throws InterruptedException{
		Thread.currentThread().join();
	}

	public void run(){
		pseudo = propriete.getPseudo();
		port = propriete.getPort();
		ia = propriete.getAddr();


		System.out.println("Vous pouvez Žcrire ˆ " + port);
		try {
			serverSocket = new DatagramSocket();
			System.out.print("> ");
			while (true){

				InetAddress adresse = InetAddress.getByName(ia);
				chaine = sc.nextLine();

				if(chaine.equals("")){
					System.out.print("> ");
					continue;
				}else if(chaine.equals("stop")){
					Thread.sleep(2000);
					
					System.out.println("----------------------- Fin de la conversation ");
					Thread.currentThread().join();

					System.out.print("> ");

				}else if(chaine.startsWith("createG:")){
					nameGroupe = chaine.substring(chaine.indexOf(":")+1,  chaine.length());
					
				}else if(chaine.startsWith("joinG:")){
					 
					nameGroupe = chaine.substring(chaine.indexOf(":")+1,  chaine.indexOf("?"));
					String user = chaine.substring(chaine.indexOf("?")+1,  chaine.length() );

					ProprieteClient _c = client.Trouver2(user);
					if(_c != null){
						
						System.out.println(user + " vient de rejoindre le groupe " + nameGroupe);
					}else {
						System.out.println("Utilisateur introuvable !!" + user );
					}
/*
				}else if(chaine.startsWith("quitG:")){
					

					nameGroupe = chaine.substring(chaine.indexOf(":")+1,  chaine.indexOf("?"));
					String user = chaine.substring(chaine.indexOf("?")+1,  chaine.length() );
					ProprieteClient _c = client.getClient().contains(user);
					if(_c != null){
						System.out.println(user + " vient de quitter le groupe " + nameGroupe);
					}else {
						System.out.println("Utilisateur introuvable !" + user );
					}
					*/
				}else if(chaine.equals("showG")){
					
				}else if(chaine.equals("showGD")){
					
				}else{
					byte[] buffer = chaine.getBytes();
					DatagramPacket messagePacket = new DatagramPacket(buffer, 
							buffer.length, adresse, port);
					serverSocket.send(messagePacket);
				}
				System.out.print("> ");
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
}

