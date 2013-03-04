package openGL;

public class Scianka
{
	public int[] wierz;
	public float[][] norm;
	public float[] normScianki;
	public int[] TexCoord;
	
	Scianka(int a, int b, int c)
	{
		wierz = new int[3];
		wierz[0] = a;
		wierz[1] = b;
		wierz[2] = c;
	}
	
	public void ustawTexCoord(int a, int b, int c)
	{
		TexCoord = new int[3];
		TexCoord[0] = a;
		TexCoord[1] = b;
		TexCoord[2] = c;
	}
	
	public void ustawNormalne(float n0A, float n1A, float n2A, 
			float n0B, float n1B, float n2B, 
			float n0C, float n1C, float n2C, 
			float n0Scianki, float n1Scianki, float n2Scianki)
	{
		norm = new float[3][3];
		norm[0][0] = n0A;
		norm[0][1] = n1A;
		norm[0][2] = n2A;
		norm[1][0] = n0B;
		norm[1][1] = n1B;
		norm[1][2] = n2B;
		norm[2][0] = n0C;
		norm[2][1] = n1C;
		norm[2][2] = n2C;
		normScianki = new float[3];
		normScianki[0] = n0Scianki;
		normScianki[1] = n1Scianki;
		normScianki[2] = n2Scianki;
	}
}