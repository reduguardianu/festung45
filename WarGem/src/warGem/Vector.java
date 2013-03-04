package warGem;


public class Vector {
	private Point p; //punkt zaczepienia
	
	private float dx;//przesunięcie
	private float dy;
	private float dz;
	
	public Vector(float x,float y,float z,float dx,float dy,float dz)
	{
        p=new Point(x,y,z);
		
		this.dx=dx;
		this.dy=dy;
		this.dz=dz;
	}
	public float length()//zwraca długość wektora, pomocne przy wyliczaniu prędkości obiektu
	{
		return (float) Math.sqrt(Math.pow(this.dx, 2)+Math.pow(this.dy, 2)+Math.pow(this.dz, 2));
	}
	public float getX()
	{
		return p.getX();
	}
	public float getY()
	{
		return p.getY();
	}
	public float getZ()
	{
		return p.getZ();
	}
	public void setBeginning(float x,float y,float z)
	{
		p.changeCoordinates(x, y, z);
	}
	public void newOffset(float dx, float dy, float dz)
	{
		this.dx=dx;
		this.dy=dy;
		this.dz=dz;
	}
}
