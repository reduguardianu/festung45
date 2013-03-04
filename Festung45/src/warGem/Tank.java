package warGem;

import java.lang.Math;

public class Tank extends PlayersUnit {
	private int leftCaterpillar;
	private int driveAngle = 0;
	private int rightCaterpillar;
	private double speed = 0;
	private double accTime = 0;
	private int weight;
	private int firePower;
	private int width;
	private int length;
	private int height;
	private int ammo;
	private int cannonTorsion = 0;
	private int cannonAngle = 0;
	private double maxSpeed;
	private int maxPitch;
	private int health;
	private mapStorage map = mapStorage.getInstance();

	public Tank(Alliances ally,float x,float y,float z,float dx,float dy,float dz,int torsion) {
		this.setAlly(ally);
		this.setMoveVector(x, y, z, dx, dy, dz);
		this.setCameraVector(x, y-12.0f, z+6.0f, 0.0f, 1.0f, 0.0f);
		this.cannonTorsion=torsion;
		updateCameraVector();
		this.create();
		this.setType(PlayersUnitType.Tank);
		switch (this.getAlly()) {
		case redArmy: {
			// Dane Kaczuchy
			maxSpeed = 0.75;
		}
		case wermacht: {
			// Opisy Tygrysa
			maxSpeed = 0.75;
		}

		}

	}

	public void fire() {
		// TODO Opisać wystrzeliwanie, opracować fizykę pocisku

	}

	public void speedUp() {
		// TODO Auto-generated method stub
		if (speed < maxSpeed) {
			accTime += 0.005;
			updateSpeed();
		}
	}

	public void speedDown() {
		if (accTime > 0.01) {
			accTime -= 0.01;
			updateSpeed();
		} else {
			accTime = 0;
			speed = 0;
		}
	}

	public void updateSpeed() {
		speed = Math.pow(accTime, 3.0f / 4.0f);
	}

	public void turnLeft() {
		driveAngle += 1;

		this.setMoveVector(this.getMoveVector().getVectorHandle().getX(), this
				.getMoveVector().getVectorHandle().getY(), this.getMoveVector()
				.getVectorHandle().getZ(), (float) java.lang.Math
				.sin(driveAngle * (java.lang.Math.PI / 180)),
				(float) java.lang.Math.cos(driveAngle
						* (java.lang.Math.PI / 180)), 0);
		updateCameraVector();
	}

	public void turnRight() {
		driveAngle -= 1;

		this.setMoveVector(this.getMoveVector().getVectorHandle().getX(), this
				.getMoveVector().getVectorHandle().getY(), this.getMoveVector()
				.getVectorHandle().getZ(), (float) java.lang.Math
				.sin(driveAngle * (java.lang.Math.PI / 180)),
				(float) java.lang.Math.cos(driveAngle
						* (java.lang.Math.PI / 180)), 0);
		updateCameraVector();

	}

	public void setLeftCaterpillar(int leftCaterpillar) {
		this.leftCaterpillar = leftCaterpillar;
	}

	public int getLeftCaterpillar() {
		return leftCaterpillar;
	}

	public void setRightCaterpillar(int rightCaterpillar) {
		this.rightCaterpillar = rightCaterpillar;
	}

	public int getRightCaterpillar() {
		return rightCaterpillar;
	}
	public void updateCameraVector()
	{
		float dx, dy;
		dx = (float) java.lang.Math.sin((this.driveAngle+this.cannonTorsion)
				* (java.lang.Math.PI / 180));
		dy = (float) java.lang.Math.cos((this.driveAngle+this.cannonTorsion)
				* (java.lang.Math.PI / 180));
		this.setCameraVector(-dx * 12, -dy * 12, 6.0f, dx, dy, 0);
	}
	public void setCannonTorsion(int cannonTorsion) {
		this.cannonTorsion = cannonTorsion;
		updateCameraVector();
	}

	public int getCannonTorsion() {
		return cannonTorsion;
	}

	public void positionUpdate() {
		this.getMoveVector().getVectorHandle().changeCoordinates(
				this.getMoveVector().getVectorHandle().getX()
						+ this.getMoveVector().dx * ((float) speed / 15000),
				this.getMoveVector().getVectorHandle().getY()
						+ this.getMoveVector().dy * ((float) speed / 15000),
				this.getMoveVector().getVectorHandle().getZ());
		map.updateUnit(0, this);
	}
	public int getDriveAngle()
	{
		return driveAngle;
	}

}
