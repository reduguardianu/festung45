package festungSiec;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

class Serwer_in extends Klient_Serwer
{
    final int PORT = 12675;
    private String wiadomosc;
    private ArrayList<String> wiadomosci;
    private Thread parent;


    public Serwer_in(Thread watek)
    {
        bufor = new byte[BUFOR_SIZE];
        try{
        dtSocket = new DatagramSocket(PORT);
        } catch (SocketException e)
        {
            System.err.println("Serwer_in:SocketExeption " + e);
        }
        dtPacket = new DatagramPacket(bufor, BUFOR_SIZE);
        wiadomosci = new ArrayList<String>();
        parent = watek;
    }

    public String getMessage()
    {
        if (wiadomosci.isEmpty())
            return "EMPTY";
        else
            return wiadomosci.remove(0);

    }

	public void run()
	{
        while(true)
        {
            try{
            bufor = new byte[BUFOR_SIZE];
            dtPacket.setData(bufor);          
            dtSocket.receive(dtPacket);
            }catch(IOException e)
            {

            }
            wiadomosc = new String(dtPacket.getData());
            wiadomosci.add(wiadomosc);
            parent.interrupt();
        }
	}
}