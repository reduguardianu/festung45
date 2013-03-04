package openGL;

import net.java.games.jogl.GL;

public class Czolg extends Obiekt3D
{
	//SystemCzasteczkowy[] kurz;
	
	Czolg(int liczbaCzesci)
	{
		czesci = new Czesc[liczbaCzesci];
		pozycja = new Point3D();
		obrot = new Point3D();
		
		//kurz = new SystemCzasteczkowy[2];
		//kurz[0] = new SystemCzasteczkowy(main.Renderer.texKurz[1], 16, main.Renderer.frequency, new Point3D(pozycja.x + 1.0f, pozycja.y, pozycja.z), main.Renderer.frequency, main.Renderer.tick, new Point3D(0, 1.0f, 0), new Point3D(1.0f, 1.0f, 1.0f), 1f, true);
		//kurz[1] = new SystemCzasteczkowy(main.Renderer.texKurz[1], 16, main.Renderer.frequency, new Point3D(pozycja.x - 1.0f, pozycja.y, pozycja.z), main.Renderer.frequency, main.Renderer.tick, new Point3D(0, 1.0f, 0), new Point3D(1.0f, 1.0f, 1.0f), 1f, true);
	}
	
	public void obroc(String czesc, float X, float Y, float Z)
	{
		for(int i = 0; i < czesci.length; i++)
		{
			if(0 == czesci[i].nazwa.compareTo("\"" + czesc + "\""))
			{
				czesci[i].obrot.x = X;
				czesci[i].obrot.y = Y;
				czesci[i].obrot.z = Z;
			}
		}
	}
	
	public void Render(GL gl)
	{
		int tex0;
		int tex1;
		int tex2;
		Wierzcholek wierz0;
		Wierzcholek wierz1;
		Wierzcholek wierz2;
		int num;
		Czesc czesc = null;		
		gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[1]);
		gl.glPushMatrix();
		gl.glTranslatef(pozycja.x, pozycja.y, pozycja.z);
		for(int k = 0; k < czesci.length; k++)
		{
			for(int i = 0; i < czesci.length; i++)
			{
				if((k == 0) && (0 == czesci[i].nazwa.compareTo("\"korpus\"")))
					czesc = czesci[i];
				if((k == 1) && (0 == czesci[i].nazwa.compareTo("\"wieza\"")))
					czesc = czesci[i];
				if((k == 2) && (0 == czesci[i].nazwa.compareTo("\"lufa\"")))
					czesc = czesci[i];
			}
			num = czesc.liczbaScianek;
			
			gl.glPushMatrix();
			gl.glTranslatef(czesc.pivot.x, czesc.pivot.y, czesc.pivot.z);
			gl.glRotatef(czesc.obrot.x, 1.0f, 0.0f, 0.0f);
			gl.glRotatef(czesc.obrot.y, 0.0f, 1.0f, 0.0f);
			gl.glRotatef(czesc.obrot.z, 0.0f, 0.0f, 1.0f);
			gl.glTranslatef(-czesc.pivot.x, -czesc.pivot.y, -czesc.pivot.z);
			
			for(int i = 0; i < num; i++)
			{
				gl.glBegin(GL.GL_TRIANGLES);
				tex0 = czesc.scian[i].TexCoord[0];
				tex1 = czesc.scian[i].TexCoord[1];
				tex2 = czesc.scian[i].TexCoord[2];
				wierz0 = czesc.wierz[czesc.scian[i].wierz[0]];
				wierz1 = czesc.wierz[czesc.scian[i].wierz[1]];
				wierz2 = czesc.wierz[czesc.scian[i].wierz[2]];
				
				gl.glNormal3fv(czesc.scian[i].norm[0]);
				gl.glTexCoord2f(czesc.TexVert[tex0][0], czesc.TexVert[tex0][1]);
				gl.glVertex3f(wierz0.X, wierz0.Y, wierz0.Z);
				
				gl.glNormal3fv(czesc.scian[i].norm[1]);
				gl.glTexCoord2f(czesc.TexVert[tex1][0], czesc.TexVert[tex1][1]);
				gl.glVertex3f(wierz1.X, wierz1.Y, wierz1.Z);

				gl.glNormal3fv(czesc.scian[i].norm[2]);
				gl.glTexCoord2f(czesc.TexVert[tex2][0], czesc.TexVert[tex2][1]);
				gl.glVertex3f(wierz2.X, wierz2.Y, wierz2.Z);
				gl.glEnd();
			}
		}
		for(int i = 0; i < czesci.length - 1; i++)
			gl.glPopMatrix();
		
		//double dT = ((double)main.Renderer.tick - kurz[0].aktualnyCzas)/main.Renderer.frequency;
		//kurz[0].przemiesc(dT, pozycja.x + 1.0f, pozycja.y, pozycja.z);
		//kurz[1].przemiesc(dT, pozycja.x - 1.0f, pozycja.y, pozycja.z);
		//kurz[0].Render(gl, main.Renderer.kamera);
		//kurz[1].Render(gl, main.Renderer.kamera);
		
		gl.glPopMatrix();
		gl.glPopMatrix();
	}
}
