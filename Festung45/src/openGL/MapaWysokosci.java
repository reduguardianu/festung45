package openGL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLU;

public class MapaWysokosci extends Obiekt{
	public int[][] w;
	//public int[][] n;
	public float skalaXY;
	public float skalaWys;
	static float normalne[][][];
	
	public MapaWysokosci(GL gl, GLU glu, String adresTekstury, String adresMapy, float rozmiar, float maxWys)
	{
		w = null;
		//n = null;
		skalaXY = rozmiar;
		skalaWys = (1.0f / 256.0f) * maxWys;
		
		tekstura = new int[2];
		
		Texture texture = null;
    	try
    	{
    		texture = TextureReader.readTexture(adresTekstury, false);
    	}
    	catch (IOException e)
    	{
    	}
        
        gl.glGenTextures(3, tekstura);
        gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[0]);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);

        gl.glTexImage2D(GL.GL_TEXTURE_2D,
                0,
                3,
                texture.getWidth(),
                texture.getHeight(),
                0,
                GL.GL_RGB,
                GL.GL_UNSIGNED_BYTE,
                texture.getPixels());
        
        gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[1]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);

        glu.gluBuild2DMipmaps(GL.GL_TEXTURE_2D,
        		GL.GL_RGB8,
                texture.getWidth(),
                texture.getHeight(),
                GL.GL_RGB,
                GL.GL_UNSIGNED_BYTE,
                texture.getPixels());
        
        czytajMapeWysokosci(adresMapy);
	}
	
	public void Render(GL gl)
	{
		gl.glBindTexture(GL.GL_TEXTURE_2D, tekstura[1]);
		final int krok = 2;
		for(int j = 0; j < w[0].length-krok; j+=krok)//po wysokosci
		{
			gl.glBegin(GL.GL_TRIANGLE_STRIP);
			for(int i = 0; i < w.length-krok*2; i+=krok*2)//po szerokosci
			{
				gl.glNormal3fv(normalne[i][j]);
				gl.glTexCoord2f(0, 0);
				gl.glVertex3f((i - w.length / 2)*skalaXY, w[i][j]*skalaWys, (j - w.length / 2)*skalaXY);
						  
				gl.glNormal3fv(normalne[i][j+krok]);
				gl.glTexCoord2f(0, 1);
				gl.glVertex3f((i - w.length / 2)*skalaXY, w[i][j+krok]*skalaWys, (j+krok - w.length / 2)*skalaXY);
						  
				gl.glNormal3fv(normalne[i+krok][j]);
				gl.glTexCoord2f(1, 0);
				gl.glVertex3f((i+krok - w.length / 2)*skalaXY, w[i+krok][j]*skalaWys, (j - w.length / 2)*skalaXY);
						  
				gl.glNormal3fv(normalne[i+krok][j+krok]);
				gl.glTexCoord2f(1, 1);
				gl.glVertex3f((i+krok - w.length / 2)*skalaXY, w[i+krok][j+krok]*skalaWys, (j+krok - w.length / 2)*skalaXY);
						  
			}
			gl.glEnd();
		}
	}
	
	public void czytajMapeWysokosci(String adres)
    {
    	float v[] = new float[3], w[] = new float[3], n[] = new float[3], d;
        InputStream file = null;
        int szer = 0, wys = 0;
        try 
        {
			file = new FileInputStream(adres);
		
			for(int i = 0; i < 18; i++)
			{
				file.read();
			}
			szer = file.read() + 256*file.read() + 6656*file.read() + 16777216*file.read();
			wys = file.read() + 256*file.read() + 6656*file.read() + 16777216*file.read();
			for(int i = 0; i < 28; i++)
			{
				file.read();
			}
        } 
        catch (FileNotFoundException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
        
        this.skalaXY /= szer;
        this.w = new int[szer][wys];
		normalne = new float[256][256][3];

		try 
		{
        for(int i = 0; i < szer; i++)
        {
        	for(int j = 0; j< wys; j++)
        	{
        		this.w[i][j] = file.read();
        		file.read();
        		file.read();
        	}
        }
    	}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < szer-1; i++)
		{
			for(int j = 0; j < wys-1; j++)
			{
        		v[0] = skalaXY;
			    v[1] = this.w[j+1][i]-this.w[j][i];
				v[2] = 0;
				w[0] = 0;
				w[1] = this.w[j][i+1]-this.w[j][i];
				w[2] = skalaXY;
				
				n[0] = v[1]*w[2]-v[2]*w[2];
				n[1] = v[2]*w[0]-v[0]*w[2];
				n[2] = v[1]*w[0]-v[0]*w[1];
				
				d = (float) Math.sqrt(n[0]*n[0]+n[1]*n[1]+n[2]*n[2]);
				
				normalne[j][i][0] = n[0]/d;
				normalne[j][i][1] = n[1]/d;
				normalne[j][i][2] = n[2]/d;
			}
    	}
	}
}
