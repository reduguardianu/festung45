package openGL;

import net.java.games.jogl.GL;

public class Obiekt3D extends Obiekt
{
	Point3D pozycja;
	Point3D obrot;
	Czesc[] czesci;
	
	Obiekt3D()
	{
		czesci = new Czesc[0];
		pozycja = new Point3D();
		obrot = new Point3D();
	}
	
	Obiekt3D(int iloscCzesci)
	{
		czesci = new Czesc[iloscCzesci];
		pozycja = new Point3D();
		obrot = new Point3D();
	}
	
	public void przesun(float X, float Y, float Z)
	{
		pozycja.x = X;
		pozycja.y = Y;
		pozycja.z = Z;
	}
	
	public void obroc(float X, float Y, float Z)
	{
		for(int i = 0; i < czesci.length; i++)
		{
			czesci[i].obrot.x = X;
			czesci[i].obrot.y = Y;
			czesci[i].obrot.z = Z;
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
		if(tekstura != null)
			gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[1]);
		else
		{
			gl.glColor3f(1.0f, 1.0f, 1.0f);
			gl.glDisable(GL.GL_TEXTURE_2D);
		}
		
		gl.glPushMatrix();
		gl.glTranslatef(pozycja.x, pozycja.y, pozycja.z);
		for(int k = 0; k < czesci.length; k++)
		{
			czesc = czesci[k];
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
		for(int i = 0; i < czesci.length; i++)
			gl.glPopMatrix();
		gl.glPopMatrix();
		if(tekstura == null)
			gl.glEnable(GL.GL_TEXTURE_2D);
	}
}
