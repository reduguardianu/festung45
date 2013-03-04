package warGem;



public abstract class PlayersUnit {
	private Alliances ally;
	private PlayersUnitType type;
	private Vector moveVector=new Vector(0.0f,0.0f, 0.0f,0.0f,0.0f,0.0f);
	private Vector cameraVector=new Vector(0.0f,-12.0f, 6.0f,0.0f,1.0f,0.0f);;
	private mapStorage map=mapStorage.getInstance();
	private Boolean status;//false - martwy, true - żywy

	
	public abstract void fire();
	public abstract void positionUpdate();
	//public abstract void backwards();
	public abstract void turnLeft();
	public abstract void turnRight();
	
	public void setAlly(Alliances ally)
	{
		this.ally=ally;
	}
	public Alliances getAlly()
	{
		return ally;
	}
	public Vector getMoveVector()
	{
		return this.moveVector;
	}
	public float getMoveVectorLength()
	{
		return this.moveVector.length();
	}
	public void setMoveVector(float x,float y,float z,float dx,float dy,float dz)
	{
		if(this.moveVector==null)
			this.moveVector=new Vector(x,y,z,dx,dy,dz);
		else
		{
			this.moveVector.newOffset(dx, dy, dz);
			this.moveVector.setBeginning(x, y, z);
		}
	}
	public Vector getCameraVector()
	{
		return this.cameraVector;
	}
	public void setCameraVector(float x,float y,float z,float dx,float dy,float dz)
	{
		if(this.cameraVector==null)
			this.cameraVector=new Vector(x,y,z,dx,dy,dz);
		else
		{
			this.cameraVector.newOffset(dx, dy, dz);
			this.cameraVector.setBeginning(x, y, z);
		}
	}
	public Boolean getStatus()
	{
		return status;
	}
	public Boolean check()
	{
		return status;
	}
	public void create()
	{
		status=true;
	}
	public void destroy()
	{
		status=false;
	}
	public void setType(PlayersUnitType nType)
	{
		this.type=nType;
	}
}