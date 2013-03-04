package warGem;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import warGem.warGemClient;


public class keyboardHandle {
	private Robot reseter;
	
	public keyboardHandle(){
		try {
			reseter = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void keyPressed(warGemClient client,int keyCode)
	{
		if(keyCode==KeyEvent.VK_UP)
		{
			client.up();
		}
		if(keyCode==KeyEvent.VK_DOWN)
		{
			client.down();
		}
		if(keyCode==KeyEvent.VK_LEFT)
		{
			client.left();
		}
		if(keyCode==KeyEvent.VK_RIGHT)
		{
			client.right();
		}
			
	}
}
