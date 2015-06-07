import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class GreedyDistanceSolver extends DistanceSolver
{

	@Override
	public SolutionRoute findBestRoute(ArrayList<Store> stores, double[][] distances, Location origin)
	{
			ArrayList<Location> storeLocations = getStoreLocations(stores);
			Location currentLoc = origin;
			int currentLocIndex = storeLocations.size();
			ArrayList<Location> route = new ArrayList<Location>();
			ArrayList<Store> orderedStores = new ArrayList<Store>();
			Set<Integer> takenLocations = new HashSet<Integer>();
			takenLocations.add(currentLocIndex);
			
			//??? 
			
			double bestDistance;
			Location bestLocation = null;
			int bestIndex;
			
			double totalDistance = 0;
			for (int store = 0; store < storeLocations.size(); store++)
			{
				bestIndex = 0;
				bestDistance = 9999999;
				bestLocation = null;
				
				for (int storeIndex = 0; storeIndex < storeLocations.size(); storeIndex++)
				{
					if (!takenLocations.contains(storeIndex))
					{
						double distance = distances[currentLocIndex][storeIndex];
						if (distance < bestDistance)
						{
							bestIndex = storeIndex;
							bestLocation = storeLocations.get(storeIndex);
							bestDistance = distance;
						}
					}
				}
				
				currentLoc = bestLocation;
				currentLocIndex = bestIndex;
				totalDistance += bestDistance;
				route.add(currentLoc);
				orderedStores.add(stores.get(bestIndex));
				takenLocations.add(currentLocIndex);
			}
			
			if (bestLocation != null)
			{
				totalDistance += Location.getDistance(origin, bestLocation);
			}
			route.add(origin);
			//return route;
			
			
			SolutionRoute soln = new SolutionRoute();
			soln.stores = orderedStores;
			soln.locations = route;
			soln.distance = totalDistance;
			
			return soln;
		
	}


}
