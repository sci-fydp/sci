package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.DataModel.Grocery;
import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

import java.util.List;

//TODO inprogress.

//OK i'm just going to make this do everything nvm, too hard
public class SaveShoppingListItemAsyncTask extends AbstractShoppingListAsyncTask {

    List<Purchase> purchases;
    ShoppingList shopList;
    @Override
    protected String getUrlTail() {
        return "/user/saveShoppingListItems";
    }

    public void setParams(ShoppingList shopList, List<Purchase> purchases)
    {
        this.shopList = shopList;
        this.purchases = purchases;
        //TODO
    }
    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateSaveShoppingListItemJSON(shopList, purchases);
        //TODO
    }

    @Override
    protected Object processResponse(String response) {
        //Parse result
        //TODO
        return response;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: SaveShoppingList";
    }
}
