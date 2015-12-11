import java.io.PrintWriter;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;





public class EmetteurClientTCP extends Thread{


	private String chaine = "";
	private Scanner lire  = new Scanner (System.in);
	private Scanner sca  = new Scanner (System.in);
	private Scanner sca2  = new Scanner (System.in);
	private PrintWriter pw  = null ;
	private Socket socket;
	private Client client;
	private String data = "" ;
	static int nbrGroupe;
	String []iPMulticaste = new String[5];
	public EmetteurClientTCP(Socket s, Client c) {
		socket = s;
		client = c; 
		iPMulticaste[0]= ("225.1.2.4");
        iPMulticaste[1]= ("225.1.2.5");
        iPMulticaste[2]= ("225.1.2.6");
        iPMulticaste[3]= ("225.1.2.7");
        iPMulticaste[4]= ("225.1.2.8");
        nbrGroupe=0;
	}
	public static int port() throws Exception{
		MulticastSocket server = new MulticastSocket(0);
		int port = server.getLocalPort();
		System.out.println(" PORT CHOISI :"+port);
		return port;
	    }
	
	public boolean GroupeDispo(){
		if (nbrGroupe == 5)
			return false;
		else
			return true;
	}

	public int menu(){
		int reponse;
		Scanner rp = new Scanner(System.in);
		System.out.println(" *********************** Commandes ********************* \n \n \n");
		System.out.println(" 1 - Demander la liste des utilisateurs presents");
		System.out.println(" 2 - Demander la liste des groupes presents");
		System.out.println(" 3 - Demander une conversation privŽe avec un utilisateur ");
		System.out.println(" 4 - Creer un groupe ");
		System.out.println(" 5 - Rejoindre un groupe");
		
		reponse = rp.nextInt();

		return reponse;
	}

	public void run(){
		try {
			data =  "NEW_USER"+client.getPseudo()+"|"+client.getAddr()+"|"+client.getLocalPort();
			pw = new PrintWriter(socket.getOutputStream());
			pw.println(data);
			pw.flush();


			System.out.print("> ");
			while(true){
				int p = menu();
				chaine = String.valueOf(p);
				switch(p){
				case 1:	pw = new PrintWriter(socket.getOutputStream());
				pw.println("XX-LISTE");
				pw.flush();

				Thread.sleep(3000);

				System.out.println("----------------------- CLIENTS CONNECTES ----------------------- ");
				System.out.println(" La taille de la liste des clients : "+Client.getClient().size());
				client.ClientConnected(client.getLocalPort());
				System.out.println("--------------------------------------------------------------------");
				System.out.print("> ");
				break;
				
				
				case 2:	pw = new PrintWriter(socket.getOutputStream());
				pw.println("XX-GLIST");
				pw.flush();

				Thread.sleep(3000);

				System.out.println("----------------------- GROUPES CONNECTES -------------------------- ");
				client.GroupConnected();
				System.out.println("-------------------------------------------------------------------- ");
				System.out.print("> ");
				break;

				/*
					}else if(chaine.equals("stop")) {
						data =  "XX-FIN"+client.getPseudo()+"|"+client.getAddr()+"|"+client.getLocalPort();

						pw = new PrintWriter(socket.getOutputStream());
						pw.println(data);
						pw.flush();

						System.out.println("----------------------- END CONNECTION");
				 */

				case 3 : System.out.println(" Entrer le pseudo du client");
						String pseudo = lire.nextLine();
						if(isClientConnected(pseudo,client)){
							System.out.println("----------------------- CONNEXION reussi vers "+ pseudo);
							Thread.currentThread().join();
							System.out.print("moi : ");
						}else {
							System.out.println("----------------------- le pseudo n est pas correct ! ");
						}
						break;

						
				case 4 :if(GroupeDispo()){
							System.out.println(" Entrer le nom de votre groupe");
							String nom = sca.nextLine();
							String adresse = iPMulticaste[nbrGroupe];
							nbrGroupe++;
							int pr = port();
							Groupe g = new Groupe (nom,adresse,pr);
							data =  "NEW_GROUP"+g.getNom()+"|"+g.getMulticastAdress()+"|"+g.getPort();
							pw.println(data);
							pw.flush();
							RecepteurGroup rg = new RecepteurGroup(g.getMulticastAdress(), g.getPort(),g.getNom());
							EmetteurGroup eg = new EmetteurGroup(client.getPseudo(),g.getMulticastAdress(), g.getPort(),g.getNom());
							rg.start();
							eg.start();
							EmetteurClientTCP.Join();
							RecepteurClientUDP.Join();
							
						}else
							System.out.println(" Desole !! Tout les groupe sont occupe");  
						break;
						
				case 5: System.out.println(" Entrer le nom du groupe :");
						String noms = sca2.nextLine();
						if(isGroupConnected(noms,client)){
							System.out.println("----------------------- Vous avez rejoint le groupe "+ noms);
							Thread.currentThread().join();
							System.out.print("moi : ");
						}else {
							System.out.println("----------------------- le nom n'est pas correct ! ");
						}
						break;
				
				default:System.out.print("> ");
						break;



				}
			}
		}catch(Exception e){
			e.printStackTrace();
			System.exit(0);}
	}

	private boolean isClientConnected(String pseudo,Client client) {

		for (int i = 0; i <client.getClient().size(); i++) {

			ProprieteClient c = client.getClient().get(i);
			if(c.getPseudo().equals(pseudo)){
				System.out.println("Pseudo :"+c.getPseudo()+" Adresse:"+c.getAddr());
				EmetteurClientUDP e = new EmetteurClientUDP(c, client);
				e.start();
				return true;
			}
		}
		return false;
	}
	
	
	public static void Join() throws InterruptedException{
		Thread.currentThread().join();
	}
	
	private boolean isGroupConnected(String nom,Client client) throws InterruptedException {

		for (int i = 0; i <client.getGroup().size(); i++) {

			ProprieteGroup c = client.getGroup().get(i);
			if(c.getNom().equals(nom)){
				System.out.println("Nom :"+c.getNom()+" Adresse:"+c.getAddressMulticast());
				EmetteurGroup eg = new EmetteurGroup(client.getPseudo(), c.getAddressMulticast(), c.getPortMulticast(), c.getNom());
				RecepteurGroup rc = new RecepteurGroup(c.getAddressMulticast(), c.getPortMulticast(), c.getNom());
				eg.start();
				rc.start();
				EmetteurClientTCP.Join();
				RecepteurClientUDP.Join();
				return true;
			}
		}
		return false;
	}
}

