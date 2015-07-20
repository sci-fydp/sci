package controllers;

import java.util.Date;
import java.util.Random;

import models.Address;
import models.City;
import models.Country;
import models.Item;
import models.StateProvince;
import models.StoreLocation;
import models.UserItem;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class User extends Controller {
	
	public static Result register() {
		JsonNode json = request().body().asJson();
		
		JsonNode registrationJson = json.findPath("register");
		JsonNode addressJson = registrationJson.findPath("address");
		
		String email = registrationJson.get("email").toString();
		String hashedPassword = registrationJson.get("password").toString();
		String storeIdAvoidList = registrationJson.get("avoidlist").toString();
		
		models.User user = models.User.find.where().eq("email", email).findUnique();
		
		if(user != null) {
    		return status(450, "email not available");
		}
		
		String addressStr = addressJson.get("address_str").toString();
		String postalCode = addressJson.get("postal_code").toString();
		int cityId = addressJson.get("city_id").intValue();
		int stateProvinceId = addressJson.get("state_province_id").intValue();
		int country_id = addressJson.get("country_id").intValue();
		
		City city = City.find.where().eq("id", cityId).findUnique();
    	StateProvince stateProvince = StateProvince.find.where().eq("id", stateProvinceId).findUnique();
    	Country country = Country.find.where().eq("id", country_id).findUnique();
    	
    	if(city == null || stateProvince == null || country == null) {
    		return status(450, "address id(s) not valid");
    	}
    	
    	Address address = Address.find.where().eq("address", addressStr).eq("city.id", city.id).eq("postalCode", postalCode).eq("stateProvince.id", stateProvince.id).eq("country.id", country.id).findUnique();
    	
    	if(address != null) {
    		address = new Address();
        	address.address = addressStr;
        	address.city = city;
        	address.postalCode = postalCode;
        	address.stateProvince = stateProvince;
        	address.country = country;
        	address.save();
        	address = Address.find.where().eq("id", address.id).findUnique();
    	}
    	
		user = new models.User();
    	user.address = address;
    	user.email = email;
    	user.hashedPassword = hashedPassword;
    	user.storeIdAvoidList = storeIdAvoidList;
    	user.verified = false;
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
		
		models.User user = models.User.find.where().eq("email", email).eq("hashedPassword", hashedPassword).findUnique();
		
		if(user == null) {
			return status(451, "login failed");
		}
		
		user.sessionStr = getRandomHexString(255);
		user.update();
		
		return ok("session=" + user.sessionStr);
	}
	
	public static Result saveItem() {
		JsonNode json = request().body().asJson();
		
		JsonNode saveItemJson = json.findPath("save");
		JsonNode userJson = saveItemJson.findPath("user");
		
		int userId = userJson.get("user_id").intValue();
		String sessionStr = userJson.get("session").toString();
		
		int itemId = saveItemJson.get("item_id").intValue();
		int locationId = saveItemJson.get("location_id").intValue();
		String name = saveItemJson.get("name").toString();
		String description = saveItemJson.get("description").toString();
		Float price = saveItemJson.get("price").floatValue();

		models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
		
		long minutes = 5 * 60000;
		
		if(user == null || System.currentTimeMillis() - user.modifyDate.getTime() > minutes) {
			return status(452, "unauthorized action");
		}
		
		Item item = Item.find.where().eq("id", itemId).findUnique();
		StoreLocation location = StoreLocation.find.where().eq("id", locationId).findUnique();
		
		UserItem userItem = new UserItem();
		userItem.user = user;
		
		if(item != null) {
			userItem.item = item;
		}
		if(location != null) {
			userItem.location = location;
		}
		
		userItem.name = name;
		
		userItem.description = description;
		
		userItem.price = price;
		
		userItem.save();
		
		return ok("saved");
	}
}
