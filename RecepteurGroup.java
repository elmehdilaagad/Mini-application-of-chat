import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class RecepteurGroup extends Thread {

	private String adresse;
	private int port;
	private String nom;
	
	
	public RecepteurGroup(String a, int p, String n){
		adresse = a;
		port = p;
		nom = n;
	}
	
	
	
	public void run(){
		
		try {
		      byte [] data = new byte[256];
		      InetAddress ia = InetAddress.getByName(adresse);
		      MulticastSocket ms = new MulticastSocket(port);
		      ms.joinGroup(ia);
		      DatagramPacket dp = new DatagramPacket(data,data.length);
		      while (true) {
		        ms.receive(dp);
		        String s = new String(dp.getData(),0,dp.getLength());
		        System.out.println("Message reçu du groupe"+nom+" : "+s);
		      }
		    } catch(Exception e) {
		      e.printStackTrace();
		    }
		
	}
	
}
