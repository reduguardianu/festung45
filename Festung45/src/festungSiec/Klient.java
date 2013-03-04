package festungSiec;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Klient extends Klient_Serwer
{
	private Klient_in klientIn;
	//private Klient_tcp klientTCP;
	private Thread klientInThread, klientTCPThread;
    private Vector<String> wiadomosci;
    private Vector<String> pakiety;
    private String listaGraczy;
    private final int PORT = 12345;
    private final int INET_PORT = 12675; //17777
    private int id;
    private int sojusz;
    private String nick;
    private Socket socket;
    
	public Klient()
	{
        try {
            dtSocket = new DatagramSocket(PORT);
        } catch (SocketException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
        wiadomosci = new Vector<String>();
        pakiety = new Vector<String>();
	}

    public String getMessage()
    {
        if (!wiadomosci.isEmpty())
        {
            return wiadomosci.remove(0);
        }
        else
        {
            return "EMPTY";
        }
    }
	
	public void sendMessage(int soj, String mes)
	{
        bufor = ("MESS;" + soj + ";" + nick + ":" + mes).getBytes();
        try {
            dtPacket.setData(bufor);
            dtSocket.send(dtPacket);
        } catch (IOException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public void connectToServer(int soj, String name, InetSocketAddress sockAddr)
    {
        
        try {
            socket = new Socket(sockAddr.getAddress(), sockAddr.getPort());
        } catch (IOException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
        dtPacket = new DatagramPacket(bufor, BUFOR_SIZE, sockAddr.getAddress(), INET_PORT);
        sojusz = soj;
        nick = name;
    }

    public void sendPacket(int soj, String mes)
    {
        bufor = ("PACK;" + soj + ";" + mes).getBytes();
        try {
            dtPacket.setData(bufor);
            dtSocket.send(dtPacket);
            //bufor = empty;
        } catch (IOException ex) {
            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPacket()
    {
        if (pakiety.size() > 0)
            return pakiety.remove(0);
        else
            return "EMPTY";

    }

    public void run()
	{
        klientIn = new Klient_in(dtSocket, Thread.currentThread());
        klientInThread = new Thread(klientIn);
        klientInThread.start();
        String temp;
        String head;
		while(true)
		{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException x) {
            temp = klientIn.getMessage();
            if (!temp.equals("EMPTY"))
            {
                head = temp.split(";",2)[0];              
                if (head.equals("PACK"))
                {
                    temp = temp.split(";",2)[1];
                    pakiety.add(temp);
                }
                else if (head.equals("MESS")) //message
                {
                    temp = temp.split(";",2)[1];
                    wiadomosci.add(temp);
                }
                else if (head.equals("DATA"))
                {
                    if (temp.equals("NO"))
                    {
                        wiadomosci.add("Klient:Serwer jest pełny");
                    }
                    else
                    {
                        try {
                            id = Integer.parseInt(temp.split(";", 3)[2]);
                            //bufor = empty;
                            bufor = ("DATA;" + id + ";" + sojusz + ";" + nick).getBytes();
                            dtPacket.setData(bufor);
                            dtSocket.send(dtPacket);
                        } catch (IOException ex) {
                            Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                else if (head.equals("PLAY")) //players
                {
                    temp = temp.split(";",2)[1];
                    listaGraczy = temp;
                }
            }
            }
		}
	}
}