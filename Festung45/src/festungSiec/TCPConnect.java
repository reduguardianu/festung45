package festungSiec;

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPConnect implements Runnable
{
    private ServerSocket tcpServerSocket;
    private Vector<Socket> sockety;
    private final int PORT = 26667;

    public TCPConnect()
    {
        try{
        tcpServerSocket = new ServerSocket(PORT);
        } catch (IOException e)
        {
            System.out.println("Blad:TCPConnect:Constructor: " + e.getMessage());
        }
        sockety = new Vector<Socket>();
    }

    public Socket noweSockety()
    {
        if (sockety.size() > 0)
        {
            return sockety.remove(0);
        }
        else
        {
            return null;
        }
    }

    public void run()
    {
        try{
            Socket tempSocket = tcpServerSocket.accept();
            if (tempSocket != null)
            {
                sockety.add(tempSocket);
                ////
                tempSocket.close();
            }
        } catch (IOException e)
        {
            System.err.println("TCPConnect:Blad: " + e.getMessage());
        }

    }
}