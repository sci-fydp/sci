package logics;

import java.util.List;
import java.util.Map;
import java.util.Set;

import models.Item;
import models.LocationItem;
import models.StoreLocation;

public class StoreSearchResult
{
	//Mapping of stores that sell the required items
	public Map<StoreLocation, List<LocationItem>> storeMerchandiseMap = null;
	
	//Mapping of items & stores that sell that item.
	public Map<Item, Set<StoreLocation>> itemToStoreMap = null;
}
