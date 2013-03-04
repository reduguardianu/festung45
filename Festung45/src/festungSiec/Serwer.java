package festungSiec;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Serwer extends Klient_Serwer
{
	private Serwer_in serwerIn;
	private Serwer_tcp serwerTCP;
	Thread serwerInThread, serwerTCPThread;
    private final int PORT = 12676;
    private final int INET_PORT = 12345;
	
	public Serwer()
	{
		//serwerIn = new Serwer_in(Thread.currentThread());
		serwerTCP = new Serwer_tcp();
//		serwerInThread = new Thread(serwerIn);
		serwerTCPThread = new Thread(serwerTCP);
        bufor = new byte[BUFOR_SIZE];
        try {
            dtSocket = new DatagramSocket(PORT);
            ////System.out.println("Serwer:konstruktor:dtSocket = " +  dtSocket.getLocalPort());
        } catch (SocketException ex) {
            System.err.println("Serwer:SocketExeption " + ex);
            //Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
        }
        dtPacket = new DatagramPacket(bufor, BUFOR_SIZE);
	}
	


    private void rozeslijPakiet(int soj, String wiadomosc)
    {
        //bufor = new byte[BUFOR_SIZE];
        //bufor = empty;
        bufor = ("PACK;"+wiadomosc).getBytes();
        dtPacket.setData(bufor);
        dtPacket.setPort(INET_PORT);
        for (int i = 0; i < serwerTCP.sockety.size(); i++) {
            if (serwerTCP.sockety.elementAt(i).sojusz() == soj || soj == 0)
            {
                dtPacket.setAddress(serwerTCP.sockety.elementAt(i).port.getInetAddress());
                try {
                    dtSocket.send(dtPacket);
                    //bufor = empty;
                    ////System.out.println("Serwer:rozeslijPakiet:wyslalem " + wiadomosc + " do " + dtPacket.getSocketAddress());
                } catch (IOException ex) {
                    System.out.println("Blad:Serwer: " + ex);
                }
            }
        }
    }

    private void rozeslijWiadomosc(int soj, String wiadomosc)
    {
        //int soj = Integer.parseInt(wiadomosc.split(";", 2)[0]);
        //wiadomosc = wiadomosc.split(";",2)[1];
         for (int i = 0; i < serwerTCP.sockety.size(); i++)
         {
            if (serwerTCP.sockety.elementAt(i).sojusz() == soj || soj == 0)
            {
                //bufor = new byte[BUFOR_SIZE];
                //bufor = empty;
                bufor = ("MESS;"+wiadomosc).getBytes();
                dtPacket.setAddress(serwerTCP.sockety.elementAt(i).port.getInetAddress());
                dtPacket.setData(bufor);
                try
                {
                    dtSocket.send(dtPacket);
                    //bufor = empty;
                    ////System.out.println("Serwer:Wyslalem wiadomosc: " + wiadomosc + " do " + serwerTCP.sockety.elementAt(i).daneGracza.id);

                } catch (IOException ex)
                {
                    System.out.println("Blad:Serwer: " + ex);
                }
            }
          }
    }

    private void listaGraczy()
    {
        String lista = new String("PLAY;");
        for (int i = 0; i < serwerTCP.sockety.size(); i++) {
            lista += (serwerTCP.sockety.elementAt(i).daneGracza.id + ";" +
                    serwerTCP.sockety.elementAt(i).daneGracza.sojusz + ";" +
                    serwerTCP.sockety.elementAt(i).daneGracza.nick + ";" +
                    serwerTCP.sockety.elementAt(i).daneGracza.fragi);
        }
       
        bufor = lista.getBytes();
        for (int i = 0; i < serwerTCP.sockety.size(); i++) {
            dtPacket.setAddress(serwerTCP.sockety.elementAt(i).port.getInetAddress());
            try {
                dtSocket.send(dtPacket);
            } catch (IOException ex) {
                Logger.getLogger(Serwer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
	
	public void run()
	{
        serwerIn = new Serwer_in(Thread.currentThread());
        serwerInThread = new Thread(serwerIn);
		serwerInThread.start();
		serwerTCPThread.start();
        String wiadomosc = null;
        String head;
        while(true)
        {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex)
            {
                wiadomosc = serwerIn.getMessage().trim();
                if (!wiadomosc.equals("EMPTY"))
                {
                    head = wiadomosc.split(";",2)[0];
                    wiadomosc = wiadomosc.split(";",2)[1];
                    if (head.equals("DATA"))
                    {
                        int id = Integer.parseInt(wiadomosc.split(";",3)[0]);
                        for (int i = 0; i < serwerTCP.sockety.size(); i++)
                        {
                            if (serwerTCP.sockety.elementAt(i).daneGracza.id == id)
                            {
                                serwerTCP.sockety.elementAt(i).daneGracza.sojusz = Integer.parseInt(wiadomosc.split(";",3)[1]);
                                serwerTCP.sockety.elementAt(i).daneGracza.nick = wiadomosc.split(";",3)[2];
                                break;
                            }
                            listaGraczy();
                        }
                    }
                else if(head.equals("PACK"))
                {
                    int soj = Integer.parseInt(wiadomosc.split(";",2)[0]);
                    wiadomosc = wiadomosc.split(";",2)[1];
                    rozeslijPakiet(soj, wiadomosc);
                }
                else if (head.equals("MESS"))
                {
                    int soj = Integer.parseInt(wiadomosc.split(";",2)[0]);
                    wiadomosc = wiadomosc.split(";",2)[1];
                    rozeslijWiadomosc(soj, wiadomosc);
                }
                }
            }
            
            Thread.yield();
        }
    }
}