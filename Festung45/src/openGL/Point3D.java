package openGL;

public class Point3D 
{
	float x;
	float y;
	float z;
	
	public Point3D()
	{
		x = Float.NaN;
		y = Float.NaN;
		z = Float.NaN;
	}
	public Point3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
}
