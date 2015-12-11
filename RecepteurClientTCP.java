import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import javax.crypto.Cipher;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

class RecepteurClientTCP  extends Thread {
	private BufferedReader in;
	private String message = "" ;
	private Socket socket;
	private Client client;

	public RecepteurClientTCP (Socket s, Client c) {
		socket  = s;
		client = c;
	}

	public void run() {
		try {
			while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				message = in.readLine();
				if(message!=null){
					if(message.equals("PseudoUsed")){
						System.out.println(" Ce Pseudo est déjà utilisé !");
						client = null;
						System.exit(0);
					}else if(message.startsWith("[")){
						System.out.println("Liste des clients :"+message);
						Client.getClient().removeAllElements();;
						Thread.sleep(1000);

						String[] temp ;
						String delims = "[@]" ;

						message = message.substring(1, message.length()-1);
						temp = message.split(delims);
						delims = "[|]" ;

						for(int j=0;j<temp.length;j++){
							String[] t ;
							t = temp[j].split(delims);
							int num = Integer.parseInt(t[0]);
							String pseudo = t[1]; 
							String ip = t[2];
							int port = Integer.parseInt(t[3]);


							ProprieteClient c = new ProprieteClient(num,pseudo, ip, port);

							if(!Client.getClient().contains(c))
								Client.getClient().add(c);

						}
						
					}else if(message.startsWith("glist"))
						if(message.length() > 3){
							Client.getClient().removeAllElements();
							Thread.sleep(1000);

							String[] tmp ;
							String delims ="[@]";
							message = message.substring(5);
							tmp = message.split(delims);
							delims = "[|]" ;

							for(int j=0;j<tmp.length;j++){
								String[] t ;
								t = tmp[j].split(delims);
								String nom = t[0]; 
								String address = t[1];
								System.out.println(" port "+t[2]);
								int port = Integer.parseInt(t[2]);
								ProprieteGroup g = new ProprieteGroup(nom, address, port);
								if(!client.getGroup().contains(g))
									client.getGroup().add(g);
							}
						}
				
					}else if(message.startsWith("FIN_OK")){
						String[] temp ;
						String delims = "[|]" ;
						String addr; int port;

						message = message.substring(6, message.length());
						temp = message.split(delims);
						addr = temp[0];
						port = Integer.parseInt(temp[1]);
						ProprieteClient c = new ProprieteClient(-1, "" , addr, port);

						if(!Client.getClient().contains(c))
							Client.getClient().remove(c);
						System.exit(0);
					}

				}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
