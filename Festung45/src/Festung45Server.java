import festungSiec.*;
public class Festung45Server {
	

	Serwer server=new Serwer();
	Thread serverThread=new Thread(server);
	
	public Festung45Server() {
		// TODO Auto-generated constructor stub
		serverThread.start();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true)
		{
			Thread.yield();
		}
				
	}

}
