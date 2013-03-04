package warGem;

public class Tank extends PlayersUnit {
	private int weight;
	private int firePower;
	private int width;
	private int length;
	private int height;
	private int ammo;
	private int cannonTorsion=0;
	private int cannonAngle=0;
	private int maxSpeed;
	private int maxPitch;
	private int health;
	
	
	public Tank(Alliances ally)
	{
		this.setAlly(ally);
		this.setMoveVector((float)0,(float) 0, (float)0,(float) 0,(float) 0, (float)0);
		this.setCameraVector((float)1, (float)1, (float)0, (float)0, (float)0, (float)0);
		this.create();
		this.setType(PlayersUnitType.Tank);
		switch(this.getAlly())
		{
		case redArmy:
		{
			//Dane Kaczuchy
		}
		case wermacht:
		{
			//Opisy Tygrysa
		}
		
	}
		
		
	}
	
	public void backwards() {
		// TODO Auto-generated method stub

	}
	public void fire() {
		// TODO Opisać wystrzeliwanie, opracować fizykę pocisku
		
	}
	public void go() {
		// TODO Auto-generated method stub

	}
	public void turnLeft() {
		

	}
	public void turnRight() {


	}

}
