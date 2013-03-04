package openGL;

public class Interface 
{
	Point3D tankPosition;
	Point3D tankTurn[];
	Point3D camPosition;
	Point3D camTarget;
	int drops;
	long tick;
	long frequency;
	
	public Interface()
	{
		tankPosition = new Point3D();
		tankTurn = new Point3D[3];
		for(int i = 0; i < 3; i++)
			tankTurn[i] = new Point3D();
		camPosition = new Point3D();
		camTarget = new Point3D();
		drops = 0;
		tick = 0;
		frequency = 0;
	}
}