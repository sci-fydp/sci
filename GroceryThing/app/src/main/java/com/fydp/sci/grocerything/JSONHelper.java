package com.fydp.sci.grocerything;


import android.provider.Settings;

import com.fydp.sci.grocerything.DataModel.Grocery;
import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.DataModel.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    public static final String JKEY_ITEM_NAME = "name";
    public static final String JKEY_ITEM_ID = "id";
    public static final String JKEY_REGISTER = "register";
    public static final String JKEY_REGISTER_EMAIL = "email";
    public static final String JKEY_REGISTER_PASS = "password";
    public static final String JKEY_REGISTER_UNIQUE_ID = "udid";

    public static final String JKEY_LOGIN = "login";
    public static final String JKEY_LOGIN_EMAIL = "email";
    public static final String JKEY_LOGIN_PASS = "password";
    public static final String JKEY_LOGIN_UNIQUE_ID = "udid";

    public static final String JKEY_GET_SHOPPING_LISTS_KEY = "getShoppingLists";

    public static final String JKEY_USER_ID = "user_id";
    public static final String JKEY_USER_SESSION = "sessionStr";
    public static final String JKEY_USER_OBJ_KEY = "user";
    public static final String JKEY_SAVE_KEY = "save";
    public static final String JKEY_DELETE_KEY = "delete";
    public static final String JKEY_UPDATE_KEY = "update";
    public static final String JKEY_SHOPPING_LIST_NAME_KEY = "name";

    public static  ArrayList<Grocery> parseGroceryItems(JSONArray mainArray)
    {
        ArrayList<Grocery> groceries = new ArrayList<Grocery>();
        try {
            for (int i = 0; i < mainArray.length(); i++) {

                JSONObject itemObj = mainArray.getJSONObject(i);
                String name = itemObj.getString(JSONHelper.JKEY_ITEM_NAME);
                int id = itemObj.getInt(JSONHelper.JKEY_ITEM_ID);
                String desc = itemObj.getString("description");
                //strs.add(name);
                Grocery grocery = new Grocery(name, id, desc);
                groceries.add(grocery);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return groceries;
    }

    public static JSONObject generateRegistrationJSON(String email, String password)
    {
        JSONObject mainObj = new JSONObject();
        JSONObject registerObj = new JSONObject();
        try
        {
            registerObj.put(JKEY_REGISTER_EMAIL, email);
            registerObj.put(JKEY_REGISTER_PASS, password);
            //TODO get UUID
            //http://android-developers.blogspot.ca/2011/03/identifying-app-installations.html
            //http://stackoverflow.com/questions/5088474/how-can-i-get-the-uuid-of-my-android-phone-in-an-application
            registerObj.put(JKEY_REGISTER_UNIQUE_ID, Settings.Secure.ANDROID_ID);
            registerObj.put("avoidlist", ""); //??????
            mainObj.put(JKEY_REGISTER, registerObj);

        }
        catch(Exception e)
        {

        }
        return mainObj;
    }

    public static JSONObject generateLoginJSON(String email, String password)
    {
        JSONObject mainObj = new JSONObject();
        JSONObject registerObj = new JSONObject();
        try
        {
            registerObj.put(JKEY_LOGIN_EMAIL, email);
            registerObj.put(JKEY_LOGIN_PASS, password);
            //TODO get UUID
            //http://android-developers.blogspot.ca/2011/03/identifying-app-installations.html
            //http://stackoverflow.com/questions/5088474/how-can-i-get-the-uuid-of-my-android-phone-in-an-application
            registerObj.put(JKEY_LOGIN_UNIQUE_ID, Settings.Secure.ANDROID_ID);
            mainObj.put(JKEY_LOGIN, registerObj);

        }
        catch(Exception e)
        {

        }
        return mainObj;
    }

    public static JSONObject generateGetShoppingListsJSON()
    {
        JSONObject getShopListObj = new JSONObject();
        try
        {
            getShopListObj.put(JKEY_GET_SHOPPING_LISTS_KEY, getUserDataJSON());

        }
        catch(Exception e)
        {

        }
        return getShopListObj;
    }

    public static JSONObject generateSaveShoppingListsJSON(String shopListName)
    {
        JSONObject saveShopListObj = new JSONObject();
        try
        {
            JSONObject userObj = getUserDataJSON();
            userObj.put(JKEY_SHOPPING_LIST_NAME_KEY, shopListName);
            saveShopListObj.put(JKEY_SAVE_KEY, userObj);
           // saveShopListObj.put(JKEY_SHOPPING_LIST_NAME_KEY, shopListName);

        }
        catch(Exception e)
        {

        }
        return saveShopListObj;
    }

    public static JSONObject generateUpdateShoppingListsJSON(String shopListName, int shopListID)
    {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put(JKEY_UPDATE_KEY, getUserDataJSON());
            obj.put("id", shopListID);
            obj.put(JKEY_SHOPPING_LIST_NAME_KEY, shopListName);
        }
        catch(Exception e)
        {

        }
        return obj;
    }


    public static JSONObject generateDeleteShoppingListsJSON(int shopListID)
    {
        JSONObject obj = new JSONObject();
        try
        {
            JSONObject userObj = getUserDataJSON();
            userObj.put("id", shopListID);
            obj.put(JKEY_DELETE_KEY, userObj);
        }
        catch(Exception e)
        {

        }
        return obj;
    }


    public static JSONObject generateSaveShoppingListItemJSON(ShoppingList shopList, List<Purchase> purchases)
    {
        JSONObject mainObj = new JSONObject();
        try
        {
            JSONObject obj = getUserDataJSON();


            obj.put("shopping_list_id", shopList.getId());


            JSONArray jPurchaseArray = new JSONArray();

            for (Purchase purchase : purchases)
            {
                jPurchaseArray.put(getPurchaseJSON(purchase));
            }

            obj.put("items", jPurchaseArray);

            mainObj.put(JKEY_SAVE_KEY, obj);

        }
        catch(Exception e)
        {

        }
        return mainObj;
    }

    private static JSONObject getPurchaseJSON(Purchase purchase)
    {
        try {
            JSONObject obj = new JSONObject();
            obj.put("item_id", purchase.getId());
            obj.put("location_id", purchase.getLocationId()); //TODO FIXME ????????????????
            obj.put("name", purchase.getName());
            obj.put("description", purchase.getDescription());
            obj.put("price", purchase.getPrice());

            return obj;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getUserDataJSON ()
    {
        JSONObject obj = new JSONObject();
        JSONObject userObj = new JSONObject();
        try
        {
            obj.put(JKEY_USER_ID, Model.getInstance().getUserId());
            obj.put("session_str", Model.getInstance().getSessionKey());
            userObj.put(JKEY_USER_OBJ_KEY, obj);
        }
        catch(Exception e)
        {

        }

        return userObj;
    }

    public static UserSession parseUserLoginJSON(String jsonString)
    {
        UserSession session = null;
        try
        {
            JSONObject obj = new JSONObject(jsonString);
            String sessionRef = obj.getString(JKEY_USER_SESSION);
            int intRef = obj.getInt("id");

            session = new UserSession(sessionRef, intRef);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return session;
    }

    public static ShoppingList parseShoppingList(String jsonString)
    {
        try {
            JSONObject obj = new JSONObject(jsonString);
            return parseShoppingList(obj);
        }
        catch (Exception e)
        {

        }
        return null;
    }

    public static ArrayList<ShoppingList> parseAllShoppingLists(String response) {
        try
        {
            ArrayList<ShoppingList> lists = new ArrayList<ShoppingList>();

            JSONArray jListArray = new JSONArray(response);
            for (int i = 0; i < jListArray.length(); i++)
            {
                JSONObject shopListObj = jListArray.getJSONObject(i);
                ShoppingList list = parseShoppingList(shopListObj);
                lists.add(list);
            }
            return lists;
        }
        catch (Exception e) {

        }
        return null;
    }

    private static ShoppingList parseShoppingList(JSONObject shopListObj)
    {
        ShoppingList shoppingList = null;
        try
        {
            String name = shopListObj.getString("name");
            int id = shopListObj.getInt("id");

            shoppingList = new ShoppingList(name, id);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return shoppingList;
    }

    public static JSONObject generateGetShoppingListItemsJSON(ShoppingList l)
    {
        JSONObject obj = new JSONObject();
        try
        {
            JSONObject userObj = getUserDataJSON();
            userObj.put("shopping_list_id", l.getId());
            obj.put("get", userObj);
        }
        catch(Exception e)
        {

        }
        return obj;
    }

    public static List<Purchase> parseShoppingListPurchases(String response) {

        try
        {
            List<Purchase> purchases = new ArrayList<Purchase>();
            JSONArray jPurchaseArray =  new JSONArray(response);
            for (int i = 0; i < jPurchaseArray.length(); i++)
            {
                JSONObject itemObj = jPurchaseArray.getJSONObject(i);
                String name = itemObj.getString(JSONHelper.JKEY_ITEM_NAME);
                int id = itemObj.getInt(JSONHelper.JKEY_ITEM_ID);
                String desc = itemObj.getString("description");
                //strs.add(name);
                Grocery grocery = new Grocery(name, id, desc);
                double price = itemObj.getDouble("price");
                int locationId = 0;//itemObj.getInt("location"); //????? null????
                Purchase purchase = new Purchase(grocery, price);
                purchase.setLocationId(locationId);
                purchases.add(purchase);
            }
            return purchases;
        }
        catch(Exception e)
        {

        }
        return null;
    }
}
