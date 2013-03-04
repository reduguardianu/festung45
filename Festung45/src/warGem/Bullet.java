package warGem;

public class Bullet {
	private Vector mV;
	private bulletType bT=bulletType.antiTank;
	
	public Bullet(Vector mV)//nie ma na razie innych typów pocisków niż przeciwczołgowe
	{
		this.mV=mV;
	}
	public void setMV(Vector mV) {
		this.mV = mV;
	}

	public Vector getMV() {
		return mV;
	}

	public void setBT(bulletType bT) {
		this.bT = bT;
	}

	public bulletType getBT() {
		return bT;
	}

}
