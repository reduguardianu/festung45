package openGL;

import net.java.games.jogl.GL;


public class SystemCzasteczkowy extends AbstrakcyjnySystemCzasteczkowy
{
	int[] tekstura;
	Czasteczka czasteczki[];
	long czestotliwosc;
	long start;
	long dlugoscZycia;
	long aktualnyCzas;
	Point3D pozycja;
	private Point3D predkosc;
	private Point3D rozrzut;
	double dT;
	private float tmp;
	float pom;
	float rozmiarCzasteczki;
	float[] emission = {1.0f, 1.0f, 1.0f, 0.0f};
	float[] no_mat = {0.0f, 0.0f, 0.0f, 1.0f};
	boolean czySwieci;
	
	Point3D pr = new Point3D();
	Point3D rozLos = new Point3D();
	Point3D wek1;
	Point3D wek2;
	Point3D wek3;
	
	public SystemCzasteczkowy(int bitmapa, int ilCzast, 
			long dlugoscZycia, Point3D poz, long czestotliwosc, 
			long start, Point3D v, Point3D rozrzut, float rozmiarCzasteczki,
			boolean czySwieci)
	{
		wek1 = new Point3D();
		wek2 = new Point3D();
		wek3 = new Point3D();
		
		tekstura = new int[2];
		tekstura[0] = bitmapa;
		tekstura[1] = bitmapa;
		this.rozmiarCzasteczki = rozmiarCzasteczki;
		MAX_CZASTECZEK = ilCzast;
		aktualnieCzasteczek = 0;
		this.dlugoscZycia = dlugoscZycia;
		pozycja = poz;
		this.czestotliwosc = czestotliwosc;
		this.start = start;
		predkosc = v;
		this.rozrzut = rozrzut;
		this.czySwieci = czySwieci;
		
		dT = (double)this.dlugoscZycia / MAX_CZASTECZEK;
		
		czasteczki = new Czasteczka[MAX_CZASTECZEK];

		for(int i = 0; i < MAX_CZASTECZEK; i++)
		{
			//x' = xcosφ − ysinφ
			//y' = xsinφ + ycosφ
			pr.x = predkosc.x;
			pr.y = predkosc.y;
			pr.z = predkosc.z;

			rozLos.x = generator.nextFloat() * rozrzut.x - rozrzut.x/2;
			rozLos.y = generator.nextFloat() * rozrzut.y - rozrzut.y/2;
			rozLos.z = generator.nextFloat() * rozrzut.z - rozrzut.z/2;
			
			//OŚ X
			tmp = pr.y;
			pr.y = (float)(tmp * Math.cos(rozLos.x) - pr.z * Math.sin(rozLos.x));
			pr.z = (float)(tmp * Math.sin(rozLos.x) + pr.z * Math.cos(rozLos.x));
			
			//OŚ Y			  
			tmp = pr.z;
			pr.z = (float)(tmp * Math.cos(rozLos.y) - pr.x * Math.sin(rozLos.y));
			pr.x = (float)(tmp * Math.sin(rozLos.y) + pr.x * Math.cos(rozLos.y));
			
			//OŚ Z
			tmp = pr.x;
			pr.x = (float)(tmp * Math.cos(rozLos.z) - pr.y * Math.sin(rozLos.z));
			pr.y = (float)(tmp * Math.sin(rozLos.z) + pr.y * Math.cos(rozLos.z));
			
			czasteczki[i] = new Czasteczka(pozycja.x, pozycja.y, pozycja.z,
					pr.x, pr.y, pr.z, start);
		}
	}
	
	public void przemiesc(double dT, float X, float Y, float Z)
	{
		aktualnyCzas = Renderer.localInterface.getTick();
		if((double)(aktualnyCzas - start) / this.dT > aktualnieCzasteczek)
		{
			int pom = aktualnieCzasteczek;
			aktualnieCzasteczek = (int)Math.round((double)(aktualnyCzas - start) / this.dT);
			if(aktualnieCzasteczek > MAX_CZASTECZEK)
				aktualnieCzasteczek = MAX_CZASTECZEK;
			
			for(int i = pom; i < aktualnieCzasteczek; i++)
			{
				czasteczki[i].pozycja.x = pozycja.x;
				czasteczki[i].pozycja.y = pozycja.y;
				czasteczki[i].pozycja.z = pozycja.z;
				czasteczki[i].poczatekZycia = aktualnyCzas;
			}
		}
		for(int i = 0; i < aktualnieCzasteczek; i++)
		{
			if(czasteczki[i].poczatekZycia + dlugoscZycia > aktualnyCzas)
			{
				czasteczki[i].pozycja.x += czasteczki[i].predkosc.x * dT;
				czasteczki[i].pozycja.y += czasteczki[i].predkosc.y * dT;
				czasteczki[i].pozycja.z += czasteczki[i].predkosc.z * dT;
			}
			else
			{
				pr.x = predkosc.x;
				pr.y = predkosc.y;
				pr.z = predkosc.z;

				rozLos.x = generator.nextFloat() * rozrzut.x - rozrzut.x/2;
				rozLos.y = generator.nextFloat() * rozrzut.y - rozrzut.y/2;
				rozLos.z = generator.nextFloat() * rozrzut.z - rozrzut.z/2;
				
				//OŚ X
				tmp = pr.y;
				pr.y = (float)(tmp * Math.cos(rozLos.x) - pr.z * Math.sin(rozLos.x));
				pr.z = (float)(tmp * Math.sin(rozLos.x) + pr.z * Math.cos(rozLos.x));
				
				//OŚ Y
				tmp = pr.z;
				pr.z = (float)(tmp * Math.cos(rozLos.y) - pr.x * Math.sin(rozLos.y));
				pr.x = (float)(tmp * Math.sin(rozLos.y) + pr.x * Math.cos(rozLos.y));
				
				//OŚ Z
				tmp = pr.x;
				pr.x = (float)(tmp * Math.cos(rozLos.z) - pr.y * Math.sin(rozLos.z));
				pr.y = (float)(tmp * Math.sin(rozLos.z) + pr.y * Math.cos(rozLos.z));
				
				czasteczki[i].pozycja.x = X; 
				czasteczki[i].pozycja.y = Y;
				czasteczki[i].pozycja.z = Z;
				czasteczki[i].predkosc.x = pr.x; 
				czasteczki[i].predkosc.y = pr.y; 
				czasteczki[i].predkosc.z = pr.z; 
				czasteczki[i].poczatekZycia = aktualnyCzas;
			}
		}
	}
	
	void Render(GL gl, Point3D pozycjaKam)
	{	
		if(czySwieci)
			gl.glMaterialfv(GL.GL_FRONT,GL.GL_EMISSION, emission);
		
		if(aktualnieCzasteczek > 2)
			quicksortJava(czasteczki, 0, aktualnieCzasteczek-1, pozycjaKam);
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[1]);
		gl.glBegin(GL.GL_QUADS);
		for(int i=aktualnieCzasteczek-1; i>=0; i--)
		{
			gl.glColor4f(1, 1, 1, (float)Math.sin(Math.pow(1-((double)aktualnyCzas - czasteczki[i].poczatekZycia)/dlugoscZycia, 2.0f)));
			wek1.x = czasteczki[i].pozycja.x - pozycjaKam.x;
			wek1.y = czasteczki[i].pozycja.y - pozycjaKam.y;
			wek1.z = czasteczki[i].pozycja.z - pozycjaKam.z;
			pom = (float)Math.sqrt(wek1.x * wek1.x + wek1.y * wek1.y + wek1.z * wek1.z);
			wek1.x /= pom / rozmiarCzasteczki;
			wek1.y /= pom / rozmiarCzasteczki;
			wek1.z /= pom / rozmiarCzasteczki;
			wek2.x = -wek1.z;
			wek2.y = 0.0f;
			wek2.z = wek1.x;
			wek3.x = (wek1.y * wek2.z - wek2.y * wek1.x) / rozmiarCzasteczki;
			wek3.y = (wek1.z * wek2.x - wek2.z * wek1.x) / rozmiarCzasteczki;
			wek3.z = (wek1.x * wek2.y - wek2.x * wek1.y) / rozmiarCzasteczki;

			gl.glTexCoord2f(1.0f, 0.0f);
		  	gl.glVertex3f(czasteczki[i].pozycja.x - wek2.x + wek3.x, 
		  			czasteczki[i].pozycja.y - wek2.y + wek3.y, 
		  			czasteczki[i].pozycja.z - wek2.z + wek3.z);

			gl.glTexCoord2f(0.0f, 0.0f);
		  	gl.glVertex3f(czasteczki[i].pozycja.x + wek2.x + wek3.x, 
		  			czasteczki[i].pozycja.y + wek2.y + wek3.y, 
		  			czasteczki[i].pozycja.z + wek2.z + wek3.z);
			
			gl.glTexCoord2f(0.0f, 1.0f);
		  	gl.glVertex3f(czasteczki[i].pozycja.x + wek2.x - wek3.x, 
		  			czasteczki[i].pozycja.y + wek2.y - wek3.y, 
		  			czasteczki[i].pozycja.z + wek2.z - wek3.z);
		  	
		  	gl.glTexCoord2f(1.0f, 1.0f);
			gl.glVertex3f(czasteczki[i].pozycja.x - wek2.x - wek3.x, 
					czasteczki[i].pozycja.y - wek2.y - wek3.y, 
					czasteczki[i].pozycja.z - wek2.z - wek3.z);
		}

		gl.glEnd();
		gl.glDisable(GL.GL_BLEND);
		gl.glMaterialfv(GL.GL_FRONT,GL.GL_EMISSION, no_mat);
	}
	
	private void quicksortJava(Czasteczka[] a, int lo, int hi, Point3D pozKam)
	{
		//  lo dolny indeks
		//  hi gorny indeks
	    int i=lo, j=hi; 
	    Czasteczka h;
	    double x = Math.pow(Math.pow(a[(lo+hi)/2].pozycja.x - pozKam.x, 2)
	    + Math.pow(a[(lo+hi)/2].pozycja.y - pozKam.y, 2) + Math.pow(a[(lo+hi)/2].pozycja.z - pozKam.z, 2), 0.5);
    
	    do
	    {
	        while(Math.pow(Math.pow(a[i].pozycja.x - pozKam.x, 2) + 
	        		Math.pow(a[i].pozycja.y - pozKam.y, 2) + 
	        		Math.pow(a[i].pozycja.z - pozKam.z, 2), 0.5) < x)
	        	i++;
	        while(x < Math.pow(Math.pow(a[j].pozycja.x - pozKam.x, 2) + 
	        		Math.pow(a[j].pozycja.y - pozKam.y, 2) + 
	        		Math.pow(a[j].pozycja.z - pozKam.z, 2), 0.5))
	        	j--;
	        
	        if(i <= j)
	        {
	        	h=a[i];
	        	a[i]=a[j];
	        	a[j]=h;
	        	i++;
	        	j--;
	        }
	    }while(i<=j);
	    //  rekursja
	    if (lo<j) 
	    	quicksortJava(a, lo, j, pozKam);
	    if (i<hi) 
	    	quicksortJava(a, i, hi, pozKam);
	}
}
