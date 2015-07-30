package com.fydp.sci.grocerything.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//TODO inprogress.
public class GetShoppingListsAsyncTask extends AbstractShoppingListAsyncTask {

    @Override
    protected String getUrlTail() {
        return "/user/getShoppingLists";
    }

    @Override
    protected JSONObject getJSONParams() {
        return JSONHelper.generateGetShoppingListsJSON();
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
