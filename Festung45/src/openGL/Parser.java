package openGL;


import java.io.*;
import java.util.StringTokenizer;
import net.java.games.jogl.GL;
import net.java.games.jogl.GLU;


public class Parser 
{	
	private final int OCZEKIWANIE_NA_OBIEKT = 0;
	private final int CZYTANIE_WIERZCHOLKOW = 1;
	private final int CZYTANIE_SCIANEK = 2;
	private final int CZYTANIE_TEKSTURY = 3;
	private final int CZYTANIE_NORMALNYCH = 4;
	private final int WYJSCIE = 5;
	private int stan = OCZEKIWANIE_NA_OBIEKT;
	int przebieg = -1;
	
	public void czytaj(Obiekt3D obj, String adres, GL gl, GLU glu)
	{
		FileReader fr = null;
		BufferedReader bufor = null;
		StringTokenizer strTok = null;
		String str;
		String pom = null;
		int numerCzesci = -1;
		float X;
		float Y;
		float Z;
		int numWierz = 0;
		int numScian = 0;
		int A = 0;
		int B = 0;
		int C = 0;
		int nrCzesci = -1;
		
		try 
		{
			fr = new FileReader(adres);
			bufor = new BufferedReader(fr);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			pom = "*NODE_NAME";
			for(;;)
			{
				if(stan == WYJSCIE) break;
				switch(stan)
				{
				case OCZEKIWANIE_NA_OBIEKT:
					try
					{
						strTok = new StringTokenizer(bufor.readLine());
					}
					catch(NullPointerException e)
					{
						stan = WYJSCIE;
						break;
					}
					while(strTok.hasMoreTokens())
					{
						str = strTok.nextToken();
						
						if(0 == str.compareTo("*BITMAP"))
						{
							str = strTok.nextToken();
							obj.tekstura = new int[2];
							
							Texture texture = null;
					    	try
					    	{
					    		texture = TextureReader.readTexture(str, false);
					    	}
					    	catch (IOException e)
					    	{
					    	}
					        
					        gl.glGenTextures(3, obj.tekstura);
					        gl.glBindTexture(GL.GL_TEXTURE_2D, obj.tekstura[0]);

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
					        
					        gl.glBindTexture(GL.GL_TEXTURE_2D, obj.tekstura[1]);
					        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
					        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);

					        glu.gluBuild2DMipmaps(GL.GL_TEXTURE_2D,
					        		GL.GL_RGB8,
					                texture.getWidth(),
					                texture.getHeight(),
					                GL.GL_RGB,
					                GL.GL_UNSIGNED_BYTE,
					                texture.getPixels());
						}
						
						if(0 == str.compareTo("*GEOMOBJECT"))
							nrCzesci++;
						if(0 == str.compareTo(pom))
						{
							przebieg++;
							obj.czesci[nrCzesci] = new Czesc(strTok.nextToken());
							numerCzesci++;
							pom = "?????";
						}
						
						if(0 == str.compareTo("*TM_POS"))
						{		
							X = Float.parseFloat(strTok.nextToken());
							Z = - Float.parseFloat(strTok.nextToken());
							Y = Float.parseFloat(strTok.nextToken());
							obj.czesci[nrCzesci].UstawPivot(X, Y, Z);
							pom = "*NODE_NAME";
						}
						
						if(0 == str.compareTo("*MESH"))
						{
							bufor.readLine();
							
							strTok = new StringTokenizer(bufor.readLine());
							strTok.nextToken();
							numWierz = Integer.parseInt(strTok.nextToken());
							
							strTok = new StringTokenizer(bufor.readLine());
							strTok.nextToken();
							numScian = Integer.parseInt(strTok.nextToken());
							
							obj.czesci[nrCzesci].Ustaw(numWierz, numScian);
							
							bufor.readLine();
							stan = CZYTANIE_WIERZCHOLKOW;
						}
					}
					break;
				case CZYTANIE_WIERZCHOLKOW:
					for(int i = 0; i < numWierz; i++)
					{
						strTok = new StringTokenizer(bufor.readLine());
						strTok.nextToken();
						strTok.nextToken();
						X = Float.parseFloat(strTok.nextToken());
						Z = -Float.parseFloat(strTok.nextToken());
						Y = Float.parseFloat(strTok.nextToken());
						obj.czesci[nrCzesci].wierz[i] = new Wierzcholek(X, Y, Z);
					}
					bufor.readLine();
					stan = CZYTANIE_SCIANEK;
					break;
				case CZYTANIE_SCIANEK:
					bufor.readLine();
					for(int i = 0; i < numScian; i++)
					{
						strTok = new StringTokenizer(bufor.readLine());
						strTok.nextToken();
						strTok.nextToken();
						strTok.nextToken();
						A = Integer.parseInt(strTok.nextToken());
						strTok.nextToken();
						B = Integer.parseInt(strTok.nextToken());
						strTok.nextToken();
						C = Integer.parseInt(strTok.nextToken());
						
						//obj.czesci.get(numerCzesci).scian[i] = new Scianka(A, B, C);
						obj.czesci[nrCzesci].scian[i] = new Scianka(A, B, C);
					}
					bufor.readLine();
					stan = CZYTANIE_TEKSTURY;
					break;
				case CZYTANIE_TEKSTURY:
					strTok = new StringTokenizer(bufor.readLine());
					strTok.nextToken();
					int numTexVert = Integer.parseInt(strTok.nextToken());
					obj.czesci[nrCzesci].inicjujTexVert(numTexVert);
					bufor.readLine();
					
					for(int i = 0; i < numTexVert; i++)
					{
						strTok = new StringTokenizer(bufor.readLine());
						strTok.nextToken();
						strTok.nextToken();
						obj.czesci[nrCzesci].TexVert[i][0] = Float.parseFloat(strTok.nextToken());
						obj.czesci[nrCzesci].TexVert[i][1] = Float.parseFloat(strTok.nextToken());
					}
					bufor.readLine();
					bufor.readLine();
					bufor.readLine();
					for(int i = 0; i < numScian; i++)
					{
						strTok = new StringTokenizer(bufor.readLine());
						strTok.nextToken();
						strTok.nextToken();
						A = Integer.parseInt(strTok.nextToken());
						B = Integer.parseInt(strTok.nextToken());
						C = Integer.parseInt(strTok.nextToken());
						obj.czesci[nrCzesci].scian[i].ustawTexCoord(A, B, C);
					}
					bufor.readLine();
					bufor.readLine();
					stan = CZYTANIE_NORMALNYCH;
					break;
				case CZYTANIE_NORMALNYCH:
					float[][] VertexNormal = new float[3][3];
					float[] FaceNormal = new float[3];
					
					for(int i = 0; i < numScian; i++)
					{
						strTok = new StringTokenizer(bufor.readLine());
						strTok.nextToken();
						strTok.nextToken();
						FaceNormal[0] = Float.parseFloat(strTok.nextToken());
						FaceNormal[1] = Float.parseFloat(strTok.nextToken());
						FaceNormal[2] = Float.parseFloat(strTok.nextToken());
						
						for(int j = 0; j < 3; j++)
						{
							strTok = new StringTokenizer(bufor.readLine());
							strTok.nextToken();
							strTok.nextToken();
							if(przebieg != 1)
							{
								VertexNormal[j][0] = Float.parseFloat(strTok.nextToken());
								VertexNormal[j][1] = Float.parseFloat(strTok.nextToken());
								VertexNormal[j][2] = Float.parseFloat(strTok.nextToken());
							}
							else
							{
								VertexNormal[j][0] = -Float.parseFloat(strTok.nextToken());
								VertexNormal[j][2] = -Float.parseFloat(strTok.nextToken());
								VertexNormal[j][1] = Float.parseFloat(strTok.nextToken());
							}
						}
						obj.czesci[nrCzesci].scian[i].ustawNormalne(
								VertexNormal[0][0], VertexNormal[0][1], VertexNormal[0][2], 
								VertexNormal[1][0], VertexNormal[1][1], VertexNormal[1][2],
								VertexNormal[2][0], VertexNormal[2][1], VertexNormal[2][2],
								FaceNormal[0], FaceNormal[1], FaceNormal[2]);
					}
					stan = OCZEKIWANIE_NA_OBIEKT;
					break;
				}
			}
		} 
		catch (IOException e) 
		{		
			System.out.println("WYLAZŁO");
		}
		stan = OCZEKIWANIE_NA_OBIEKT;
	}
	
	int sprawdzLiczbeCzesci(String adres)
	{
		FileReader fr = null;
		BufferedReader bufor = null;
		StringTokenizer strTok = null;
		String str;
		int liczbaCzesci = 0;
		
		try 
		{
			fr = new FileReader(adres);
			bufor = new BufferedReader(fr);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		try
		{
			while((str = bufor.readLine()) != null)
			{
				strTok = new StringTokenizer(str);
				for(int i=0; i<strTok.countTokens(); i++)
				{
					str = strTok.nextToken();
					if(0 == str.compareTo("*GEOMOBJECT"))
						liczbaCzesci++;
				}
			}
			fr = new FileReader(adres);
			bufor = new BufferedReader(fr);
		}
		catch(IOException e) 
		{
			
		}
		
		return liczbaCzesci;
	}
}
