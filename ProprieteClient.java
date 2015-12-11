class ProprieteClient {
	private int num;
	private String pseudo;
	private String address;
	private int port;

	public ProprieteClient(int n, String ps, String a, int p) {
		num = n;
		pseudo = ps;
		address = a;
		port = p;
	}

	public int getNum(){return num;}
	public String getAddr(){return address;}
	public int getPort(){return port;}
	public String getPseudo() {return pseudo;}
}

