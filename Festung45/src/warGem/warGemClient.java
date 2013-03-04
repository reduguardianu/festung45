/**
 * 
 */
package warGem;

import java.lang.Math;
import java.net.InetSocketAddress;
import java.util.Vector;
//import openGL.*;
/**
 * @author Marek Ozimina
 *
 */
public class warGemClient {
	private int	mainTankID=0;
	private mapStorage map=null;
	private double accelerationTime=0;
	private PlayersUnit unit;
	private java.util.Vector<Bullet> bullets;
	public warGemClient()		//W przyszłości można stworzyć konstruktor warGemClient(PlayersUnitType type),
								//ale w chwili obecnej jest to zbędne
	{
		
		unit=new Tank(Alliances.wermacht,0,0,0,0,0,0,0);//na razie sztywno Tiger II
		map=mapStorage.getInstance();
		mainTankID=map.addUnit(unit);
		bullets=new Vector<Bullet>();
	}
	public void updateMap()
	{
		if(!bullets.isEmpty())
		{
			for (Bullet newBullet : bullets) {
				map.addBullet(newBullet);				
			}
			bullets.clear();
		}
		map.updateUnit(0, unit);
	}
	/**public void increaseMainTankTorsion(short torsion)
	 * Obraca wieżyczkę o torsion stopni w lewo
	 */
	public void increaseMainTankTorsion(short torsion)
	{
		((Tank) unit).setCannonTorsion((((Tank) unit).getCannonTorsion()+torsion)%360);
		updateMap();	
	}
	/**public void decreaseMainTankTorsion(short torsion)
	 * Obraca wieżyczkę czołgu o torsion stopni w prawo
	 */
	public void decreaseMainTankTorsion(short torsion)
	{
		int newTorsion=(((Tank) unit).getCannonTorsion()-torsion);
		if(newTorsion<0)
			newTorsion=360+newTorsion;
		((Tank) unit).setCannonTorsion(newTorsion%360);
		updateMap();
	}
	/**public void throttle()
	 * Obsługuje wciśnięcie przez użytkownika przycisku "gazu"
	 */
	public void up()
	{
			((Tank)unit).speedUp();
			/*double currentSpeed=unit.getMoveVector().length();
			if(currentSpeed==0)
				accelerationTime=0;
			else
				accelerationTime=(float) Math.pow(currentSpeed, 4.0f/3.0f);
			
			accelerationTime=accelerationTime+(double)(map.getNewTick()-map.getOldTick())/(double)map.getFreq();
			System.out.println("Nt-Ot"+" "+(map.getNewTick()-map.getOldTick()));
			System.out.println("Freq "+map.getFreq());
			System.out.println("AT: "+accelerationTime);
			unit.setMoveVectorLength(Math.pow(accelerationTime,3.0f/4.0f)/1000);*/
		
		/*else
		{
			if(accelerationTime>0){
				double currentSpeed=unit.getMoveVector().length();
				accelerationTime=(float) Math.pow(currentSpeed, 4/3);
				accelerationTime-=(map.getNewTick()-map.getOldTick())/(2*(double)map.getFreq());
				//System.out.println(accelerationTime);
			}
			else accelerationTime=0;
			unit.setMoveVectorLength(Math.pow(accelerationTime,3/4)/1000);
		}*/
	}
	public void down()
	{
			((Tank)unit).speedDown();

	}
	public void left()
	{
		((Tank)unit).turnLeft();
	}
	public void right()
	{
		((Tank)unit).turnRight();
	}
}
