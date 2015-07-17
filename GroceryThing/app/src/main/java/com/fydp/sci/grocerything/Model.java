package com.fydp.sci.grocerything;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Model {

    private static Model INSTANCE = null;
    private ArrayList<String> groceryNames;

    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
            INSTANCE.init();
        }
        return INSTANCE;
    }

    public void init()
    {
        findGroceryNames();
    }

    public void findGroceryNames()
    {
        LongOperation op = new LongOperation();
        op.execute();
    }
    public ArrayList<String> getGroceriesSearchable() {

        return groceryNames;
    }


    private class LongOperation extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {

            ArrayList<String> ans = new ArrayList<String>();
              HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL("http://10.0.3.2:9000/search/allItems");
                    urlConnection = (HttpURLConnection) url.openConnection();
                   // urlConnection.setRequestMethod("GET");
                    String jsonString = readStream(urlConnection.getInputStream());
                    JSONArray mainObj = new JSONArray(jsonString);
                    ans = parseItemNames(mainObj);
                    Log.d("EHH", "EHEHEH");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    if (urlConnection != null)
                         urlConnection.disconnect();
                }
                Log.d("EHH", "WHY ARE WE DONE");
                return ans;
//            return returned;
        }
        private ArrayList<String> parseItemNames(JSONArray mainArray)
        {
            ArrayList<String> strs = new ArrayList<String>();
            try {
                for (int i = 0; i < mainArray.length(); i++) {
                    JSONObject itemObj = mainArray.getJSONObject(i);
                    String name = itemObj.getString(JSONKeys.JKEY_ITEM_NAME);
                    strs.add(name);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            return strs;
        }
        private String readStream(InputStream in) {
            String result = null;
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(in, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                in.close();
                result = sBuilder.toString();

            } catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            groceryNames = result;
        }
    }
}
