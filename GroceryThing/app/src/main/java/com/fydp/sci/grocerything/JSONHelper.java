package com.fydp.sci.grocerything;


import android.provider.Settings;

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
}
