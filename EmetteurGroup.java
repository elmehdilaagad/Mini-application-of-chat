import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


public class EmetteurGroup extends Thread{

	private String pseudo;
	private String adresse;
	private int port;
	private String nom;


	public EmetteurGroup(String p, String a, int po, String n){

		pseudo = p;
		adresse = a;
		port = po;
		nom = n;
	}

	public void run(){

		try {
			DatagramSocket ms = new DatagramSocket();
			InetAddress ia = InetAddress.getByName(adresse);
			while(true){
				Scanner sc = new Scanner(System.in);
				String s = sc.nextLine();
				DatagramPacket dp = new DatagramPacket(s.getBytes(),s.getBytes().length,ia,port);
				ms.send(dp);
				System.out.println("Sent at");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}




	}

}
