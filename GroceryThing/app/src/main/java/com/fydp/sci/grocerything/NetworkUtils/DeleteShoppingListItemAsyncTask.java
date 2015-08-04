package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

import java.util.List;

public class DeleteShoppingListItemAsyncTask extends AbstractShoppingListAsyncTask {

    List<Purchase> purchases;
    ShoppingList shopList;
    @Override
    protected String getUrlTail() {
        return "/user/deleteShoppingListItems";
    }

    public void setParams(ShoppingList shopList, List<Purchase> purchases)
    {
        this.shopList = shopList;
        this.purchases = purchases;
    }
    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateDeleteShoppingListItemJSON(shopList, purchases);
    }

    @Override
    protected Object processResponse(String response) {
        return response;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: SaveShoppingList";
    }
}
