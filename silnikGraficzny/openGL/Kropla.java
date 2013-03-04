package openGL;

public class Kropla 
{
	Point3D pozycja;
	Kropla()
	{
		pozycja = new Point3D(0, 0, 0);
	}
	Kropla(float pX, float pY, float pZ)
	{
		pozycja = new Point3D(pX, pY, pZ);
	}	
}
