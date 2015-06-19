package logics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Address;
import models.Item;
import models.LocationItem;
import models.StoreLocation;
import models.StoreLocationDistance;

public class TempFixes
{

	/**
	 * @param args
	 */
	public static double getDistance(Address a, StoreLocation x)
	{
		return 0;
	}

	// OK
	public static double getDistance(StoreLocation x, StoreLocation y)
	{
		StoreLocationDistance dis = StoreLocationDistance.find.where()
				.eq("storeLocationA.id", x.id).eq("storeLocationB.id", y.id)
				.findUnique();
		if ( dis == null)
		{
			dis = StoreLocationDistance.find.where()
					.eq("storeLocationA.id", y.id).eq("storeLocationB.id", x.id)
					.findUnique();
			if ( dis == null)
			{
				System.out.println("GET DISTANCE FAIL" + x.id + " " + y.id);
				return 0;
			}
			else
			{
				//System.out.println("GET NOT DISTANCE FAIL" + dis.distanceM  + " " + x.id + " " + y.id);
				return dis.distanceM;
			}
			
		}
		//System.out.println("GET NOT DISTANCE FAIL" + dis.distanceM  + " " + x.id + " " + y.id);
		return dis.distanceM;
	}

	public static StoreSearchResult getStores(double beginRange,
			double endRange, Set<Item> requiredProducts,  Address origin) throws NoItemFoundException
	{
		System.out.println("HELLO?" + requiredProducts.size());
		//Map of what each store sells in the required shopping list
		Map<StoreLocation, List<LocationItem>> whatTheHeck = new HashMap<StoreLocation, List<LocationItem>>();
		
		//Map Item to each store...  hmmm
		Map<Item, Set<StoreLocation>> whatThePart2 = new HashMap<Item, Set<StoreLocation>>();
		
		for (Item item : requiredProducts)
		{
			System.out.println("IS THERE AN " + item.name);
			List<LocationItem> locItems = LocationItem.find.where()
					.eq("item.name", item.name).findList();
			//locationItem2 = LocationItem.find.where().eq("item.id", locationItem2.item.id).eq("storeLocation.id", locationItem2.storeLocation.id).findUnique();
	    	
			if (locItems.size() == 0)
			{
				System.out.println("NO ITEMS OF TYPE " + item.name);
				throw new NoItemFoundException("Couldnt find item type " + item.id);
			}
			else
			{
				System.out.println("HMM : " + locItems.size() + " " + item.name + " FOUND.");
			}
			
			Set<StoreLocation> storesThatSellItem = new HashSet<StoreLocation>();
			for (LocationItem locItem : locItems)
			{
				//For whattheheck
				List<LocationItem> items = whatTheHeck.get(locItem.storeLocation);
				if (items == null)
				{
					items = new ArrayList<LocationItem>();
					items.add(locItem);
					whatTheHeck.put(locItem.storeLocation, items);
				}
				else
				{
					items.add(locItem);
					whatTheHeck.put(locItem.storeLocation, items);
				}
				
				storesThatSellItem.add(locItem.storeLocation);
				//For the second...
			}
			whatThePart2.put(item, storesThatSellItem);
		}
		
		System.out.println("NUM STORES " + whatTheHeck.keySet().size());
		StoreSearchResult result = new StoreSearchResult();
		result.itemToStoreMap = whatThePart2;
		result.storeMerchandiseMap = whatTheHeck;
		return result;
		
		
		//FILTER OUT OF RANGE
		/*Set<StoreLocation> outOfRangeSet = new HashSet<StoreLocation>();
		for (StoreLocation store : whatTheHeck.keySet())
		{
			double distance = getDistance(origin, store);
			if (distance > endRange || distance < beginRange)
			{
				outOfRangeSet.add(store);
			}
		}
		whatTheHeck.keySet().remove(outOfRangeSet);
		
		return whatTheHeck;
		*/
	}


	public static Set<LocationItem> getProductsFromStore(StoreLocation store)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static void whattheaddall()
	{
		// TODO Auto-generated method stub

	}

	public static StoreLocation convertAddressToStoreLocation(Address origin)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
