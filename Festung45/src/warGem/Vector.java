package warGem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Vector {
	private Point p; //punkt zaczepienia
	
	public float dx;//przesunięcie
	public float dy;
	public float dz;
	
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();
	
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
		float l;
		readLock.lock();
		l=this.dx;
		readLock.unlock();
		return l;
	}
	public float getY()
	{
		float l;
		readLock.lock();
		l=this.dy;
		readLock.unlock();
		return l;
	}
	public float getZ()
	{
		float l;
		readLock.lock();
		l=this.dz;
		readLock.unlock();
		return l;
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
	public void setLength(double newLength)
	{
		double currentLength=this.length();
		dx/=currentLength;
		dy/=currentLength;
		dz/=currentLength;
		dx*=newLength;
		dy*=newLength;
		dz*=newLength;
		/*double xyCastLength=Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
		double dXY=newLength/currentLength-1;
		this.dx=(float) ((dXY+xyCastLength)*dx);
		this.dy=(float) ((dXY+xyCastLength)*dy);
		this.dz=(float) (dz*currentLength/newLength);*/
	}
	public Point getVectorHandle()
	{
		return p;
	}
}
