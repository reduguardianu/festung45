package warGem;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class mouseHandle {
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();
	private Robot reseter;

	public mouseHandle() {
		try {
			reseter = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void resetMousePosition() {
		writeLock.lock();
		reseter.mouseMove(200, 200);
		writeLock.unlock();
	}

	public void mouseMoved(Point newLocation,warGemClient client) {
		readLock.lock();
		if (newLocation.getX() < 200) {
			client.increaseMainTankTorsion((short) 1); //zwiększ skręt lufy o 1 stopień
		} 
		if (newLocation.getX()>200){
			client.decreaseMainTankTorsion((short) 1);//zmmniejsz skręt lufy o 5 stopień
		}
		readLock.unlock();
		this.resetMousePosition();

	}

}
