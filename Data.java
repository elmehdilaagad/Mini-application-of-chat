import java.util.Vector;


public class Data {
	public static Vector<ProprieteClient> clientConnected;
	public static Vector<ProprieteGroup> groupConnected;

	public Data(){
		clientConnected = new Vector<ProprieteClient>();
		groupConnected = new Vector<ProprieteGroup>();
	}

	public void recordClient(ProprieteClient client){
		clientConnected.add(client);
	}
	public void recordGroup(ProprieteGroup g){
		groupConnected.add(g);
	}
	public int tailleClient(){
		return clientConnected.size();
	}
	public int tailleGroup(){
		return groupConnected.size();
	}
	public void removeClient(ProprieteClient client){
		clientConnected.remove(client);
	}

	public void removeGroup(ProprieteGroup g){
		groupConnected.remove(g);
	}
	public void Toutsuprimer(){
		clientConnected.removeAllElements();
	}
	public void ToutsuprimerGroup(){
		groupConnected.removeAllElements();
	}

	public boolean Trouver(ProprieteClient client){

		return clientConnected.contains(client);
	}
	public boolean TrouverGroup(ProprieteGroup g){


		return groupConnected.contains(g);
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

	public ProprieteGroup TrouverGroup2(String nom){
		for(int i=0;i<groupConnected.size();i++){
			ProprieteGroup data = groupConnected.get(i);
			if(data.getNom().equals(nom) ){
				return data; 
			}
		}
		return null;
	}
	public void ClientConnected (int LocalPort){
		if(clientConnected.size()==0){
			System.out.print("0 clients connectes");
			return;
		}

		System.out.println("Liste Client ConnectŽs :");
		for(int i=0;i<clientConnected.size();i++){
			ProprieteClient c =  clientConnected.get(i);
			String pseudo = c.getPseudo();
			String adr = c.getAddr();
			int p = c.getPort();
			if(LocalPort != p ){
				// && !this.getAddr().equals(addr)
				System.out.println("n¡" + i +" : "+ pseudo +
						"  | Adresse : " + adr + 
						"  | Port : " + p); 
			}
		}
	}

	public void GroupConnected (){
		if(groupConnected.size()==0){
			System.out.print("0 Groupes prŽsents");
			return;
		}

		System.out.println("Liste Groupes PrŽsents :");
		for(int i=0;i<groupConnected.size();i++){
			ProprieteGroup c =  groupConnected.get(i);
			String nom = c.getNom();
			String adr = c.getAddressMulticast();
			int p = c.getPortMulticast();
			System.out.println("n¡" + i +" : "+ nom +
					"  | Adresse : " + adr + 
					"  | Port : " + p); 
		}
	}
}


