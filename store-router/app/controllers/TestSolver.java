package controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import logics.GrocerySolver;
import logics.NoItemFoundException;
import logics.SearchParams;
import logics.SolutionRoute;
import models.Address;
import models.City;
import models.Country;
import models.Holiday;
import models.InventoryCategory;
import models.Item;
import models.LocationHoliday;
import models.LocationItem;
import models.StateProvince;
import models.Store;
import models.StoreLocation;
import models.StoreLocationDistance;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
 
public class TestSolver extends Controller 
{
    public static Result tryFindSolution() throws NoItemFoundException{
    	//setData();
    	SearchParams params = new SearchParams();
		HashSet<Item> products = new HashSet<Item>();
		List<Item> a = Item.find.where().eq("name", "GIANTCACTI").findList();
		List<Item> b = Item.find.where().eq("name", "SMALLCACTI").findList();
		
		products.add(a.get(0));
		products.add(b.get(0));
		
		params.groceryList = products;
		params.origin = new Address();
		SolutionRoute soln = GrocerySolver.getInstance().solve(params); 
		String ans = new String();
		ans = "Go to :\n";
		for (StoreLocation loc : soln.stores)
		{
			ans += loc.store.name + "\n";
		}
		
		System.out.println(ans);
		
		
    	return ok();
    }
    
    public static void setData()
    {
    	City city = new City();
    	city.name = "TESTCITY";
    	city.save();
    	city = City.find.where().eq("id", city.id).findUnique();
    	
    	StateProvince stateProvince = new StateProvince();
    	stateProvince.name = "TESTSTATEPROVINCE";
    	stateProvince.save();
    	stateProvince = StateProvince.find.where().eq("id", stateProvince.id).findUnique();
    	
    	Country country = new Country();
    	country.name = "TESTCOUNTRY";
    	country.save();
    	country = Country.find.where().eq("id", country.id).findUnique();
    	
    	Address address = new Address();
    	address.address = "TESTADDRESS";
    	address.city = city;
    	address.postalCode = "TESTPOSTAL";
    	address.stateProvince = stateProvince;
    	address.country = country;
    	address.save();
    	address = Address.find.where().eq("id", address.id).findUnique();
    	
    	Holiday holiday = new Holiday();
    	holiday.name = "TESTHOLIDAY";
    	holiday.dayOfMonth = 1;
    	holiday.dayOfWeek = 2;
    	holiday.monthOfYear = 3;
    	holiday.iterationOfWeek = 4;
    	holiday.save();
    	holiday = Holiday.find.where().eq("id", holiday.id).findUnique();
    	
    	InventoryCategory inventoryCategory = new InventoryCategory();
    	inventoryCategory.name = "TESTINVENTORYCATEGORY";
    	inventoryCategory.save();
    	inventoryCategory = InventoryCategory.find.where().eq("id", inventoryCategory.id).findUnique();
    	
    	Item item = new Item();
    	item.name = "SMALLCACTI";
    	item.description = "SMALLCACTI Desc";
    	item.inventoryCategory = inventoryCategory;
    	item.save();
    	item = Item.find.where().eq("id", item.id).findUnique();
    	
    	Item item2 = new Item();
    	item2.name = "GIANTCACTI";
    	item2.description = "GIANTCACTI Desc";
    	item2.inventoryCategory = inventoryCategory;
    	item2.save();
    	item2 = Item.find.where().eq("id", item2.id).findUnique();
    	
    	Store store = new Store();
    	store.name = "Store Cactus";
    	store.active = true;
    	store.inventoryCategory = inventoryCategory;
    	store.save();
    	store = Store.find.where().eq("id", store.id).findUnique();
    	
    	
    	Store store2 = new Store();
    	store2.name = "Store GiantCacti";
    	store2.active = true;
    	store2.inventoryCategory = inventoryCategory;
    	store2.save();
    	store2 = Store.find.where().eq("id", store2.id).findUnique();
    	
    	
    	System.out.println("HELLO FIRST");
    	Store store3 = new Store();
    	store3.name = "Store ALLCacti";
    	store3.active = true;
    	store3.inventoryCategory = inventoryCategory;
    	store3.save();
    	store3 = Store.find.where().eq("id", store3.id).findUnique();
    	System.out.println("HELLO FIRST2");
    	
    	StoreLocation location3 = new StoreLocation();
    	location3.store = store3;
    	location3.address = address;
    	location3.active = true;
    	location3.sundayBeginTime = new Date();
    	location3.sundayEndTime = new Date();
    	location3.save();
    	location3 = StoreLocation.find.where().eq("id", location3.id).findUnique();
    	System.out.println("HELLO FIRST3");
    	
    	LocationItem locationItem3 = new LocationItem();
    	locationItem3.storeLocation = location3;
    	locationItem3.item = item;
    	locationItem3.price = 4.00f;
    	locationItem3.stockCount = 2;
    	locationItem3.save();
    	locationItem3 = LocationItem.find.where().eq("item.id", locationItem3.item.id).eq("storeLocation.id", locationItem3.storeLocation.id).findUnique();
    	System.out.println("HELLO FIRS4T");
    	LocationItem locationItem4 = new LocationItem();
    	locationItem4.storeLocation = location3;
    	locationItem4.item = item2;
    	locationItem4.price = 5.00f;
    	locationItem4.stockCount = 2;
    	locationItem4.save();
    	locationItem4 = LocationItem.find.where().eq("item.id", locationItem4.item.id).eq("storeLocation.id", locationItem4.storeLocation.id).findUnique();
    	System.out.println("HELLO FIRS5T");
    	
    	StoreLocation location = new StoreLocation();
    	location.store = store;
    	location.address = address;
    	location.active = true;
    	location.sundayBeginTime = new Date();
    	location.sundayEndTime = new Date();
    	location.save();
    	location = StoreLocation.find.where().eq("id", location.id).findUnique();
    	
    	
    	LocationItem locationItem = new LocationItem();
    	locationItem.storeLocation = location;
    	locationItem.item = item;
    	locationItem.price = 2.5f;
    	locationItem.stockCount = 2;
    	locationItem.save();
    	locationItem = LocationItem.find.where().eq("item.id", locationItem.item.id).eq("storeLocation.id", locationItem.storeLocation.id).findUnique();
    	
    	StoreLocation location2 = new StoreLocation();
    	location2.store = store2;
    	location2.address = address;
    	location2.active = true;
    	location2.sundayBeginTime = new Date();
    	location2.sundayEndTime = new Date();
    	location2.save();
    	location2 = StoreLocation.find.where().eq("id", location2.id).findUnique();
    	
    	
    	LocationItem locationItem2 = new LocationItem();
    	locationItem2.storeLocation = location2;
    	locationItem2.item = item2;
    	locationItem2.price = 3.5f;
    	locationItem2.stockCount = 2;
    	locationItem2.save();
    	locationItem2 = LocationItem.find.where().eq("item.id", locationItem2.item.id).eq("storeLocation.id", locationItem2.storeLocation.id).findUnique();
    	
    	

    	
    	LocationHoliday locationHoliday = new LocationHoliday();
    	locationHoliday.holiday = holiday;
    	locationHoliday.location = location;
    	locationHoliday.save();
    	locationHoliday = LocationHoliday.find.where().eq("holiday.id", locationHoliday.holiday.id).eq("location.id", locationHoliday.location.id).findUnique();

    	
    	
    	StoreLocationDistance distance = new StoreLocationDistance();
    	distance.storeLocationA = location;
    	distance.storeLocationB = location2;
    	distance.distanceM = 1.23f;
    	distance.save();
    	distance = StoreLocationDistance.find.where().eq("storeLocationA.id", distance.storeLocationA.id).eq("storeLocationB.id", distance.storeLocationB.id).findUnique();
    	
    	distance = new StoreLocationDistance();
    	distance.storeLocationA = location2;
    	distance.storeLocationB = location3;
    	distance.distanceM = 2.45f;
    	distance.save();
    	distance = StoreLocationDistance.find.where().eq("storeLocationA.id", distance.storeLocationA.id).eq("storeLocationB.id", distance.storeLocationB.id).findUnique();
    	
    	distance = new StoreLocationDistance();
    	distance.storeLocationA = location;
    	distance.storeLocationB = location3;
    	distance.distanceM = 3.49f;
    	distance.save();
    	distance = StoreLocationDistance.find.where().eq("storeLocationA.id", distance.storeLocationA.id).eq("storeLocationB.id", distance.storeLocationB.id).findUnique();
    	
    }
}
