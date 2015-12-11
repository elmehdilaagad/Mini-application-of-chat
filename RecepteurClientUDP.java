import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class RecepteurClientUDP extends Thread{

	private DatagramSocket socket;

	public RecepteurClientUDP(DatagramSocket s){
		socket = s;
	}

	public static void Join() throws InterruptedException{
		Thread.currentThread().join();
	}
	public void run(){
		try{

			while(true){
				byte [] reciveData = new byte[1024];
				DatagramPacket paquet = new DatagramPacket(reciveData ,reciveData.length);
				socket.receive(paquet);
				String chaine = new String(paquet.getData());
				System.out.println(chaine);

				int pt = paquet.getPort();
				InetAddress addr = paquet.getAddress();
				String t = "recu" ;
				byte[] b = t.getBytes();
				byte [] d = t.getBytes("UTF-8");
				DatagramPacket p = new DatagramPacket(d,d.length, addr, pt);
				socket.send(p);
				
				DatagramPacket p2 = new DatagramPacket(b, d.length, addr, pt);
				socket.send(p2);


			}

		}catch(Exception e){

		}
	}

}
