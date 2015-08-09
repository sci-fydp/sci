package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

public class DeleteShoppingListAsyncTask extends AbstractShoppingListAsyncTask {

    private ShoppingList shopList;
    public void setShoppingList(ShoppingList list)
    {
        this.shopList = list;
    }
    @Override
    protected String getUrlTail() {
        return "/user/deleteShoppingList";
    }

    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateDeleteShoppingListsJSON(shopList.getId());
    }

    @Override
    protected Object processResponse(String response) {
        return response;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: DeleteShoppingList";
    }
}
