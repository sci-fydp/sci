package com.fydp.sci.grocerything;


import android.provider.Settings;

import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.DataModel.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHelper {
    public static final String JKEY_ITEM_NAME = "name";
    public static final String JKEY_REGISTER = "register";
    public static final String JKEY_REGISTER_EMAIL = "email";
    public static final String JKEY_REGISTER_PASS = "password";
    public static final String JKEY_REGISTER_UNIQUE_ID = "udid";

    public static final String JKEY_LOGIN = "login";
    public static final String JKEY_LOGIN_EMAIL = "email";
    public static final String JKEY_LOGIN_PASS = "password";
    public static final String JKEY_LOGIN_UNIQUE_ID = "udid";

    public static final String JKEY_GET_KEY = "get";

    public static final String JKEY_USER_ID = "user_id";
    public static final String JKEY_USER_SESSION = "sessionStr";
    public static final String JKEY_USER_OBJ_KEY = "user";
    public static final String JKEY_SAVE_KEY = "save";
    public static final String JKEY_DELETE_KEY = "delete";
    public static final String JKEY_UPDATE_KEY = "update";
    public static final String JKEY_SHOPPING_LIST_NAME_KEY = "name";

    public static  ArrayList<String> parseItemNames(JSONArray mainArray)
    {
        ArrayList<String> strs = new ArrayList<String>();
        try {
            for (int i = 0; i < mainArray.length(); i++) {
                JSONObject itemObj = mainArray.getJSONObject(i);
                String name = itemObj.getString(JSONHelper.JKEY_ITEM_NAME);
                strs.add(name);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return strs;
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
            getShopListObj.put(JKEY_GET_KEY, getUserDataJSON());

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
            obj.put("id", shopListID);//TODO
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
            obj.put(JKEY_DELETE_KEY, getUserDataJSON());
            obj.put("id", shopListID);//TODO
        }
        catch(Exception e)
        {

        }
        return obj;
    }


    public static JSONObject generateSaveShoppingListItemJSON(int shopListID, int itemID, int locID, String name, String desc, double price)
    {
        JSONObject obj = new JSONObject();
        try
        {
            obj.put(JKEY_SAVE_KEY, getUserDataJSON());
            obj.put("id", shopListID);//TODO
            obj.put("shopping_list_id",shopListID);
            obj.put("item_id", itemID);
            obj.put("location_id", locID);
            obj.put("name", name);
            obj.put("description", desc);
            obj.put("price", price);

        }
        catch(Exception e)
        {

        }
        return obj;
    }

    private static JSONObject getUserDataJSON ()
    {
        JSONObject obj = new JSONObject();
        JSONObject userObj = new JSONObject();
        try
        {
            obj.put(JKEY_USER_ID, Model.getInstance().getUserId());
            obj.put("session", Model.getInstance().getSessionKey());
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
}
