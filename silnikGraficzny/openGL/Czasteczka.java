package openGL;

public class Czasteczka extends Kropla
{
	Point3D predkosc;
	long poczatekZycia;
	
	Czasteczka(float pX, float pY, float pZ, float vX, float vY, float vZ, long lT)
	{
		pozycja = new Point3D(pX, pY, pZ);
		predkosc = new Point3D(vX, vY, vZ);
		poczatekZycia = lT;
	}
}
