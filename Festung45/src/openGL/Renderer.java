package openGL;

import java.io.IOException;
import java.util.Random;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLEventListener;
import net.java.games.jogl.GLU;

public class Renderer implements GLEventListener
{
	GLU           glu;
	GL            gl;
	
	float             
	LightPos[]  = { 0.0f, 5.0f,-4.0f, 1.0f},   // Light Position
	LightAmb[]  = { 0.2f, 0.2f, 0.2f, 1.0f},   // Ambient Light Values
	LightDif[]  = { 0.6f, 0.6f, 0.6f, 1.0f},   // Diffuse Light Values
	LightSpc[]  = {1.0f, 1.0f, 1.0f, 1.0f}, // Specular Light Values
	
	MatAmb[]    = {0.4f, 0.4f, 0.4f, 1.0f},    // Material - Ambient Values
	MatDif[]    = {0.2f, 0.6f, 0.9f, 1.0f},    // Material - Diffuse Values
	MatSpc[]    = {0.0f, 0.0f, 0.0f, 1.0f},    // Material - Specular Values
	MatShn[]    = {0.0f},                      // Material - Shininess
	
	SpherePos[] = {-4.0f,-5.0f,-6.0f};
	
	private float[] fogColor = { 0.5f, 0.5f, 0.5f, 1.0f};
	private int[] fogMode = { GL.GL_EXP, GL.GL_EXP2, GL.GL_LINEAR };
	private int fogFilter = 0;
	Random rand = new Random();
	
	static Point3D kamera = new Point3D(10.0f, 4.0f, 10.0f);
	
	double czas;
	long oldTick;
	
	Czolg Tiger;
	Czolg T34;
	Obiekt3D Gaj;
	Obiekt3D pocisk;
	public static MapaWysokosci mapa = null;//new MapaWysokosci();
	
	Deszcz deszcz = new Deszcz(2500);
	Point3D poz = new Point3D(-5, 0, -5);
	Point3D v = new Point3D(0.5f, 2.0f, 0.0f);
	Point3D rozrzut = new Point3D((float)(Math.PI/3), 0,(float)(Math.PI/3));
	SystemCzasteczkowy ogien;
	SystemCzasteczkowy dym;
	SystemCzasteczkowy kurz;
	ParticleManager particleManager = new ParticleManager();
	static int[] texOgien;
	static int[] texDym;
	static int[] texKurz;
	public static boolean keys[]= new boolean[256];
	//public static sun.misc.Perf p = sun.misc.Perf.getPerf();
	//public static long frequency = p.highResFrequency();
	//public static long tick = p.highResCounter();
	
	public volatile Interface interf;
	static Interface localInterface;
	
	public Renderer(long freq)
	{
		interf = new Interface();
		interf.setFrequency(freq);
		
		localInterface = new Interface();
		localInterface.setFrequency(freq);
	}
    
	public void display(GLDrawable gLDrawable)
	{
		localInterface.copy(interf);
		
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		/*if(ogien == null) 
		{
			ogien = new SystemCzasteczkowy(texOgien[1], 6, localInterface.getFrequency() * 6, new Point3D(-10.0f, 1.0f, 10.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
			dym = new SystemCzasteczkowy(texDym[1], 10, localInterface.getFrequency() * 10, new Point3D(-5.0f, 1.0f, 5.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
			kurz = new SystemCzasteczkowy(texKurz[1], 6, localInterface.getFrequency() * 6, new Point3D(10.0f, 1.0f, 15.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
		}*/
		if(particleManager.particleSystemsCount() == 0)
		{
			particleManager.add(texOgien[1], 6, localInterface.getFrequency() * 6, new Point3D(-10.0f, 1.0f, 10.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
			particleManager.add(texDym[1], 10, localInterface.getFrequency() * 10, new Point3D(-5.0f, 1.0f, 5.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
			particleManager.add(texKurz[1], 6, localInterface.getFrequency() * 6, new Point3D(10.0f, 1.0f, 15.0f), localInterface.getFrequency(), localInterface.getTick(), new Point3D(0, 1.0f, 0), rozrzut, 3f, true);
		}
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glLoadIdentity();
		glu.gluLookAt(localInterface.getCamPosition().x, 
				localInterface.getCamPosition().y, 
				localInterface.getCamPosition().z,  
				localInterface.getCamTarget().x, 
				localInterface.getCamTarget().y, 
				localInterface.getCamTarget().z,  0, 1, 0);
		  
		mapa.Render(gl);
		  
		for(int i = 0; i < localInterface.getTankPosition().size(); i++)
		{
			Tiger.przesun(localInterface.getTankPosition().get(i).x, 
					localInterface.getTankPosition().get(i).y, localInterface.getTankPosition().get(i).z);
			Tiger.obroc("korpus", localInterface.getTankTurn(0).get(i).x, localInterface.getTankTurn(0).get(i).y, localInterface.getTankTurn(0).get(i).z);
			Tiger.obroc("wieza", localInterface.getTankTurn(1).get(i).x, localInterface.getTankTurn(1).get(i).y, localInterface.getTankTurn(1).get(i).z);
			Tiger.obroc("lufa", localInterface.getTankTurn(2).get(i).x, localInterface.getTankTurn(2).get(i).y, localInterface.getTankTurn(2).get(i).z);
			Tiger.Render(gl);
		}
		
		for(int i = 0; i < localInterface.getTankPosition().size(); i++)
		{
			pocisk.przesun(localInterface.getBulletPosition().get(i).x, localInterface.getBulletPosition().get(i).y, localInterface.getBulletPosition().get(i).z);
			pocisk.obroc(localInterface.getBulletTurn().get(i).x, localInterface.getBulletTurn().get(i).y, localInterface.getBulletTurn().get(i).z);
			
			pocisk.Render(gl);
		}
		
		//T34.przesun(0.0f, 0.0f, 2.0f);
		//T34.Render(gl);
		
		Gaj.przesun(0, 0, 0);
		Gaj.Render(gl);
		  
		gl.glLoadIdentity();
		gl.glTranslated(0, 15, 0);
		gl.glLoadIdentity();
		glu.gluLookAt(localInterface.getCamPosition().x, 
				localInterface.getCamPosition().y, 
				localInterface.getCamPosition().z,  
				localInterface.getCamTarget().x, 
				localInterface.getCamTarget().y, 
				localInterface.getCamTarget().z,  0, 1, 0);
		  
		czas = (double)(localInterface.getTick() - oldTick) / localInterface.getFrequency();
		oldTick = localInterface.getTick();
		
		deszcz.przemiesc(czas, localInterface.getCamTarget());
		deszcz.Render(gl);
		/*if(ogien != null)
		{
			ogien.przemiesc(czas, -10.0f, 1.0f, 10.0f);
			ogien.Render(gl, localInterface.getCamPosition());
			dym.przemiesc(czas, -5.0f, 1.0f, 5.0f);
			dym.Render(gl, localInterface.getCamPosition());
			kurz.przemiesc(czas, 10.0f, 1.0f, 15.0f);
			kurz.Render(gl, localInterface.getCamPosition());
		}*/
		if(particleManager.particleSystemsCount() != 0)
		{
			for(int i = 0; i < particleManager.particleSystemsCount(); i++)
			{
				particleManager.move(i, czas, new Point3D(10 * i, 0, 0));
			}
			particleManager.render(gl, localInterface.getCamPosition());
		}
		
		/**********************************************************
		 ********************* TU JEST KOD MAPY ******************* 
		 **********************************************************/
		  
		/*Point3D wek1;
		  Point3D wek2;
		  Point3D wek3;
		  float pom = (float)Math.sqrt(wek.x * wek.x + wek.y * wek.y + wek.z * wek.z);
		  
		  wek1 = new Point3D(wek.x, wek.y, wek.z);
		  wek1.x /= pom * 190;
		  wek1.y /= pom * 190;
		  wek1.z /= pom * 190;
		  wek2 = new Point3D(-wek1.z, 0.0f, wek1.x);
		  wek3 = new Point3D(wek1.y * wek2.z - wek2.y * wek1.x, 
				  wek1.z * wek2.x - wek2.z * wek1.x, wek1.x * wek2.y - wek2.x * wek1.y);
		  pom = (float)Math.sqrt(wek3.x * wek3.x + wek3.y * wek3.y + wek3.z * wek3.z);
		  wek2.x /= pom * 160000;
		  wek2.y /= pom * 160000;
		  wek2.z /= pom * 160000;
		  wek3.x /= pom * 1000;
		  wek3.y /= pom * 1000;
		  wek3.z /= pom * 1000;
		  gl.glBegin(GL.GL_TRIANGLE_STRIP);
		  	gl.glVertex3f(kamera.x + wek1.x + wek2.x + wek3.x, 
		  			kamera.y + wek1.y + wek2.y + wek3.y, 
		  			kamera.z + wek1.z + wek2.z + wek3.z);
		  	
		  	gl.glVertex3f(kamera.x + wek1.x + wek2.x + wek3.x * 2, 
		  			kamera.y + wek1.y + wek2.y + wek3.y * 2, 
		  			kamera.z + wek1.z + wek2.z + wek3.z * 2);
		  	
		  	gl.glVertex3f(kamera.x + wek1.x + wek2.x * 2 + wek3.x, 
		  			kamera.y + wek1.y + wek2.y * 2 + wek3.y, 
		  			kamera.z + wek1.z + wek2.z * 2 + wek3.z);
		  	
		  	gl.glVertex3f(kamera.x + wek1.x + (wek2.x + wek3.x) * 2, 
		  			kamera.y + wek1.y + (wek2.y + wek3.y) * 2, 
		  			kamera.z + wek1.z + (wek2.z + wek3.z) * 2);
		  gl.glEnd();*/
		  //System.out.println(1/czas);
		  
		gl.glFlush();
	}
	
	
	 /** Called when the display mode has been changed.  <B>!! CURRENTLY UNIMPLEMENTED IN JOGL !!</B>
	* @param gLDrawable The GLDrawable object.
	* @param modeChanged Indicates if the video mode has changed.
	* @param deviceChanged Indicates if the video device has changed.
	*/
	public void displayChanged(GLDrawable gLDrawable, boolean modeChanged, boolean deviceChanged)
	{
	}
	
	 /** Called by the drawable immediately after the OpenGL context is 
	* initialized for the first time. Can be used to perform one-time OpenGL 
	* initialization such as setup of lights and display lists.
	* @param gLDrawable The GLDrawable object.
	*/
	public void init(GLDrawable gLDrawable)
	{
		gl = gLDrawable.getGL();
		glu = gLDrawable.getGLU();
		   
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	   
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);					// Full Brightness.  50% Alpha (new )
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);	
		gl.glEnable(GL.GL_TEXTURE_2D);
		   
		gl.glLightfv(GL.GL_LIGHT0,GL.GL_POSITION, LightPos);        // Set Light1 Position
		gl.glLightfv(GL.GL_LIGHT0,GL.GL_AMBIENT,  LightAmb);        // Set Light1 Ambience
		gl.glLightfv(GL.GL_LIGHT0,GL.GL_DIFFUSE,  LightDif);        // Set Light1 Diffuse
		gl.glLightfv(GL.GL_LIGHT0,GL.GL_SPECULAR, LightSpc);        // Set Light1 Specular
		   // Enable Light1
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_LIGHT0);                                // Enable Lighting
		
		   //gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT, MatAmb);         // Set Material Ambience
		   //gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE, MatDif);         // Set Material Diffuse
		   //gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR, MatSpc);        // Set Material Specular
		   //gl.glMaterialfv(GL.GL_FRONT,GL.GL_SHININESS, MatShn);       // Set Material Shininess
		   
		gl.glCullFace(GL.GL_BACK);                                  // Set Culling Face To Back Face
		gl.glEnable(GL.GL_CULL_FACE);
		   
		   //q = glu.gluNewQuadric();                                    // Initialize Quadratic
		   //glu.gluQuadricNormals(q,GL.GL_SMOOTH);                      // Enable Smooth Normal Generation
		   //glu.gluQuadricTexture(q,false);
		   
		gl.glClearColor( 0.5f, 0.5f, 0.5f, 1.0f);
		gl.glFogi(GL.GL_FOG_MODE, fogMode[fogFilter]);
		gl.glFogfv(GL.GL_FOG_COLOR, fogColor);
		gl.glFogf(GL.GL_FOG_DENSITY, 0.001f);
		gl.glHint(GL.GL_FOG_HINT, GL.GL_NICEST);
		gl.glFogf(GL.GL_FOG_START, 1.0f);
		gl.glFogf(GL.GL_FOG_END, 150.0f);
		gl.glEnable(GL.GL_FOG);
		float materialOdbicie[] ={ 1.0f, 1.0f, 1.0f, 1.0f};
			   //ustaw kolor odbicia
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, materialOdbicie);
		   // okre�l wsp�czynnik odbicia
		gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, 25.0f);
		gl.glEnable(GL.GL_SPECULAR);
		   
		texOgien = new int[2];
		texDym = new int[2];
		texKurz = new int[2];
		ladujBitmape(gl, glu, "ogien.bmp", texOgien);
		ladujBitmape(gl, glu, "dym.bmp", texDym);
		ladujBitmape(gl, glu, "kurz.bmp", texKurz);
		   
		Parser parser = new Parser();
		   
		Tiger = new Czolg(parser.sprawdzLiczbeCzesci("3d/Tiger2.ASE"));
		T34 = new Czolg(parser.sprawdzLiczbeCzesci("3d/T34-2.ASE"));
		Gaj = new Obiekt3D(parser.sprawdzLiczbeCzesci("3d/budynki.ASE"));
		pocisk = new Obiekt3D(parser.sprawdzLiczbeCzesci("3d/pocisk.ASE"));
		   
		parser.czytaj(Tiger, "3d/Tiger2.ASE", gl, glu);
		parser.czytaj(T34, "3d/T34-2.ASE", gl, glu);
		parser.czytaj(Gaj, "3d/budynki.ASE", gl, glu);
		parser.czytaj(pocisk, "3d/pocisk.ASE", gl, glu);
		   
		   //Tiger.kurz = new SystemCzasteczkowy[2];
		   //Tiger.kurz[0] = new SystemCzasteczkowy(texKurz[1], 4, frequency, new Point3D(0.0f, 0.0f, -5.0f), frequency, tick, new Point3D(0, 1.0f, 0), rozrzut, 1f, true);
		   //Tiger.kurz[1] = new SystemCzasteczkowy(texKurz[1], 4, frequency, new Point3D(0.0f, 0.0f, -5.0f), frequency, tick, new Point3D(0, 1.0f, 0), rozrzut, 1f, true);
		   
		mapa = new MapaWysokosci(gl, glu, "trawa.png", "mapa.bmp", 1200, 5.0f);
	}
	
	public void ladujBitmape(GL gl, GLU glu, String adres, int[] bitmap)
	{
		Texture texture = null;
		try
		{
			texture = TextureReader.readTexture(adres, true);
		}
		catch (IOException e)
		{
		}
		gl.glGenTextures(3, bitmap);
		gl.glBindTexture(GL.GL_TEXTURE_2D, bitmap[0]);
			
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
			
		gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, texture.getWidth(), 
						texture.getHeight(), 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
						texture.getPixels());
			       
		gl.glBindTexture(GL.GL_TEXTURE_2D, bitmap[1]);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
			
		glu.gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA8, 
						texture.getWidth(), texture.getHeight(), 
						GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, texture.getPixels());
	}
	
	
	 /** Called by the drawable during the first repaint after the component has 
	* been resized. The client can update the viewport and view volume of the 
	* window appropriately, for example by a call to 
	* GL.glViewport(int, int, int, int); note that for convenience the component
	* has already called GL.glViewport(int, int, int, int)(x, y, width, height)
	* when this method is called, so the client may not have to do anything in
	* this method.
	* @param gLDrawable The GLDrawable object.
	* @param x The X Coordinate of the viewport rectangle.
	* @param y The Y coordinate of the viewport rectanble.
	* @param width The new width of the window.
	* @param height The new height of the window.
	*/
	public void reshape(GLDrawable gLDrawable, int x, int y, int width, int height)
	{
		final GL gl = gLDrawable.getGL();
		final GLU glu = gLDrawable.getGLU();
			
		if (height <= 0) // avoid a divide by zero error!
			height = 1;
		final float h = (float)width / (float)height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 0.005, 1000.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		//tick = p.highResCounter();
	}
}