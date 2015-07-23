package com.fydp.sci.grocerything.NetworkUtils;

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

public class SearchItemsAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    String likeStr;
    int tag;
    SearchListener listener;
    public interface SearchListener
    {
        void searchComplete(ArrayList<String> similarStrs,int tag);
    }


    public void setListener(SearchListener l)
    {
        listener = l;
    }

    public void setDetails(String like, int tag)
    {
        this.tag = tag;
        likeStr = like;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {

        ArrayList<String> ans = new ArrayList<String>();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://10.0.3.2:9000/search" + "?s=" + likeStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            // urlConnection.setRequestMethod("GET");
            String jsonString = readStream(urlConnection.getInputStream());
            JSONArray mainObj = new JSONArray(jsonString);
            ans = JSONHelper.parseItemNames(mainObj);
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

        listener.searchComplete(result, tag);
    }
}
