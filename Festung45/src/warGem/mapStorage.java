package warGem;

import openGL.Point3D;
import openGL.Renderer;
import sun.misc.Perf;

//ampStorage jest Singletonem
public final class mapStorage {
	
	private static volatile mapStorage instance = null;
	private java.util.Vector<PlayersUnit> unitsList;
	private java.util.Vector<EnvObject>   environment;
	private java.util.Vector<Bullet>      bullets;
	private java.util.Vector<Integer>     bulletsFreeSlots;
	public Renderer render=null;
	public static Perf p;
	private static long freq;
	private long oldTick;
	private long newTick;
	openGL.Interface localInterface = new openGL.Interface();

	public static mapStorage getInstance()						//pobierz uchwyt do mapStorage'a
	{
		if(instance==null)
			synchronized (mapStorage.class) {
				if(instance==null)
					instance=new mapStorage();
								
			}
		p=sun.misc.Perf.getPerf();
		freq=p.highResFrequency();
		return instance;
	}
	public int addUnit(PlayersUnit newUnit)			//dodaj jednostkę i zwróć jej ID w tabeli jednostek
	{
		if(unitsList==null)
			unitsList=new java.util.Vector<PlayersUnit>();
		unitsList.add(newUnit);
		return (unitsList.capacity()-1);
	}
	public void addObject(EnvObject newObject)			//dodaj obiekt otoczenia
	{
		environment.add(newObject);
	}
	public int addBullet(Bullet newBullet)				//dodaj pocisk
	{
		if(bulletsFreeSlots.isEmpty())
		{
			bullets.add(bullets.capacity()+1, newBullet);
			return bullets.capacity();
		}
		int slot=bulletsFreeSlots.remove(bulletsFreeSlots.capacity()-1);
		bullets.set(slot, newBullet);
		return slot;
	}
	public void removeBullet(int bulletID)				//usuń pocisk
	{
		bulletsFreeSlots.add(bulletID);
		bullets.remove(bulletID);
	}
	public void updateUnit(int unitID,PlayersUnit updatedUnit)//uaktualnia stan jednostki gracza
	{
		unitsList.set(unitID, updatedUnit);
	}
	public void updateRender(int Id)
	{
		unitsList.get(0).positionUpdate();
		if(Math.abs(unitsList.get(Id).getMoveVector().getVectorHandle().getY())>600)
			System.out.println(unitsList.get(Id).getMoveVector().getVectorHandle().getY());
		localInterface.setCamPosition(new Point3D((float)unitsList.get(Id).getMoveVector().getVectorHandle().getX()+unitsList.get(Id).getCameraVector().getVectorHandle().getX(),
												  (float)unitsList.get(Id).getMoveVector().getVectorHandle().getZ()+unitsList.get(Id).getCameraVector().getVectorHandle().getZ(),
												  (float)unitsList.get(Id).getMoveVector().getVectorHandle().getY()+unitsList.get(Id).getCameraVector().getVectorHandle().getY()));
		Vector temp=unitsList.get(Id).getCameraVector();
		temp.setLength(2);
		unitsList.get(Id).getMoveVector().getVectorHandle().changeCoordinates((float)unitsList.get(Id).getMoveVector().getVectorHandle().getX(),
				(float)unitsList.get(Id).getMoveVector().getVectorHandle().getY(),
				(float)unitsList.get(Id).getMoveVector().getVectorHandle().getZ());
		localInterface.setCamTarget(new Point3D((float)unitsList.get(Id).getMoveVector().getVectorHandle().getX()+unitsList.get(Id).getCameraVector().getVectorHandle().getX()+temp.getX(),
												(float)unitsList.get(Id).getMoveVector().getVectorHandle().getZ()+unitsList.get(Id).getCameraVector().getVectorHandle().getZ()+temp.getZ(),
												(float)unitsList.get(Id).getMoveVector().getVectorHandle().getY()+unitsList.get(Id).getCameraVector().getVectorHandle().getY()+temp.getY()));
		localInterface.setTankPosition(new Point3D(unitsList.get(Id).getMoveVector().getVectorHandle().getX(),
											    unitsList.get(Id).getMoveVector().getVectorHandle().getZ(),
											    unitsList.get(Id).getMoveVector().getVectorHandle().getY()));
		
		localInterface.setTankTurn(new Point3D(0,((Tank)unitsList.get(Id)).getDriveAngle(),0), 0);
		localInterface.setTankTurn(new Point3D(0,((Tank)unitsList.get(Id)).getCannonTorsion(),0), 1);
		localInterface.setTankTurn(new Point3D(0,0,0), 2);
				
		localInterface.setTick(newTick);
		
		render.interf.copy(localInterface);
	}
	protected mapStorage(){}							//zabezpieczenie przed utworzeniem publicznego konstruktora
	public long getFreq() {
		return freq;
	}
	public long getOldTick() {
		return oldTick;
	}
	public void setNewTick() {
		synchronized (p) {
			this.oldTick=this.newTick;
			this.newTick = p.highResCounter();	
		}
	}
	public long getNewTick() {
		return newTick;
	}
	public int getID(PlayersUnit o){
		return unitsList.indexOf(o);
	}
}
