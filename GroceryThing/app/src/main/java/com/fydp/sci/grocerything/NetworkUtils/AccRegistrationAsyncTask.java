package com.fydp.sci.grocerything.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

//TODO inprogress.
public class AccRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

    AccRegistrationListener listener = null;
    String email;
    String pass;
    boolean success = false;
    public interface AccRegistrationListener
    {
        void registrationSuccess(String session);
        void registrationFailure(String reason);
    }

    public void setListener (AccRegistrationListener l)
    {
        listener = l;
    }
    public void setRegistrationDetails(String email, String pass)
    {
        this.email = email;
        this.pass = pass;
    }
    @Override
    protected String doInBackground(Void... params) {

        String ans = new String();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL("http://10.0.3.2:9000/user/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = JSONHelper.generateRegistrationJSON(email,pass);
            DataOutputStream printout = new DataOutputStream(urlConnection.getOutputStream ());
            byte[] data=jsonParam.toString().getBytes("UTF-8");
            printout.write(data);
            printout.flush();
            printout.close();

            StringBuilder sb = new StringBuilder();
            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK)
            {

                ans = readStream( urlConnection.getInputStream());
                Log.d("ANS", ans);
                success = true;

            }else{
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
        return ans;
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
    protected void onPostExecute(String result) {
        if (listener != null)
        {
            if (success) {
                listener.registrationSuccess(result);
            }
            else
            {
                if (result == null)
                {
                    result = "Something really went wrong";
                }
                listener.registrationFailure(result);
            }
        }
        else
        {
            Log.d("AccRegistrationTask", "wat..");            
        }

    }
}
