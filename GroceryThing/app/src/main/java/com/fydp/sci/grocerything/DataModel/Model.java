package com.fydp.sci.grocerything.DataModel;


import android.os.AsyncTask;
import android.util.Log;

import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Model {

    private static Model INSTANCE = null;
    private UserSession userSession;


    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }
    public void loginSuccess(String session)
    {
        userSession = new UserSession(session);
    }
}
