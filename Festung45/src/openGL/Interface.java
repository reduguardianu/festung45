package openGL;

import java.util.ArrayList;

public class Interface 
{
	private ArrayList<Point3D> bulletTurn;
	private ArrayList<Point3D> bulletPosition;
	private ArrayList<Point3D> tankPosition;
	private ArrayList<Point3D> tankTurn[];
	//private ArrayList<Point3D> camPosition;
	//private ArrayList<Point3D> camTarget;
	//private Point3D tankPosition;
	//private Point3D tankTurn[];
	private Point3D camPosition;
	private Point3D camTarget;
	private int drops;//liczba kropli deszczu(intensywność padania)
	private long tick;
	private long frequency;	
	private boolean semafor;
	
	public Interface()
	{
		tankPosition = new ArrayList<Point3D>();
		tankTurn = new ArrayList[3];
		for(int i = 0; i < 3; i++)
			tankTurn[i] = new ArrayList<Point3D>();
		camPosition = new Point3D();
		camTarget = new Point3D();
		drops = 0;
		tick = 0;
		frequency = 0;
		semafor = false;
	}
	
	public synchronized void copy(Interface interf)
	{
		/*while(this.semafor == true && interf.semafor == true)
			Thread.yield();*/
		
		this.semafor = true;
		interf.semafor = true;

		this.camPosition.x = interf.camPosition.x;
		this.camPosition.y = interf.camPosition.y;
		this.camPosition.z = interf.camPosition.z;
		this.camTarget.x = interf.camTarget.x;
		this.camTarget.y = interf.camTarget.y;
		this.camTarget.z = interf.camTarget.z;
		this.drops = interf.drops;
		//this.frequency = interf.frequency;
		for(int i = 0; i < 3; i++)
		{
			this.tankTurn[i] = new ArrayList<Point3D>();
			for(int j = 0; j < interf.tankTurn[i].size(); j++)
				this.tankTurn[i].add(new Point3D(interf.tankTurn[i].get(j).x, interf.tankTurn[i].get(j).y, interf.tankTurn[i].get(j).z));
		}
		this.tankPosition = new ArrayList<Point3D>();
		for(int i = 0; i < interf.tankPosition.size(); i++)
		{
			this.tankPosition.add(new Point3D(interf.tankPosition.get(i).x, interf.tankPosition.get(i).y, interf.tankPosition.get(i).z));
		}
		//this.tankPosition.x = interf.tankPosition.x;
		//this.tankPosition.y = interf.tankPosition.y;
		//this.tankPosition.z = interf.tankPosition.z;
		/*for(int i = 0; i < 3; i++)
		{
			this.tankTurn[i].x = interf.tankTurn[i].x;
			this.tankTurn[i].y = interf.tankTurn[i].y;
			this.tankTurn[i].z = interf.tankTurn[i].z;
		}*/
		this.tick = interf.tick;
		
		this.semafor = false;
		interf.semafor = false;
	}

	public Point3D getCamPosition() {
		return camPosition;
	}

	public void setCamPosition(Point3D camPosition) {
		this.camPosition = camPosition;
	}

	public Point3D getCamTarget() {
		return camTarget;
	}

	public void setCamTarget(Point3D camTarget) {
		this.camTarget = camTarget;
	}

	public int getDrops() {
		return drops;
	}

	public void setDrops(int drops) {
		this.drops = drops;
	}

	public long getFrequency() {
		return frequency;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public boolean isSemafor() {
		return semafor;
	}

	public void setSemafor(boolean semafor) {
		this.semafor = semafor;
	}

	public ArrayList<Point3D> getTankPosition() {
		return tankPosition;
	}

	public void setTankPosition(ArrayList<Point3D> tankPosition) {
		this.tankPosition = new ArrayList<Point3D>();
		for(int i = 0; i < tankPosition.size(); i++)
		{
			this.tankPosition.add(new Point3D(tankPosition.get(i).x, tankPosition.get(i).y, tankPosition.get(i).z));
		}
		//this.tankPosition = tankPosition;
	}

	public ArrayList<Point3D> getTankTurn(int nr) {
		return tankTurn[nr];
	}

	public void setTankTurn(ArrayList<Point3D> tankTurn, int nr) {
		//this.tankTurn[nr] = tankTurn;
		this.tankTurn[nr] = new ArrayList<Point3D>();
		for(int i = 0; i < tankTurn.size(); i++)
		{
			this.tankTurn[nr].add(new Point3D(tankTurn.get(i).x, tankTurn.get(i).y, tankTurn.get(i).z));
		}
	}
	
	public ArrayList<Point3D> getBulletPosition() {
		return bulletPosition;
	}

	public void setBulletPosition(ArrayList<Point3D> bulletPosition) {
		this.bulletPosition = new ArrayList<Point3D>();
		for(int i = 0; i < bulletPosition.size(); i++)
		{
			this.bulletPosition.add(new Point3D(bulletPosition.get(i).x, bulletPosition.get(i).y, bulletPosition.get(i).z));
		}
		//this.tankPosition = tankPosition;
	}
	
	public ArrayList<Point3D> getBulletTurn() {
		return bulletTurn;
	}

	public void setBulletTurn(ArrayList<Point3D> bulletTurn) {
		this.bulletTurn = new ArrayList<Point3D>();
		for(int i = 0; i < bulletTurn.size(); i++)
		{
			this.bulletTurn.add(new Point3D(bulletTurn.get(i).x, bulletTurn.get(i).y, bulletTurn.get(i).z));
		}
		//this.tankPosition = tankPosition;
	}

	public long getTick() {
		return tick;
	}

	public void setTick(long tick) {
		this.tick = tick;
	}
}