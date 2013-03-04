package festungSiec;

import java.net.InetSocketAddress;

class Main
{
	public static void main(String [ ] args)
	{
		Serwer serwer = new Serwer();
		Klient klient1 = new Klient();
		Thread serwerThread = new Thread(serwer);
		Thread klient1Thread = new Thread(klient1);
        InetSocketAddress soAd = new InetSocketAddress("127.0.0.1", 26667);
		
		serwerThread.start();
		klient1Thread.start();

        klient1.connectToServer(1, "Gamer", soAd);
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println("Main:watek Przerwany");
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < 300; i++) {

        klient1.sendPacket(1, "p" + i);
        Thread.yield();
          }

        for (int i = 0; i < 300; i++) {
            klient1.sendMessage(1, "Wiadomosc " + i);
            Thread.yield();
        }

        System.out.println("Pakiety:");
        String mes;
       while(!(mes = klient1.getPacket()).equals("EMPTY"))
            System.out.println(mes);

        System.out.println("Wiadomosci:");
        while(!(mes = klient1.getMessage()).equals("EMPTY"))
            System.out.println(mes);


        System.out.println("Koniec Programu");
        klient1Thread.stop();
        serwerThread.stop();
        System.exit(1);
    }
}