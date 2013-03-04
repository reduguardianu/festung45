package warGem;

public class Point {
	private float x;
	private float y;
	private float z;
	
	public Point(float x, float y, float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public float getX()
	{
		return this.x;
	}
	public float getY()
	{
		return this.y;
	}
	public float getZ()
	{
		return this.z;
	}
	public void changeCoordinates(float nX, float nY, float nZ)
	{
		x=nX;
		y=nY;
		z=nZ;
	}

}
