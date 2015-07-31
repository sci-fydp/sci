package com.fydp.sci.grocerything.NetworkUtils;

import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

//TODO inprogress.
public class NewShoppingListAsyncTask extends AbstractShoppingListAsyncTask {

    private String listName = "GenericListName";
    public void setShoppingTitleName(String s)
    {
        listName = s;
    }
    @Override
    protected String getUrlTail() {
        return "/user/saveShoppingList";
    }

    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateSaveShoppingListsJSON(listName);
    }

    @Override
    protected Object processResponse(String response) {
        ShoppingList list = JSONHelper.parseShoppingList(response);
        return list;
    }

    @Override
    protected String processFailure(String response) {
        return "Error: SaveShoppingList";
    }
}
