package logics;

import java.util.ArrayList;
import java.util.Set;

import models.Address;
import models.StoreLocation;




public abstract class DistanceSolver
{

	public abstract SolutionRoute findBestRoute(Set<StoreLocation> storeLocations, Address origin);
	/*
	protected ArrayList<Location> getStoreLocations(ArrayList<StoreLocation> stores)
	{
		ArrayList<Location> locations = new ArrayList<Location>();
		for (Store store : stores)
		{
			locations.add(store.getLocation());
		}
		return locations;
	}
	*/
}
