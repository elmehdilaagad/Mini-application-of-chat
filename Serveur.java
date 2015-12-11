import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;



public class Serveur extends Thread {

	private static int PORT = 1027;
	private ServerSocket srvSocket;
	private Socket clientSocket;
	private static Vector<Connexion> L = new Vector<Connexion>();


	public void run(){
		try {
			srvSocket = new ServerSocket(PORT);
			while(true){
				clientSocket = srvSocket.accept();
				Connexion c = new Connexion(clientSocket);
				L.add(c);
				c.start();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public Serveur()  {
	}

	public static Vector<Connexion> getAll(){
		return L;
	}


	public static void main(String[] args) {
		try {
			new Serveur().start() ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public int getPort() {
		return PORT;
	}


}
