package warGem;

public class EnvObject {
	private float width;
	private float height;//w dalszej wersji może się tu pojawić osobna klasa, opisująca funkcję i przedział,
						 //na podstawie których tworzone będą krawędzie budynków
	
	private short condition=10; //stan budynku od 0 do 10
	private Point p;		 //punkt zaczepienia lewego dolnego rogu budynku
	private int angle;	 //kąt nachylenia wobec linii poziomej przechodzącej przez ten punkt
	
	public EnvObject(float w,float h,float x,float y,float z,int ang)
	{
		width=w;
		height=h;
		p=new Point(x,y,z);
		angle=ang;
	}
	public float getWidth()
	{
		return width;
	}
	public float getHeight()
	{
		return height;
	}
	public short getCond()
	{
		return condition;
	}
	public Point getTap()
	{
		return p;
	}
	public int getAngle()
	{
		return angle;
	}
	
}
