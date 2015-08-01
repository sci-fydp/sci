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

    Purchase purchase;
    ShoppingList shopList;
    @Override
    protected String getUrlTail() {
        return "/user/saveShoppingListItem";
    }

    public void setParams(ShoppingList shopList, Purchase purchase)
    {
        this.shopList = shopList;
        this.purchase = purchase;
        //TODO
    }
    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateSaveShoppingListItemJSON(shopList, purchase);
        //TODO
    }

    @Override
    protected Object processResponse(String response) {
        //Parse result
        //TODO
        return null;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: GetShoppingList";
    }
}
