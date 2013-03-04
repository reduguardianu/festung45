package openGL;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

import javax.swing.JFrame;
import net.java.games.jogl.*;

public class main 
{
	public static Animator animator = null;
	
	public static void main(String arg[])
	{
		JFrame frame = new JFrame("Moje okno");
		
		frame.setUndecorated(true);
		GraphicsEnvironment.getLocalGraphicsEnvironment().
	      getDefaultScreenDevice().setFullScreenWindow(frame);
		
		GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(new GLCapabilities());
		canvas.addGLEventListener(new Renderer());
		canvas.setAutoSwapBufferMode(true);
		frame.add(canvas);
		animator = new Animator(canvas);

	    frame.addWindowListener(new WindowAdapter()
	    {
	      public void windowClosing(WindowEvent e)
	      {
	    	  animator.stop();
	          System.exit(0);
	      }
	    });
	    frame.show();
	    animator.start();
	    canvas.requestFocus();
	}
}
