package openGL;

import net.java.games.jogl.GL;

public abstract class Obiekt 
{
	int[] tekstura;
	private Point3D pozycja;
	
	Obiekt()
	{
		pozycja = new Point3D(0.0f, 0.0f, 0.0f);
	}
	
	public void przesun(float X, float Y, float Z)
	{
		pozycja.x = X;
		pozycja.y = Y;
		pozycja.z = Z;
	}
	
	abstract public void Render(GL gl);
}