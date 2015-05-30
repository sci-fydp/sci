
public class Location
{
	double x;
	double y;

	public Location(double locx, double locy)
	{
		x = locx;
		y = locy;
	}

	public static double getDistance (Location loc1, Location loc2)
	{
		if (loc1 == loc2)
		{
			return 0;
		}
		double distance = (double)(Math.sqrt((loc1.x - loc2.x)*(loc1.x - loc2.x) + (loc1.y + loc2.y) * (loc1.y + loc2.y)));
		return distance;
	}


}
