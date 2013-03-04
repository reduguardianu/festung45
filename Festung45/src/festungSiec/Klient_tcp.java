package festungSiec;

import java.net.*;
import java.io.*;
import java.util.Vector;

public class Klient_tcp implements Runnable
{
	private Socket socket;
	private BufferedReader inFromServer;
    private PrintWriter outToServer;
    private Vector<String> wiadomosci;
    DatagramPacket dtPacket;
    DatagramSocket dtSocket;
    byte[] bufor;
	
	public Klient_tcp()
	{
       wiadomosci = new Vector<String>();
	}
	
	public void sendMessage(int soj, String wiadomosc)
	{
		outToServer.print(soj + ";" + "MESS" + ";" + wiadomosc);	
	}

    public void connectToServer(int soj, String name, InetSocketAddress sockAddr)
    {
            try
            {
                socket = new Socket(sockAddr.getAddress(), sockAddr.getPort());
            }
            catch (IOException e)
            {
                System.out.println("Klient_tcp:Blad:line 48 " + e);
            }
    }

    public String getMessage()
    {
        try{
        return wiadomosci.remove(wiadomosci.size()-1);
        } catch (ArrayIndexOutOfBoundsException e)
        {
            return "EMPTY";
        }
    }
	
	public void run()
	{
        //Pusto.
        //Pozostalosc po tcp.
	}
}