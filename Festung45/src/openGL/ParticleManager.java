package openGL;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import net.java.games.jogl.GL;

public class ParticleManager
{
	private ArrayList<SystemCzasteczkowy> systemy;
	static Point3D camPos;
	int currentId;
	
	ParticleManager()
	{
		currentId = -1;
		systemy = new ArrayList<SystemCzasteczkowy>();
	}
	
	int add(int bitmapa, int ilCzast, 
			long dlugoscZycia, Point3D poz, long czestotliwosc, 
			long start, Point3D v, Point3D rozrzut, float rozmiarCzasteczki,
			boolean czySwieci)
	{
		systemy.add(new SystemCzasteczkowy(bitmapa, ilCzast, 
			dlugoscZycia, poz, czestotliwosc, start, v, 
			rozrzut, rozmiarCzasteczki, czySwieci));
		currentId++;
		return currentId;
	}
	
	void render(GL gl, Point3D camPos)
	{		
		ParticleManager.camPos = camPos;
		ArrayList<SystemCzasteczkowy> kopia = (ArrayList<SystemCzasteczkowy>)systemy.clone();
		Collections.sort(kopia, new comparator());
		
		for(int i = 0; i < kopia.size(); i++)
		{
			kopia.get(i).Render(gl, camPos);
		}
	}
	
	void move(int ID, double dT, Point3D pos)
	{
		systemy.get(ID).przemiesc(dT, pos.x, pos.y, pos.z);
	}
	
	int particleSystemsCount()
	{
		return systemy.size();
	}
}

class comparator implements Comparator<SystemCzasteczkowy>
{
	double d1;
	double d2;
	
	public int compare(SystemCzasteczkowy s1, SystemCzasteczkowy s2)
	{
		d1 = Math.sqrt(Math.pow(s1.pozycja.x - ParticleManager.camPos.x, 2) + 
				Math.pow(s1.pozycja.y - ParticleManager.camPos.y, 2) + 
				Math.pow(s1.pozycja.z - ParticleManager.camPos.z, 2));

		d2 = Math.sqrt(Math.pow(s2.pozycja.x - ParticleManager.camPos.x, 2) + 
				Math.pow(s2.pozycja.y - ParticleManager.camPos.y, 2) + 
				Math.pow(s2.pozycja.z - ParticleManager.camPos.z, 2));
		
		if(d1 > d2)
			return -1;
		else
			if(d2 > d1)
				return 1;
			else
				return 0;
	}
}