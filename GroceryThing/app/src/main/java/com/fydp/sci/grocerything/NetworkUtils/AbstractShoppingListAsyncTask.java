package com.fydp.sci.grocerything.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public abstract class AbstractShoppingListAsyncTask extends AsyncTask<Void, Void, Void> {

    protected ArrayList<ShoppingListTaskListener> listeners = new ArrayList<ShoppingListTaskListener>();
    protected boolean success = false;
    protected Object savedResult;

    public interface ShoppingListTaskListener
    {
        void success(AbstractShoppingListAsyncTask task, Object obj);
        void failure(AbstractShoppingListAsyncTask task, String reason);
    }

    public void addListener (ShoppingListTaskListener l)
    {
        listeners.add(l);
    }
    abstract protected String getUrlTail();
    abstract protected JSONObject getJSONParams();
    @Override
    protected Void doInBackground(Void... params) {

        String ans = new String();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(NetworkUtils.BASE_URL + getUrlTail());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = getJSONParams();
            DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
            byte[] data=jsonParam.toString().getBytes("UTF-8");
            printout.write(data);
            printout.flush();
            printout.close();

            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK)
            {

                ans = readStream( urlConnection.getInputStream());
                savedResult = processResponse(ans);
                if (savedResult == null) {
                    savedResult = processFailure(ans);
                }
                else
                    success = true;

            }else{
                processFailure(urlConnection.getResponseMessage());
                System.out.println(urlConnection.getResponseMessage());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
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
    protected void onPostExecute(Void result) {

        for (ShoppingListTaskListener listener : listeners)
        {
            if (success) {
                listener.success(this, savedResult);
            }
            else
            {
                if (savedResult == null)
                {
                    savedResult = "Something really went wrong";
                }
                listener.failure(this, (String)savedResult);
            }
        }
    }

    abstract protected Object processResponse(String response);
    abstract protected String processFailure(String response);
}
