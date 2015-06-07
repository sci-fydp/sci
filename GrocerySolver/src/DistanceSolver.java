import java.util.ArrayList;




public abstract class DistanceSolver
{

	public abstract SolutionRoute findBestRoute(ArrayList<Store> stores, double[][] distances, Location origin);
	
	protected ArrayList<Location> getStoreLocations(ArrayList<Store> stores)
	{
		ArrayList<Location> locations = new ArrayList<Location>();
		for (Store store : stores)
		{
			locations.add(store.getLocation());
		}
		return locations;
	}
}
