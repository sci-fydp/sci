package logics;

import java.util.List;

import models.UserShoppingList;
import models.UserShoppingListItem;

public class EricLogic {
	
	public static List<UserShoppingListItem> generateItemsForUser() {
		models.User user = models.User.find.where().eq("id", 1).findUnique();
		
		List<UserShoppingList> shoppingLists = UserShoppingList.find.where().eq("user_id", user.id).findList();
		
		List<UserShoppingListItem> generatedItems = null;
		
		for(UserShoppingList shoppingList : shoppingLists) {
			List<UserShoppingListItem> shoppingListItems = UserShoppingListItem.find.where().eq("shopping_list_id", shoppingList.id).findList();
			
			
		}
		
		return generatedItems;
	}

}
