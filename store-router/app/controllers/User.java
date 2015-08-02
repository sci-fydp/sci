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
		try {
			JsonNode json = request().body().asJson();
			JsonNode registrationJson = json.findPath("register");
//			JsonNode addressJson = registrationJson.findPath("address");
			
			String email = registrationJson.get("email").textValue();
			String hashedPassword = registrationJson.get("password").textValue();
			String storeIdAvoidList = registrationJson.get("avoidlist").textValue();
			String udid = registrationJson.get("udid").textValue();

			models.User user = models.User.find.where().eq("email", email).findUnique();
			
			if(user != null) {
	    		return ok(Json.toJson("error: email not available"));
			}
			
//			String addressStr = addressJson.get("address_str").toString();
//			String postalCode = addressJson.get("postal_code").toString();
//			int cityId = addressJson.get("city_id").intValue();
//			int stateProvinceId = addressJson.get("state_province_id").intValue();
//			int country_id = addressJson.get("country_id").intValue();
//			
//			City city = City.find.where().eq("id", cityId).findUnique();
//	    	StateProvince stateProvince = StateProvince.find.where().eq("id", stateProvinceId).findUnique();
//	    	Country country = Country.find.where().eq("id", country_id).findUnique();
//	    	
//	    	if(city == null || stateProvince == null || country == null) {
//	    		return status(450, "address id(s) not valid");
//	    	}
//	    	
//	    	Address address = Address.find.where().eq("address", addressStr).eq("city.id", city.id).eq("postalCode", postalCode).eq("stateProvince.id", stateProvince.id).eq("country.id", country.id).findUnique();
//	    	
//	    	if(address != null) {
//	    		address = new Address();
//	        	address.address = addressStr;
//	        	address.city = city;
//	        	address.postalCode = postalCode;
//	        	address.stateProvince = stateProvince;
//	        	address.country = country;
//	        	address.save();
//	        	address = Address.find.where().eq("id", address.id).findUnique();
//	    	}
	    	
			user = new models.User();
	    	user.address = null;
	    	user.email = email;
	    	user.hashedPassword = hashedPassword;
	    	user.storeIdAvoidList = storeIdAvoidList;
	    	user.verified = false;
	    	user.udid = udid;
	    	user.sessionStr = getRandomHexString(255);
	    	user.save();
	    	user = models.User.find.where().eq("id", user.id).findUnique();
	    	
	    	user.hashedPassword = null;
	    	
	    	return ok(Json.toJson(user));
		}
		catch(Exception e) {
			System.out.println("Register Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
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
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode loginJson = json.findPath("login");
			
			String email = loginJson.get("email").textValue();
			String hashedPassword = loginJson.get("password").textValue();
			String udid = loginJson.get("udid").textValue();
			
			models.User user = models.User.find.where().eq("email", email).eq("hashedPassword", hashedPassword).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			user.udid = udid;
			user.sessionStr = getRandomHexString(255);
			user.update();
			
			user.hashedPassword = null;
			
			return ok(Json.toJson(user));
		}
		catch(Exception e) {
			System.out.println("Login Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result saveShoppingList() {
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode saveShoppingListJson = json.findPath("save");
			JsonNode userJson = saveShoppingListJson.findPath("user");
	
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			String name = saveShoppingListJson.get("name").textValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			UserShoppingList shoppingList = new UserShoppingList();
			shoppingList.userId = user.id;
			shoppingList.name = name;
			shoppingList.save();
			
			return ok(Json.toJson(shoppingList));
		}
		catch(Exception e) {
			System.out.println("SaveShoppingList Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result getShoppingLists() {
		try {
			JsonNode json = request().body().asJson();
			JsonNode getShoppingListJson = json.findPath("getShoppingLists");
			JsonNode userJson = getShoppingListJson.findPath("user");
			
			int userId = userJson.get("user_id").intValue();
						
			String sessionStr = userJson.get("session_str").textValue();
						
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			
			List<UserShoppingList> lists = UserShoppingList.find.where().eq("user_id", userId).findList();
			
			return ok(Json.toJson(lists));
		}
		catch(Exception e) {
			System.out.println("GetShoppingListItem Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result updateShoppingList() {
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode updateShoppingListJson = json.findPath("update");
			JsonNode userJson = updateShoppingListJson.findPath("user");
	
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			int shoppingListId = updateShoppingListJson.get("id").intValue();
			String name = updateShoppingListJson.get("name").textValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			
			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();
			
			if(shoppingList == null) {
				return ok(Json.toJson("shoppinglist with id:" + shoppingListId + " does not exist"));
			}
			
			shoppingList.userId = user.id;
			shoppingList.name = name;
			shoppingList.update();
			
			return ok(Json.toJson(shoppingList));
		}
		catch(Exception e) {
			System.out.println("UpdateShoppingList Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result deleteShoppingList() {
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode updateShoppingListJson = json.findPath("delete");
			
			JsonNode userJson = updateShoppingListJson.findPath("user");
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			int shoppingListId = updateShoppingListJson.get("id").intValue();
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();
			
			if(shoppingList == null) {
				return ok(Json.toJson("shoppinglist with id:" + shoppingListId + " does not exist"));
			}
			
			shoppingList.delete();
			
			return ok(Json.toJson("deleted"));
		}
		catch(Exception e) {
			System.out.println("DeleteShoppingList Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result saveShoppingListItems() {
		/* {
			  "save": {
			    "user": {
			      "user_id": 1,
			      "session_str": "asdf"
			    },
			    "shopping_list_id": 1,
			    "items": [
			      {
			        "item_id": null,
			        "location_id": null,
			        "name": "itemName",
			        "description": "description",
			        "price": 123
			      },
			      {
			        "item_id": null,
			        "location_id": null,
			        "name": "itemName",
			        "description": "description",
			        "price": 123
			      }
			    ]
			  }
			}
		 * 
		 * 
		 */
		
		try{
			JsonNode json = request().body().asJson();
			
			JsonNode saveShoppingItemJson = json.findPath("save");
			JsonNode userJson = saveShoppingItemJson.findPath("user");
			
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			
			int shoppingListId = saveShoppingItemJson.get("shopping_list_id").intValue();
			
			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();
			
			if(shoppingList == null) {
				return ok(Json.toJson("error: invalid shopping list id"));
			}
			
			JsonNode itemsNode = saveShoppingItemJson.get("items");
			if(itemsNode.isArray()) {
				for(JsonNode itemNode : itemsNode) {
					int itemId = itemNode.get("item_id").intValue();
					int locationId = itemNode.get("location_id").intValue();
					String name = itemNode.get("name").textValue();
					String description = itemNode.get("description").textValue();
					Float price = itemNode.get("price").floatValue();
					
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
					
					shoppingListItem.item = null;
					
					shoppingListItem.save();
				}
			}
			
			return ok(Json.toJson("saved"));
		}
		catch(Exception e) {
			System.out.println("SaveShoppingListItem Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result getShoppingListItems() {
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode getShoppingListJson = json.findPath("get");
			JsonNode userJson = getShoppingListJson.findPath("user");
			
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			int shoppingListId = getShoppingListJson.get("shopping_list_id").intValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			
			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();

			if(shoppingList == null) {
				return ok(Json.toJson("error: unauthorized shopping list"));
			}
			
			List<UserShoppingListItem> items = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingListId).findList();
			
			return ok(Json.toJson(items));
		}
		catch(Exception e) {
			System.out.println("GetShoppingListItem Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result updateShoppingListItems() {
		try {
			/* {
				  "update": {
				    "user": {
				      "user_id": 1,
				      "session_str": "asdf"
				    },
				    "shopping_list_id": 1,
				    "items": [
				      {
				        "id": 1,
				        "item_id": null,
				        "location_id": null,
				        "name": "newItemName",
				        "description": "newDescription",
				        "price": 1234
				      },
				      {
				        "id": 2,
				        "item_id": null,
				        "location_id": null,
				        "name": "newItemName2",
				        "description": "newDescription2",
				        "price": 456
				      }
				    ]
				  }
				}
			} */
					
			JsonNode json = request().body().asJson();
			
			JsonNode updateShoppingItemJson = json.findPath("update");
			JsonNode userJson = updateShoppingItemJson.findPath("user");
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}

			int shoppingListId = updateShoppingItemJson.get("shopping_list_id").intValue();

			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();

			if(shoppingList == null) {
				return ok(Json.toJson("error: unauthorized shopping list"));
			}
			
			JsonNode itemsNode = updateShoppingItemJson.get("items");
			if(itemsNode.isArray()) {
				for(JsonNode itemNode : itemsNode) {
					int itemId = itemNode.get("id").intValue();
					
					int newItemId = itemNode.get("item_id").intValue();
					int newLocationId = itemNode.get("location_id").intValue();
					String newName = itemNode.get("name").textValue();
					String newDescription = itemNode.get("description").textValue();
					Float newPrice = itemNode.get("price").floatValue();
					
					UserShoppingListItem item = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingListId)
							.eq("id", itemId).findUnique();
					
					if(item == null) {
						return ok(Json.toJson("invalid item id: " + itemId));
					}
					if(newItemId != 0) item.item.id = newItemId;
					if(newLocationId != 0) item.location.id = newLocationId;
					item.name = newName;
					item.description = newDescription;
					item.price = newPrice;
					
					item.update();
				}
			}

			UserShoppingList.find.where().eq("id", shoppingListId).findUnique().update();

			return ok(Json.toJson("updated"));
		}
		catch(Exception e) {
			System.out.println("UpdateShoppingListItem Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result deleteShoppingListItems() {
		try {
			/* {
				  "delete": {
				    "user": {
				      "user_id": 1,
				      "session_str": "asdf"
				    },
				    "shopping_list_id": 1,
				    "item_ids": [
				      1,
				      2
				    ]
				  }
				}
			} */
					
			JsonNode json = request().body().asJson();
			
			JsonNode deleteShoppingItemJson = json.findPath("delete");
			JsonNode userJson = deleteShoppingItemJson.findPath("user");
			int userId = userJson.get("user_id").intValue();
			String sessionStr = userJson.get("session_str").textValue();
			
			models.User user = models.User.find.where().eq("id", userId).eq("sessionStr", sessionStr).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: unauthorized action"));
			}
			
			int shoppingListId = deleteShoppingItemJson.get("shopping_list_id").intValue();
			
			UserShoppingList shoppingList = UserShoppingList.find.where().eq("id", shoppingListId).eq("user_id", user.id).findUnique();

			if(shoppingList == null) {
				return ok(Json.toJson("error: unauthorized shopping list"));
			}
			
			JsonNode itemIdsNode = deleteShoppingItemJson.get("item_ids");
			if(itemIdsNode.isArray()) {
				for(JsonNode itemIdNode : itemIdsNode) {
					int itemId = itemIdNode.intValue();
					UserShoppingListItem item = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingListId).eq("id", itemId).findUnique();
					if(item == null) {
						return ok(Json.toJson("invalid item id: " + itemId));
					}
					item.delete();
				}
			}
			
			UserShoppingList.find.where().eq("id", shoppingListId).findUnique().update();
			
			return ok(Json.toJson("deleted"));
		}
		catch(Exception e) {
			System.out.println("DeleteShoppingListItem Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
}
