import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Vector;

public class Connexion extends Thread{	
	private Socket socket = null ;
	private PrintWriter out;
	private static int clientConnectes = 0;
	private int idConnexion ;
	private String msg = "";
	private String addr = "";
	private String pseudo = "";
	private int port ;
	private String[] tokens ;
	private String delims = "[|]" ;
	private String nom;
	private String adresseMulticast;
	private int portMulticast;
	
	
	public  static Vector<ProprieteClient> clientConnected = new Vector<ProprieteClient>();;
	public  static Vector<ProprieteGroup> groupConnected = new Vector<ProprieteGroup>();;

	public Connexion(Socket s){
		socket = s;
	}

	public Socket getSocket(){
		return socket;
	}

	/**
	 * Méthode qui envoie le message en argument à tous les clients connectés
	 * @param message: le message à envoyer
	 */
	public void envoiMessage(String message){
		PrintWriter out;
		for(int i=0; i<Serveur.getAll().size(); i++){
			try {
				out = new PrintWriter(Serveur.getAll().get(i).getSocket().getOutputStream(),true);
				out.println(message);
			}catch (IOException e){}
		}		
	}

	public void run(){
		BufferedReader in;

		try {
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}  

		while(true){

			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				msg = in.readLine();
				if(msg.startsWith("NEW_USER")){
					msg = msg.substring(8);
					System.out.println(" Ce que j'ai recu client :"+msg);
					tokens = msg.split(delims);
					
					pseudo = tokens[0];
					addr = tokens[1];
					port = Integer.parseInt(tokens[2]);
					ProprieteClient user = new ProprieteClient(clientConnectes, pseudo,addr,port);
					if(!isUserExist(user)){
						clientConnected.add(user);
						clientConnectes++;
						System.out.println("n° " + clientConnectes + " : " + clientConnected.get(clientConnectes-1).getPseudo());
					}else {
						out.println("PseudoUsed");
						out.flush(); 
						Thread.sleep(200);
					}
				}else if(msg.startsWith("NEW_GROUP")){
					msg = msg.substring(9);
					System.out.println(" Ce que j'ai recu group :"+msg);
					tokens = msg.split(delims);
					nom = tokens[0];
					adresseMulticast = tokens[1];
					portMulticast = Integer.parseInt(tokens[2]);
					ProprieteGroup group = new ProprieteGroup(nom,adresseMulticast,portMulticast);
					if(!isGroupExist(nom)){
						groupConnected.add(group);
						System.out.println(nom +" Est crée");
					}else {
						out.println("NomUsed");
						out.flush();
						Thread.sleep(200);
					}
					
				}else if(msg.startsWith("XX-FIN")){
					msg = msg.substring(6);
					tokens = msg.split(delims);
					
					pseudo = tokens[0];
					addr = tokens[1];
					port = Integer.parseInt(tokens[2]);
					
					for(int i=0;i<clientConnected.size();i++){
						ProprieteClient cl = clientConnected.get(i)  ;
						if(cl.getAddr().equals(addr) && cl.getPseudo().equals(pseudo) && cl.getPort() == port ){
							
							clientConnected.remove(cl);
							removeClientDisconected(cl);
						}
					}

					clientConnectes--; 



					msg = "Un client s'est déconnecté  \t " +
							clientConnectes + "Client(s) en ligne en ce moment"
							;
					System.out.println(msg);

					// on peut fermer le serveur si y a pas clientConnectes==0 
					//System.exit(0);

				}else if (msg.startsWith("XX-LISTE")){


					String listToClient = "[" ;
					
					for(int i=0;i<clientConnected.size();i++){
						idConnexion = clientConnected.get(i).getNum();
						pseudo = clientConnected.get(i).getPseudo();
						addr = clientConnected.get(i).getAddr() ; 
						System.out.println("XX-LISTE --> addr:"+addr);
						port = clientConnected.get(i).getPort() ;
						listToClient += idConnexion+"|"+pseudo+"|"+addr+"|"+port;
						if(i<clientConnected.size()-1) 
							listToClient += "@" ;
					}
					listToClient += "]";

					out.println(listToClient);
					out.flush(); 
					
				}else if (msg.startsWith(("XX-GLIST"))){
					
					String listGroup = "glist";
					
					for(int i=0;i<groupConnected.size();i++){
						nom = groupConnected.get(i).getNom();
						adresseMulticast = groupConnected.get(i).getAddressMulticast() ; 
						portMulticast = groupConnected.get(i).getPortMulticast() ;
						listGroup +=nom+"|"+adresseMulticast+"|"+portMulticast;
						if(i<groupConnected.size()-1) 
							listGroup += "@" ;
					}


					out.println(listGroup);
					out.flush();
					
				}
			} catch (Exception e) {
			}
		}

	}


	private boolean isUserExist(ProprieteClient user) {
		for(int i=0;i<clientConnected.size();i++){
			idConnexion = clientConnected.get(i).getNum();
			pseudo = clientConnected.get(i).getPseudo();
			addr = clientConnected.get(i).getAddr() ; 
			port = clientConnected.get(i).getPort() ;
			if (pseudo.equals(user.getPseudo())){
				System.out.println("Pseudo Déjà utilisé ! ");
				return true;
			}else if(addr.equals(user.getAddr()) && port == user.getPort() ){
				System.out.println("Port & adresse Déjà utilisé ! ");
				return true;
			}
		}
		return false;
	}

	private boolean isGroupExist(String _nom){
		
		for(int i=0;i<groupConnected.size();i++){
			nom = clientConnected.get(i).getPseudo();
			if (nom.equals(_nom)){
				System.out.println("Nom Déjà utilisé ! ");
				return true;
			}
		}
			return false;
	}
	private void removeClientDisconected(ProprieteClient cl)  {
	
		int port = cl.getPort() ;
		String addr = cl.getAddr();
		String toClient = "FIN_OK" ;
		toClient += addr+"|"+port;

		out.println(toClient); 
		out.flush(); 
	}
}

