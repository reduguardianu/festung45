package festungSiec;

/**
 * Klasa GraczSieciowy przechowuje informacje o nicku, numerze identyfikacyjnym (id), i sojuszu gracza.
 * @author Gemis
 */
public class GraczSieciowy
{
	public String nick;
	public int id;
	public int sojusz;
    public int fragi;
	static int ostatnieId = 1;
	
	public GraczSieciowy(String ni, int soj)
	{
		id = ostatnieId++;
		nick = ni; sojusz = soj;
        fragi = 0;
	}
}