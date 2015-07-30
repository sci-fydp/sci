package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

//TODO inprogress.
public class SaveShoppingListItemAsyncTask extends AbstractShoppingListAsyncTask {

    @Override
    protected String getUrlTail() {
        return "/user/saveShoppingListItem";
    }

    public void setParams(int shopListID)
    {
        //TODO
    }
    @Override
    protected JSONObject getJSONParams() {
        //return JSONHelper.gene
        return null;
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
