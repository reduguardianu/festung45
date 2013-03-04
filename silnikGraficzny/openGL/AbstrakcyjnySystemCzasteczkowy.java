package openGL;

import java.util.Random;

public abstract class AbstrakcyjnySystemCzasteczkowy 
{
	protected int MAX_CZASTECZEK;
	protected int aktualnieCzasteczek;
	protected Random generator;
	
	AbstrakcyjnySystemCzasteczkowy()
	{
		MAX_CZASTECZEK = 0;
		aktualnieCzasteczek = 0;
		generator = new Random();
	}
}
