package festungSiec;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public abstract class Klient_Serwer implements Runnable
{
	protected int BUFOR_SIZE = 450;
	protected volatile byte[] bufor;
	protected DatagramSocket dtSocket;
	protected DatagramPacket dtPacket;
    protected final byte[] empty = new byte[BUFOR_SIZE];
	
	public Klient_Serwer()
	{
		bufor = new byte[BUFOR_SIZE];
	}
	
	public abstract void run();
}