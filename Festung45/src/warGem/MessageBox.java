package warGem;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import festungSiec.*;

public class MessageBox {
			
			private ArrayList<Message> inBox;
			private ReentrantReadWriteLock lock= new ReentrantReadWriteLock();
			private Lock writeLock=lock.writeLock();
			private Lock readLock=lock.readLock();
			private Klient myClient;
			private Thread myClientThread;
			InetSocketAddress serverSocket;
			
			public MessageBox(String serverIPAddr,String nick,Boolean alliance)
			{
				myClient=new Klient();
				myClientThread=new Thread(myClient);
				serverSocket=new InetSocketAddress(serverIPAddr,26667);
				
				myClientThread.start();
				
				if(alliance)
					myClient.connectToServer(1, nick, serverSocket);
				else
					myClient.connectToServer(2, nick, serverSocket);
				try {
		            Thread.sleep(200);
		        } catch (InterruptedException ex) {
		            System.out.println("Main:watek Przerwany");
		            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		        }
				
			}
			public void sendSignal(Message outgoing)
			{
				myClient.sendPacket(1, outgoing.iD.toString()+outgoing.body);
			}
			public void getSignal()
			{
				String rawMessage;
				rawMessage=myClient.getPacket();
				while(!rawMessage.equals("EMPTY"))
				{
					inBox.add(0,new Message(rawMessage.codePointAt(0),rawMessage.substring(1)));
					rawMessage=myClient.getPacket();
				}
			}
			public Message getInbox()
			{
				Message temp=inBox.get(0);	//Pobierz najstarszą wiadomość
				inBox.remove(0);//Usuwa pobraną wiadomość
				return temp;
			}
			public int inboxSize()
			{
				int size;
				readLock.lock();
				size=inBox.size();
				readLock.unlock();
				return size;
			}
}
