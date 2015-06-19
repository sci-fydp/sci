package logics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.Address;
import models.Country;
import models.StoreLocation;
import models.StoreLocationDistance;

import models.StoreLocation;
import models.Address;

//You are insane if you use this with many stores.
public class BruteForceDistanceSolver extends DistanceSolver
{

	@Override
	public SolutionRoute findBestRoute(Set<StoreLocation> storeLocations, Address origin)
	{
			ArrayList<StoreLocation> storeLocs = new ArrayList<StoreLocation>(storeLocations);
			ArrayList<ArrayList<StoreLocation>> allStorePerms = getAllPermutations(storeLocs);
			//return route;
			double bestDistance = 999999;
			ArrayList<StoreLocation> bestPerm = null;
			//Get distnace
			for (ArrayList<StoreLocation> storePerm : allStorePerms)
			{
				double distance = getRouteDistance(storePerm, origin);
				System.out.println("DISNTACE OF THIS RTOUTE IS " + distance + "# stores" + storeLocs.size());
				if (distance < bestDistance)
				{
					bestDistance = distance;
					bestPerm = storePerm;
				}
			}
			
			
			//ArrayList<StoreLocation> locs =  getStoreLocations(bestPerm);
			//locs.add(origin);
			SolutionRoute soln = new SolutionRoute();
			soln.stores = bestPerm;
			//soln.locations = locs;
			soln.distance = bestDistance;
			return soln;
		
	}
	
	private double getRouteDistance (ArrayList<StoreLocation> stores, Address origin)
	{
		double distance = 0;
		distance += TempFixes.getDistance(origin,  stores.get(0));
		int i = 1;
		for (; i < stores.size(); i++)
		{
			StoreLocation lastStore = stores.get(i-1);
			StoreLocation store = stores.get(i);
			distance += TempFixes.getDistance(lastStore, store);
		}
		
		distance += TempFixes.getDistance(origin,  stores.get(i-1));//StoreLocationDistance.find.where().eq("storeLocationA.id", stores.get(i-1).id).eq("storeLocationB.id", origin.id).findUnique();
		//StoreLocation.getDistance(.getLocation(), origin);
		return distance;
	}
	

	private ArrayList<ArrayList<StoreLocation>> getAllPermutations (ArrayList<StoreLocation> stores)
	{
		ArrayList<ArrayList<StoreLocation>> allPerms = new ArrayList<ArrayList<StoreLocation>>();
      
        if (stores.isEmpty()) {
        	allPerms.add(new ArrayList<StoreLocation>());
            return allPerms;
        }
        ArrayList<StoreLocation> list = new ArrayList<StoreLocation>(stores);
        StoreLocation head = list.get(0);
        ArrayList<StoreLocation> rest = new ArrayList<StoreLocation>(list.subList(1, list.size()));
        for (ArrayList<StoreLocation> permutations : getAllPermutations(rest)) {
        	ArrayList<ArrayList<StoreLocation>> subLists = new ArrayList<ArrayList<StoreLocation>>();
            for (int i = 0; i <= permutations.size(); i++) {
            	ArrayList<StoreLocation> subList = new ArrayList<StoreLocation>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            allPerms.addAll(subLists);
        }
        return allPerms;
	}

}
