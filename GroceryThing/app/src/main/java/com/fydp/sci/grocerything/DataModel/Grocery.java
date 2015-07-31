package com.fydp.sci.grocerything.DataModel;


public class Grocery {
    public String name;
    Grocery (String s)
    {
        name = s;
    }

    /*

    int shoppingListId = saveShoppingItemJson.get("shopping_list_id").intValue();
			int itemId = saveShoppingItemJson.get("item_id").intValue();
			int locationId = saveShoppingItemJson.get("location_id").intValue();
			String name = saveShoppingItemJson.get("name").toString();
			String description = saveShoppingItemJson.get("description").toString();
			Float price = saveShoppingItemJson.get("price").floatValue();


     */

    @Override
    public String toString()
    {
        return name;
    }
}
