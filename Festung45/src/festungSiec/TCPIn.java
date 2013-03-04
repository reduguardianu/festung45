package festungSiec;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPIn implements Runnable
{
    public GraczSieciowy daneGracza;
    public Socket port;
    private String wiadomosc;
    private Vector<String> wiadomosci;
    private BufferedReader inFromClient;
	private DataOutputStream outToClient;

    public TCPIn(int soj, String nick, Socket sock)
    {
        daneGracza = new GraczSieciowy(nick, soj);
        port = sock;
        try{
        inFromClient = new BufferedReader(new InputStreamReader(port.getInputStream()));
        outToClient = new DataOutputStream(port.getOutputStream());
        } catch (IOException e)
        {

        }
        wiadomosci = new Vector<String>();     
    }

    public TCPIn(Socket sock, BufferedReader bufre, DataOutputStream dostr)
    {
        daneGracza = new GraczSieciowy("imie", 1);
        port = sock;
        inFromClient = bufre;
        outToClient = dostr;
        wiadomosci = new Vector<String>();
    }

    public int sojusz()
    {
        return daneGracza.sojusz;
    }

    public void wyslij(String wiadomosc)
    {
        try{
        outToClient.writeBytes(wiadomosc);
        } catch (IOException e)
        {
            System.out.println("TCPIn:Blad: " + e);
        }
        try{
        outToClient.flush();
        }catch(IOException e)
        {

        }
    }

    public String odbierz()
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

    public void run()
    {
        //Pusto.
        //Pozostalosc po TCP.
    }

    
}

