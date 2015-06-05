package controllers;

import java.util.Date;

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

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;

public class Test extends Controller {

    public static Result index() {
        return ok();
    }
    
    //generate truncate queries: SELECT Concat('TRUNCATE TABLE ', TABLE_NAME, ';') FROM INFORMATION_SCHEMA.TABLES where table_schema in ('store_route_db');
    
    public static Result testAll() {
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
    	item.name = "TESTITEM";
    	item.inventoryCategory = inventoryCategory;
    	item.save();
    	item = Item.find.where().eq("id", item.id).findUnique();
    	
    	Store store = new Store();
    	store.name = "TESTSTORE";
    	store.active = true;
    	store.inventoryCategory = inventoryCategory;
    	store.save();
    	store = Store.find.where().eq("id", store.id).findUnique();
    	
    	StoreLocation location = new StoreLocation();
    	location.store = store;
    	location.address = address;
    	location.active = true;
    	location.sundayBeginTime = new Date();
    	location.sundayEndTime = new Date();
    	location.save();
    	location = StoreLocation.find.where().eq("id", location.id).findUnique();
    	
    	LocationHoliday locationHoliday = new LocationHoliday();
    	locationHoliday.holiday = holiday;
    	locationHoliday.location = location;
    	locationHoliday.save();
    	locationHoliday = LocationHoliday.find.where().eq("holiday.id", locationHoliday.holiday.id).eq("location.id", locationHoliday.location.id).findUnique();

    	LocationItem locationItem = new LocationItem();
    	locationItem.storeLocation = location;
    	locationItem.item = item;
    	locationItem.price = 1.99f;
    	locationItem.stockCount = 2;
    	locationItem.save();
    	locationItem = LocationItem.find.where().eq("item.id", locationItem.item.id).eq("storeLocation.id", locationItem.storeLocation.id).findUnique();
    	
    	StoreLocationDistance distance = new StoreLocationDistance();
    	distance.storeLocationA = location;
    	distance.storeLocationB = location;
    	distance.distanceM = 1.23f;
    	distance.save();
    	distance = StoreLocationDistance.find.where().eq("storeLocationA.id", distance.storeLocationA.id).eq("storeLocationB.id", distance.storeLocationB.id).findUnique();
    	
    	User user = new User();
    	user.address = address;
    	user.email = "TESTEMAIL";
    	user.hashedPassword = "TESTPASSWORD";
    	user.storeIdAvoidList = "TESTLIST";
    	user.verified = true;
    	user.save();
    	user = User.find.where().eq("id", user.id).findUnique();
    	
    	return ok(views.html.test.render(city, stateProvince, country, address, holiday, inventoryCategory, item, store, location, locationHoliday, locationItem, distance, user));
    }
    
    public static Result googleMaps() {
    	GeoApiContext context = new GeoApiContext();
    	context.setApiKey("");
    	DirectionsApiRequest request = DirectionsApi.newRequest(context);
    	return ok();
    }
    
    
}
