package logics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.Logger;

import models.Address;
import models.Item;
import models.LocationItem;
import models.StoreLocation;


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
	
	public SolutionRoute solve(SearchParams params) throws NoItemFoundException
	{
		System.out.println("FIND OPT");
		Set<StoreSearchResult> someStores = findPossibleStores(params);
		if (someStores == null)
		{
			throw new NoItemFoundException ("NOSOLN NULL?");
			//System.out.println("NOSOLN????");
		}
		
		if (someStores.size() == 0)
		{
			throw new NoItemFoundException ("NOSOLN zero res");
		}
		SolutionRoute bestSoln = null;
		double bestScore = -9999;
		for (StoreSearchResult result : someStores)
		{
			
			SolutionRoute soln = findSolution(result, params);

			System.out.println("SOLN + NUM STORES " + result.storeMerchandiseMap.keySet().size() + " " +   soln.distance);
			double score = evaluateSolution(soln);
			if (score > bestScore)
			{
				bestSoln = soln;
				bestScore = score;
			}
		}
		
		return bestSoln;
	}
	
	private static SolutionRoute findSolution(StoreSearchResult result, SearchParams params)
	{
		BruteForceDistanceSolver solver = new BruteForceDistanceSolver();
		
		SolutionRoute route = solver.findBestRoute(result.storeMerchandiseMap.keySet(), params.origin);
		getPrice(route, result, params);
		// TODO GET PRICE
		return route;
	}
	
	public static double evaluateSolution(SolutionRoute route)
	{
		if (route.distance < 1)
		{
			return 100 + 100/(route.price+1);
		}
		return 1/route.distance + 100/(route.price+1);
	}

	private Set<StoreSearchResult> findPossibleStores(SearchParams params) throws NoItemFoundException
	{
		 HashSet<Item> requiredProducts = params.groceryList;

		 double beginRange = 0;
		 double endRange = 0 + DISTANCE_INTERVALS;
		 StoreSearchResult currentStores = null;
		// Logger.debug("The param was");
		 while (true)//(productsSoFar.size() != requiredProducts.size())
		 {
			try
			{
				currentStores = TempFixes.getStores(beginRange, endRange,
						requiredProducts, params.origin);
				// final Address origin = params.origin;
				break;

			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
			// beginRange += DISTANCE_INTERVALS;
			endRange += DISTANCE_INTERVALS;
			if (endRange > MAX_DISTANCE)
			{
				// we ded.
				return null;
			}
		 }
	
		 Set<StoreSearchResult> someSolutions = new HashSet<StoreSearchResult>();
		 someSolutions.add(currentStores);
		 findStoreSolutions(currentStores, requiredProducts, someSolutions);
		 
		 
		 return someSolutions;
	}
	
	
	static int runNum = 0;
	private static void findStoreSolutions(
			StoreSearchResult searchResult,
			HashSet<Item> requiredProducts,
			Set<StoreSearchResult> someSolutions) throws NoItemFoundException
	{
		Map<StoreLocation, List<LocationItem>> storeMerchandiseMap = searchResult.storeMerchandiseMap;
		
		
		//Mapping of items & stores that sell that item.
		Map<Item, Set<StoreLocation>> itemToStoreMap = searchResult.itemToStoreMap;
		System.out.println("RUN NUM" + runNum + " HAS X STORES " + storeMerchandiseMap.keySet().size());
		for (StoreLocation store : storeMerchandiseMap.keySet())
		{
			
			List<LocationItem> itemsSoldByStore = storeMerchandiseMap.get(store);
			boolean canRemove = true;
			System.out.println("BEGIN CHECK" + store.store.name + " NUM STORES " + storeMerchandiseMap.keySet());
			for (LocationItem item : itemsSoldByStore)
			{
				runNum++;
				if (runNum > 20)
				{
					throw new NoItemFoundException("WHAT IS HAPPPENIGN");
				}
				Set<StoreLocation> stores = itemToStoreMap.get(item.item);
				System.out.println("MAP SIZE " + itemToStoreMap.size());
				//This is the only store selling it.
				System.out.println("XSTORES ARE SELLING " + stores.size());
				if (stores.size() <= 1)
				{
					System.out.println("CANT REMOVE THIS STORE " + store.store.name + " ONLY 1 " + item.item.name);
					canRemove = false;
					break;
				}
				
			}
			
			if (canRemove)
			{
				System.out.println("REMOVED ONE" + store.store.name);
				StoreSearchResult newResult = new StoreSearchResult();
				Map<StoreLocation, List<LocationItem>> newStoreMerchandiseMap = new HashMap<StoreLocation, List<LocationItem>>(searchResult.storeMerchandiseMap);
			
				
				Map<Item, Set<StoreLocation>> newItemToStoreMap = new HashMap<Item, Set<StoreLocation>>(searchResult.itemToStoreMap);
				List<LocationItem> newItemsSoldByStore = new ArrayList<LocationItem>(newStoreMerchandiseMap.get(store));
				
				newStoreMerchandiseMap.remove(store);
				newResult.storeMerchandiseMap = newStoreMerchandiseMap;
				
				
				for (LocationItem item : newItemsSoldByStore)
				{
					Set<StoreLocation> stores = new HashSet<StoreLocation>(newItemToStoreMap.get(item.item));
					stores.remove(store);
					newItemToStoreMap.put(item.item, stores);
				}
				newResult.itemToStoreMap = newItemToStoreMap;
			
				System.out.println("ADDED NEW POSSIBILITY");
				someSolutions.add(newResult);
				findStoreSolutions(newResult, requiredProducts, someSolutions);
			}
		}
	}

	//TODO
	private static void getPrice(SolutionRoute soln, StoreSearchResult result, SearchParams params)
	{
		
		HashMap<StoreLocation, List<LocationItem>> buyFrom = new HashMap<StoreLocation, List<LocationItem>>();
		for (StoreLocation s : result.storeMerchandiseMap.keySet())
		{
			buyFrom.put(s, new ArrayList<LocationItem>());
		}
		double totalPrice = 0;
		for (Item product : result.itemToStoreMap.keySet())
		{
			double bestPrice = 43289048;
			StoreLocation bestStore = null;
			Set<StoreLocation> storesThatSellProduct = result.itemToStoreMap.get(product);
			LocationItem bestLocationItem = null;
		
			for (StoreLocation store : storesThatSellProduct)
			{
				List<LocationItem> soldItems = result.storeMerchandiseMap.get(store);
				for (int i = 0; i < soldItems.size(); i++)
				{
					LocationItem curItem = soldItems.get(i);
					if (product.equals(curItem.item))
					{
						double price = curItem.price;
						if (price < bestPrice)
						{
							bestLocationItem = curItem;
							bestPrice = price;
							bestStore = store;
						}
						break;
					}
				}
			}
			
			totalPrice += bestPrice;
			List<LocationItem> buyItemsFromStore = buyFrom.get(bestStore);
			buyItemsFromStore.add(bestLocationItem);
			buyFrom.put(bestStore, buyItemsFromStore);
		}
		soln.price = totalPrice;
		soln.buyFromStore = buyFrom;
		System.out.println("TOTAL PRICE WAS " + totalPrice);
	}
}
