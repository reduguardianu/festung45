package festungSiec;

import java.net.*;
import java.util.ArrayList;
import java.net.DatagramPacket;

class Klient_in extends Klient_Serwer
{
    ArrayList<String> kolejka;
    Thread parent;

	public Klient_in(DatagramSocket socket, Thread watek)
	{
		bufor = new byte[BUFOR_SIZE];
        kolejka = new ArrayList<String>();
		dtSocket = socket;
		dtPacket = new DatagramPacket(bufor, BUFOR_SIZE);
        parent = watek;
	}
	public String getMessage()
	{
        if (kolejka.isEmpty())
            return "EMPTY";
        else
            return kolejka.remove(0);
	}
	public void run()
	{
		while(true)
		{
			try{
			dtSocket.receive(dtPacket);
            kolejka.add((new String(dtPacket.getData(), 0, dtPacket.getLength())).trim());
            parent.interrupt();
			} catch (java.io.IOException e)
			{
				System.err.println(e.getMessage());
			}
		}
	}
}