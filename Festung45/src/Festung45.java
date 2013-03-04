import java.awt.Cursor;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JFrame;

import openGL.Renderer;
import warGem.MessageBox;
import warGem.keyboardHandle;
import warGem.mapStorage;
import warGem.mouseHandle;
import warGem.warGemClient;
import warGem.Message;


import net.java.games.jogl.Animator;
import net.java.games.jogl.GLCanvas;
import net.java.games.jogl.GLCapabilities;
import net.java.games.jogl.GLDrawableFactory;

public class Festung45 implements MouseMotionListener, KeyListener {

	/**
	 * @param args
	 */
	private static Animator animator = null;
	private static warGemClient client;
	private static Boolean end = false;
	private static mapStorage map;
	private static mouseHandle mouse = new mouseHandle();
	private static keyboardHandle keyboard= new keyboardHandle();
	private static MessageBox mail;
	public static void main(String[] args) {
		JFrame frame = new JFrame("Moje okno");
		
		frame.setUndecorated(true);
		GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(frame);

		GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(
				new GLCapabilities());
		map = mapStorage.getInstance();
		Renderer r = new Renderer(map.getFreq());
		map.render = r;

		client = new warGemClient();
		mail=new MessageBox("127.0.0.1","Red",false);
		int[] pixels = new int[16 * 16];
		Image image = Toolkit.getDefaultToolkit().createImage(
				new MemoryImageSource(16, 16, pixels, 0, 16));
		Cursor transparentCursor = Toolkit.getDefaultToolkit()
				.createCustomCursor(image, new java.awt.Point(0, 0),
						"invisibleCursor");
		canvas.setCursor(transparentCursor);
		canvas.addGLEventListener(r);
		canvas.setAutoSwapBufferMode(true);
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mouse.mouseMoved(e.getLocationOnScreen(),client);
				
			}
		});
		canvas.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				keyboard.keyPressed(client,e.getKeyCode());
			}

			public void keyReleased(KeyEvent e){
			}
		});
		frame.add(canvas);
		animator = new Animator(canvas);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				animator.stop();
				System.exit(0);
			}
		});

		frame.setVisible(true);
		mouse.resetMousePosition();
		animator.start();

		canvas.requestFocus();
		while (!end) {
			mail.getSignal();
			for(int i=0;i<mail.inboxSize();i++)
			{
				//Message Temp=mail.getInbox();
				//map.updateUnit(Temp.getiD(), )
				
			}
			map.setNewTick();
			client.updateMap();
			map.updateRender(0);
			
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			animator.stop();
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
