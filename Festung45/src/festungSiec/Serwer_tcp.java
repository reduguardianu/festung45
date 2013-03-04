package festungSiec;

import java.net.*;
import java.util.Vector;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Serwer_tcp implements Runnable
{
	Socket tempSocket;
	public Vector<GraczSieciowy> gracze;
    public Vector<TCPIn> sockety;
    Vector<Thread> watki;
    Vector<String> wiadomosci;
    TCPConnect polaczenia;
    Thread TCPConnectThread;
    private byte[] bufor;
    private DatagramSocket dtSocket;
    private DatagramPacket dtPacket;
    private byte[] empty = new byte[450];
	
	private final int PORT = 16667;
	
	BufferedReader inFromClient;
	DataOutputStream outToClient;
	String clientSentence;
	GraczSieciowy nowyGracz;
	
	public Serwer_tcp ()
	{
		gracze = new Vector();
        polaczenia = new TCPConnect();
        TCPConnectThread = new Thread(polaczenia);
        gracze = new Vector<GraczSieciowy>();
        sockety = new Vector<TCPIn>();
        watki = new Vector<Thread>();
        wiadomosci = new Vector<String>();

        bufor = new byte[450];
        dtPacket = new DatagramPacket(bufor, 450);
        try {
            dtSocket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Serwer_tcp.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

    public void sendMessage(int soj, String mes)
    {
        int i;
        
        for (i=0; i < sockety.size(); i++)
        {
            sockety.elementAt(i).wyslij(mes);
        }
    }
	
	public void run()
	{
        TCPConnectThread.start();
        int i = 0;
        int sojusz;
        String wiad;
       
		while (true)
        {
            if ((tempSocket = polaczenia.noweSockety()) != null)
            {
                try {
                    sockety.add(new TCPIn(1, "imie", tempSocket));
                    ///TODO: Wstawić obsługę przepełnionego serwera
                    bufor = ("DATA;YES;" + sockety.lastElement().daneGracza.id).getBytes();
                    dtPacket.setData(bufor);
                    dtPacket.setAddress(tempSocket.getInetAddress());
                    dtPacket.setPort(12345);
                    dtSocket.send(dtPacket);
                } catch (IOException ex) {
                    Logger.getLogger(Serwer_tcp.class.getName()).log(Level.SEVERE, null, ex);
                }
                dtSocket.close();
            }
                Thread.yield();
        }
    }
}