package openGL;


public class Czesc
{	
	int liczbaWierzcholkow;
	int liczbaScianek;
	public Wierzcholek[] wierz;
	public Scianka[] scian;
	Point3D pivot;
	Point3D obrot;
	float[][] TexVert;
	String nazwa;
	
	public Czesc(String name)
	{
		nazwa = name;
	}
	
	public void Ustaw(int numWierz, int numScian)
	{	
		liczbaWierzcholkow = numWierz;
		liczbaScianek = numScian;
		wierz = new Wierzcholek[numWierz];
		scian = new Scianka[numScian];
	}
	
	public void inicjujTexVert(int numTexVert)
	{
		TexVert = new float[numTexVert][2];
	}
	
	public void UstawPivot(float X, float Y, float Z)
	{
		pivot = new Point3D(X, Y, Z);
		obrot = new Point3D(0, 0, 0);
	}
}
