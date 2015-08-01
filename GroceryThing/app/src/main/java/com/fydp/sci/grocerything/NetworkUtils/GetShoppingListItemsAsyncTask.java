package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

import java.util.ArrayList;

//TODO inprogress.
public class GetShoppingListItemsAsyncTask extends AbstractShoppingListAsyncTask {

    ShoppingList shopList;
    @Override
    protected String getUrlTail() {
        return "/user/getShoppingListItems";
    }

    public void setParams(ShoppingList shopList)
    {
        this.shopList = shopList;
    }

    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateGetShoppingListItemsJSON(shopList);
    }

    @Override
    protected Object processResponse(String response) {
        
        //TODO PARSE JSON  of user shopping items!
        return null;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: GetShoppingList";
    }
}
