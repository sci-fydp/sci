import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


//You are insane if you use this with many stores.
public class BruteForceDistanceSolver extends DistanceSolver
{

	@Override
	public SolutionRoute findBestRoute(ArrayList<Store> stores, double[][] distances, Location origin)
	{
			ArrayList<ArrayList<Store>> allStorePerms = getAllPermutations(stores);
			//return route;
			double bestDistance = 999999;
			ArrayList<Store> bestPerm = null;
			//Get distnace
			for (ArrayList<Store> storePerm : allStorePerms)
			{
				double distance = getDistance(storePerm, origin);
				if (distance < bestDistance)
				{
					bestDistance = distance;
					bestPerm = storePerm;
				}
			}
			
			
			ArrayList<Location> locs =  getStoreLocations(bestPerm);
			locs.add(origin);
			SolutionRoute soln = new SolutionRoute();
			soln.stores = bestPerm;
			soln.locations = locs;
			soln.distance = bestDistance;
			return soln;
		
	}
	
	private double getDistance (ArrayList<Store> stores, Location origin)
	{
		double distance = 0;
		int i = 1;
		for (; i < stores.size(); i++)
		{
			Store lastStore = stores.get(i-1);
			Store store = stores.get(i);
			distance += Location.getDistance(lastStore.getLocation(), store.getLocation());
		}
		
		distance += Location.getDistance(stores.get(i-1).getLocation(), origin);
		return distance;
	}
	

	private ArrayList<ArrayList<Store>> getAllPermutations (ArrayList<Store> stores)
	{
		ArrayList<ArrayList<Store>> allPerms = new ArrayList<ArrayList<Store>>();
      
        if (stores.isEmpty()) {
        	allPerms.add(new ArrayList<Store>());
            return allPerms;
        }
        ArrayList<Store> list = new ArrayList<Store>(stores);
        Store head = list.get(0);
        ArrayList<Store> rest = new ArrayList<Store>(list.subList(1, list.size()));
        for (ArrayList<Store> permutations : getAllPermutations(rest)) {
        	ArrayList<ArrayList<Store>> subLists = new ArrayList<ArrayList<Store>>();
            for (int i = 0; i <= permutations.size(); i++) {
            	ArrayList<Store> subList = new ArrayList<Store>();
                subList.addAll(permutations);
                subList.add(i, head);
                subLists.add(subList);
            }
            allPerms.addAll(subLists);
        }
        return allPerms;
	}

}
