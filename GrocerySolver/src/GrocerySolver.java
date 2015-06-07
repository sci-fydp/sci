import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrocerySolver
{

	private static GrocerySolver instance;
	private static double DISTANCE_INTERVALS = 100;
	private static double MAX_DISTANCE = 200;
	private GrocerySolver()
	{
	
	}
	
	public static GrocerySolver getInstance()
	{
		if (instance == null)
		{
			instance = new GrocerySolver();
		}
		return instance;
	}
	
	public SolutionRoute solve(SearchParams params)
	{
		System.out.println("FIND OPT");
		List<Store> someStores = findOptimalStores(params);
		if (someStores == null)
		{
			System.out.println("NOSOLN????");
		}
		ArrayList<Store> stores = new ArrayList<Store>(someStores);
		SolutionRoute soln = solveDistance(stores, params);
		/*System.out.println("STORE LOCS");
		ArrayList<Location> storeLocations = getStoreLocations(stores);
		System.out.println("LOOKING FOR DISTANCE");
		double[][] distances = getAllDistances(storeLocations, params.origin);
		ArrayList<Location> goalRoute = findBestRoute(storeLocations, distances, params.origin);
		*/
		return soln;
	}
	
	/*
	 * public class SolutionRoute
{
	ArrayList<Store> stores;
	ArrayList<Location> locations;
	double price;
	double distance;

}
	 */
	private SolutionRoute solveDistance(ArrayList<Store> stores, SearchParams params)
	{
		ArrayList<Location> storeLocations = getStoreLocations(stores);
		double[][] distances = getAllDistances(storeLocations, params.origin);
		DistanceSolver solver = new BruteForceDistanceSolver();
		SolutionRoute soln = solver.findBestRoute(stores, distances, params.origin);
		//stores = rearrangeStores(stores, goalRoute);
		return soln;
	}
	
	private ArrayList<Location> getStoreLocations(ArrayList<Store> stores)
	{
		ArrayList<Location> locations = new ArrayList<Location>();
		for (Store store : stores)
		{
			locations.add(store.getLocation());
		}
		return locations;
	}
	
	private double[][] getAllDistances(ArrayList<Location> locations, Location origin)
	{
		int numLocations = locations.size();
		double[][] distanceArray = new double[numLocations + 1][numLocations + 1];
		for (int i = 0; i < numLocations; i++)
		{
			for (int j = 0; j < numLocations; j++)
			{
				distanceArray[i][j] = Location.getDistance(locations.get(i), locations.get(j));
			}
		}
		
		//Last row/col do origin
		for (int i = 0; i < numLocations; i++)
		{
			distanceArray[i][numLocations] = Location.getDistance(locations.get(i), origin);
		}
		
		for (int i = 0; i < numLocations; i++)
		{
			distanceArray[numLocations][i] = Location.getDistance(locations.get(i), origin);
		}
		
		return distanceArray;
	}
	
	private List<Store> findOptimalStores(SearchParams params)
	{
		 List<Store> stores = new ArrayList<Store>();
		
		 HashSet<Product> requiredProducts = params.groceryList;
		 //List<Integer> productCounts = new ArrayList<Integer>();
		 List<Product> productList = new ArrayList<Product>();
		 productList.addAll(requiredProducts);
		 int[] productListCount = new int[productList.size()];
		 
		 double beginRange = 0;
		 double endRange = 0 + DISTANCE_INTERVALS;
		 while (true)//(productsSoFar.size() != requiredProducts.size())
		 {
			 ArrayList<Store> currentStores = RandomDatabase.getInstance().getStoresInRegion(beginRange, endRange, params.origin);
			 
			 if (endRange > MAX_DISTANCE)
			 {
				 System.out.println("FAILED");
				 return null;
			 }
			 else if (currentStores.size() == 0)
			 {
				 System.out.println("wtf?");
				 beginRange += DISTANCE_INTERVALS;
				 endRange += DISTANCE_INTERVALS;
				 continue;
			 }
			 final Location origin = params.origin;
			 
			 sortStoresByDistance(currentStores, origin);
			
			 System.out.println("CURRENT STORES " + currentStores.size());
			 for (Store store : currentStores)
			 {
				 boolean hasNew = false;
				 Set<Product> products = store.getProducts();
				 System.out.println("STORE " + store.name + " HAS X PRODUCTS " + products.size());
				 for (Product product : products)
				 {
					 int index = productList.indexOf(product);
					 if (index >= 0)
					 {
						hasNew = true;
						System.out.println("ADDED TO " + index);
						productListCount[index]++; 
					 }
				 }
				 if (hasNew)
				 {
					 stores.add(store);
				 }
				 
				 //Just stop when found all products? we did sort.
				 /*if (hasAllRequiredProduct(productListCount))
				 {
					 break;
				 }*/
			 }
			 
			 if (hasAllRequiredProduct(productListCount))
			 {
				 break;
			 }
			 
			 beginRange += DISTANCE_INTERVALS;
			 endRange += DISTANCE_INTERVALS;
		 }
		 
		// List<Store> storeVariation = new ArrayList<Store>();
		 //storeVariation.addAll(allPossibleStores);
		 
		 
		 //someSolutions.add(stores);

		 Set<List<Store>> someSolutions = new HashSet<List<Store>>();	
		 //List<Store> notRequiredStores = new ArrayList<Store>();
		 findStoreSolutions(stores, productListCount, someSolutions, productList);
		// }
		 List<Store> bestSoln = null;
		 int size = 999;
		 for (List<Store> solns : someSolutions)
		 {
			 System.out.println("");
			 for (Store store : solns)
			 {
				 System.out.print(" " + store.name);
			 }
			 if (solns.size() < size)
			 {
				 size = solns.size();
				 bestSoln = solns;
			 }
			 
		 }
		 System.out.println("");
		 return bestSoln;
	}
	
	//TODO
	private double getCost(SearchParams params, List<Store> stores)
	{
		HashMap<Store, List<Product>> buyFrom = new HashMap<Store, List<Product>>();
		for (Product product : params.groceryList)
		{
			double bestPrice = 43289048;
			Store bestStore = null;
			for (Store store : stores)
			{
				if (store.hasProduct(product))
				{
					
				}
			}
		}
		return 0;
	}
	
	private static void findStoreSolutions(List<Store> stores,  int[] productListCount, Set<List<Store>> someSolutions, List<Product> productList)
	{
		for (Store store : stores)
		 {
			 int[] productCount = new int[productListCount.length];
			 System.arraycopy( productListCount, 0, productCount, 0, productListCount.length );
			 
			 Set<Product> products = store.getProducts();
			 for (Product product : products)
			 {
				 int index = productList.indexOf(product);
				 if (index >= 0)
				 {
					 productCount[index]--; 
				 }
			 }
			 
			 //Just stop when found all products? we did sort.
			 if (hasAllRequiredProduct(productCount))
			 {
				List<Store> newStores = new ArrayList<Store>(stores);
				newStores.remove(store);
				someSolutions.add(newStores);
				findStoreSolutions(newStores, productListCount, someSolutions, productList);
			 }
		 }
		
	}
	private static boolean hasAllRequiredProduct(int[] count)
	{
		for (int i = 0; i < count.length; i++)
		{
			 if (count[i] <= 0)
			 {
				 
				 return false;
			 }
		}
		return true;
	}
	
	public static void sortStoresByDistance(List<Store> currentStores, final Location origin)
	{
		Collections.sort(currentStores, new Comparator<Store>()
		{
			public int compare(Store o1, Store o2)
			{
				double distance1 = Location.getDistance(o1.getLocation(), origin);
				double distance2 = Location.getDistance(o2.getLocation(), origin);
				if (distance1 > distance2)
				{
					return 1;
				}
				else if (distance2 > distance1)
				{
					return -1;
				}
				return 0;
			}
		});
	}
}
