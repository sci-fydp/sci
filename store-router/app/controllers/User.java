package controllers;

import java.util.List;
import java.util.Random;

import models.Item;
import models.StoreLocation;
import models.UserShoppingList;
import models.UserShoppingListItem;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class User extends Controller {
	
	public static Result register() {
		JsonNode json = request().body().asJson();
		
		JsonNode registrationJson = json.findPath("register");
//		JsonNode addressJson = registrationJson.findPath("address");
		
		String email = registrationJson.get("email").toString();
		String hashedPassword = registrationJson.get("password").toString();
		String storeIdAvoidList = registrationJson.get("avoidlist").toString();
		String udid = registrationJson.get("udid").toString();

		models.User user = models.User.find.where().eq("email", email).findUnique();
		
		if(user != null) {
    		return ok(Json.toJson("error: email not available"));
		}
		
//		String addressStr = addressJson.get("address_str").toString();
//		String postalCode = addressJson.get("postal_code").toString();
//		int cityId = addressJson.get("city_id").intValue();
//		int stateProvinceId = addressJson.get("state_province_id").intValue();
//		int country_id = addressJson.get("country_id").intValue();
//		
//		City city = City.find.where().eq("id", cityId).findUnique();
//    	StateProvince stateProvince = StateProvince.find.where().eq("id", stateProvinceId).findUnique();
//    	Country country = Country.find.where().eq("id", country_id).findUnique();
//    	
//    	if(city == null || stateProvince == null || country == null) {
//    		return status(450, "address id(s) not valid");
//    	}
//    	
//    	Address address = Address.find.where().eq("address", addressStr).eq("city.id", city.id).eq("postalCode", postalCode).eq("stateProvince.id", stateProvince.id).eq("country.id", country.id).findUnique();
//    	
//    	if(address != null) {
//    		address = new Address();
//        	address.address = addressStr;
//        	address.city = city;
//        	address.postalCode = postalCode;
//        	address.stateProvince = stateProvince;
//        	address.country = country;
//        	address.save();
//        	address = Address.find.where().eq("id", address.id).findUnique();
//    	}
    	
		user = new models.User();
    	user.address = null;
    	user.email = email;
    	user.hashedPassword = hashedPassword;
    	user.storeIdAvoidList = storeIdAvoidList;
    	user.verified = false;
    	user.udid = udid;
    	user.save();
    	user = models.User.find.where().eq("id", user.id).findUnique();
    	
    	return ok("registered");
	}
	
	private static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

	public static Result login() {
		JsonNode json = request().body().asJson();
		
		JsonNode loginJson = json.findPath("login");
		
		String email = loginJson.get("email").toString();
		String hashedPassword = loginJson.get("password").toString();
		String udid = loginJson.get("udid").toString();
		
		models.User user = models.User.find.where().eq("email", email).eq("hashedPassword", hashedPassword).findUnique();
		
		if(user == null) {
			return ok(Json.toJson("error: login failed"));
		}
		
		user.udid = udid;
		user.sessionStr = getRandomHexString(255);
		user.update();
		
		return ok("session=" + user.sessionStr);
	}
	
	public static Result saveShoppingList() {
		JsonNode json = request().body().asJson();
		
		JsonNode saveShoppingListJson = json.findPath("save");
		JsonNode userJson = saveShoppingListJson.findPath("user");

		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		
		String name = saveShoppingListJson.get("name").toString();
		
		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		UserShoppingList shoppingList = new UserShoppingList();
		shoppingList.userId = user.id;
		shoppingList.name = name;
		shoppingList.save();
		
		return ok(Json.toJson(shoppingList));
	}
	
	public static Result updateShoppingList() {
		JsonNode json = request().body().asJson();
		
		JsonNode updateShoppingListJson = json.findPath("update");
		JsonNode userJson = updateShoppingListJson.findPath("user");

		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		
		int shoppingListId = updateShoppingListJson.get("id").intValue();
		String name = updateShoppingListJson.get("name").toString();
		
		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).findUnique();
		shoppingList.userId = user.id;
		shoppingList.name = name;
		shoppingList.update();
		
		return ok(Json.toJson(shoppingList));
	}
	
	public static Result saveShoppingListItem() {
		JsonNode json = request().body().asJson();
		
		JsonNode saveShoppingItemJson = json.findPath("save");
		JsonNode userJson = saveShoppingItemJson.findPath("user");
		
		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		
		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		if(user == null) {
			return ok(Json.toJson("error: unauthorized action"));
		}
		
		int shoppingListId = saveShoppingItemJson.get("shopping_list_id").intValue();
		int itemId = saveShoppingItemJson.get("item_id").intValue();
		int locationId = saveShoppingItemJson.get("location_id").intValue();
		String name = saveShoppingItemJson.get("name").toString();
		String description = saveShoppingItemJson.get("description").toString();
		Float price = saveShoppingItemJson.get("price").floatValue();
		
		UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).findUnique();

		if(shoppingList == null) {
			return ok(Json.toJson("error: invalid shopping list id"));
		}
		
		UserShoppingListItem shoppingListItem = new UserShoppingListItem();
		shoppingListItem.shoppingList = shoppingList;
		
		Item item = Item.find.where().eq("id", itemId).findUnique();
		if(item != null) {
			shoppingListItem.item = item;
		}
		
		StoreLocation location = StoreLocation.find.where().eq("id", locationId).findUnique();
		if(location != null) {
			shoppingListItem.location = location;
		}
		
		shoppingListItem.name = name;
		
		shoppingListItem.description = description;
		
		shoppingListItem.price = price;
		
		shoppingListItem.save();
		
		return ok("saved");
	}
	
	public static Result getShoppingLists() {
		JsonNode json = request().body().asJson();
		
		JsonNode getShoppingListJson = json.findPath("get");
		JsonNode userJson = getShoppingListJson.findPath("user");
		
		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		
		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		if(user == null) {
			return ok(Json.toJson("error: unauthorized action"));
		}
		
		List<UserShoppingList> lists = UserShoppingList.find.where().eq("user_id", userId).findList();
		
		return ok(Json.toJson(lists));
	}
	
	public static Result getShoppingList() {
		JsonNode json = request().body().asJson();
		
		JsonNode getShoppingListJson = json.findPath("get");
		JsonNode userJson = getShoppingListJson.findPath("user");
		
		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		int shoppingListId = getShoppingListJson.get("shopping_list_id").intValue();
		
		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		if(user == null) {
			return ok(Json.toJson("error: unauthorized action"));
		}
		
		List<UserShoppingListItem> lists = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingListId).findList();
		
		return ok(Json.toJson(lists));
	}
}
