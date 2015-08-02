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
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/register -d '{"register":{"email":"alqiluo@gmail.com", "password":"asdf", "avoidlist":"avoidlist", "udid":"1234"}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/saveShoppingList -d '{"save":{"user":{"user_id":1, "session_str":"asdf"}, "name":"shoppingList1"}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/getShoppingLists -d '{"getShoppingLists":{"user":{"user_id":1, "session_str":"asdf"}}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/updateShoppingList -d '{"update":{"user":{"user_id":1, "session_str":"asdf"}, "id":2, "name":"newShoppingList"}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/deleteShoppingList -d '{"delete":{"user":{"user_id":1, "session_str":"asdf"}, "id":1}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/saveShoppingListItems -d '{"save":{"user":{"user_id":1, "session_str":"asdf"}, "shopping_list_id":1, "item_id":null, "location_id":null, "name":"itemName", "description":"description", "price":123}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/getShoppingListItems -d '{"get":{"user":{"user_id":1, "session_str":"asdf"}, "shopping_list_id":1}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/updateShoppingListItems -d '{"update":{"user":{"user_id":1, "session_str":"asdf"}, "shopping_list_id":1, "items":[{"id":1,"item_id":null, "location_id":null, "name":"newItemName", "description":"newDescription", "price":1234},{"id":2,"item_id":null, "location_id":null, "name":"newItemName2", "description":"newDescription2", "price":456}]}}'
    
    // curl -H "Content-Type: application/json; charset=UTF-8" -X POST http://localhost:9000/user/deleteShoppingListItems -d '{"delete":{"user":{"user_id":1, "session_str":"asdf"}, "shopping_list_id":1, "item_ids":[1,2]}}'
    
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
    	item.description = "TESTDESCRIPTION";
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
