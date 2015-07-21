package com.fydp.sci.grocerything;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONHelper {
    public static final String JKEY_ITEM_NAME = "name";
    public static final String JKEY_REGISTER = "register";
    public static final String JKEY_REGISTER_EMAIL = "email";
    public static final String JKEY_REGISTER_PASS = "password";

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
            mainObj.put(JKEY_REGISTER, registerObj);
        }
        catch(Exception e)
        {

        }
        return mainObj;
    }
}
