package openGL;

import net.java.games.jogl.GL;

public class Deszcz extends AbstrakcyjnySystemCzasteczkowy
{
	private float dlugoscKropli;
	private Kropla[] krople;
	//Predkosc kropli 9,5m/s
	
	Deszcz(int maxKropli)
	{
		MAX_CZASTECZEK = maxKropli;
		aktualnieCzasteczek = maxKropli;
		dlugoscKropli = 0.2f;
		krople = new Kropla[maxKropli];
		for(int i = 0; i < MAX_CZASTECZEK; i++)
		{
			krople[i] = new Kropla(generator.nextFloat()*5, generator.nextFloat()*5, generator.nextFloat()*5);
		}
	}
	
	public void przemiesc(double dT, Point3D pos)
	{
		float deltaY = (float)(dT * (double)9.5);
		
		for(int i = 0; i < aktualnieCzasteczek; i++)
		{
			krople[i].pozycja.y -= deltaY;
			
			if(krople[i].pozycja.y < pos.y-1.5f) 
			{
				krople[i].pozycja.y = pos.y+1.0f - ((pos.y-1.5f - krople[i].pozycja.y)%5);
				krople[i].pozycja.x = (generator.nextFloat() * 5)+krople[i].pozycja.x-4.5f;
				krople[i].pozycja.z = (generator.nextFloat() * 5)+krople[i].pozycja.z-4.5f;
			}
			if(krople[i].pozycja.x < pos.x-2.5f) krople[i].pozycja.x = pos.x+2.5f - ((pos.x-2.5f - krople[i].pozycja.x)%5);
			if(krople[i].pozycja.x > pos.x+2.5f) krople[i].pozycja.x = pos.x-2.5f + ((krople[i].pozycja.x-pos.x-2.5f)%5);
			if(krople[i].pozycja.z < pos.z-2.5f) krople[i].pozycja.z = pos.z+2.5f - ((pos.z-2.5f - krople[i].pozycja.z)%5);
			if(krople[i].pozycja.z > pos.z+2.5f) krople[i].pozycja.z = pos.z-2.5f + ((krople[i].pozycja.z-pos.z-2.5f)%5);
		}
	}
	
	public void Render(GL gl)
	{
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glShadeModel(GL.GL_FLAT);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE);
		gl.glClearColor(0.78431f - ((float)aktualnieCzasteczek / MAX_CZASTECZEK)/3.517f, 
				0.8863f - ((float)aktualnieCzasteczek / MAX_CZASTECZEK)/2.5887f, 
				0.9686f - ((float)aktualnieCzasteczek / MAX_CZASTECZEK)/2.134f, 1.0f);
		gl.glFogf(GL.GL_FOG_DENSITY, 0.001f + ((float)aktualnieCzasteczek / MAX_CZASTECZEK)/100);

		gl.glBegin(GL.GL_LINES);
		gl.glColor4f(0.7f, 0.7f, 0.7f, 0.25f); // kolor deszczu ustawiony na stale
		
		for(int i=0; i<aktualnieCzasteczek; i++)
		{
			gl.glVertex3f(krople[i].pozycja.x, krople[i].pozycja.y, krople[i].pozycja.z);
			gl.glVertex3f(krople[i].pozycja.x, krople[i].pozycja.y + dlugoscKropli, krople[i].pozycja.z);
		}

		gl.glEnd();

		gl.glDisable(GL.GL_BLEND);	
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glShadeModel(GL.GL_SMOOTH);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
