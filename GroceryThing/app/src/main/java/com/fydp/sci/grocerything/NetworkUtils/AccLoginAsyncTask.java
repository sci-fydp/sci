package com.fydp.sci.grocerything.NetworkUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.DataModel.UserSession;
import com.fydp.sci.grocerything.JSONHelper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccLoginAsyncTask extends AsyncTask<Void, Void, String> {

    AccLoginListener listener = null;
    String email;
    String pass;
    boolean success = false;
    public interface AccLoginListener
    {
        void loginSuccess(String msg);
        void loginFailure(String reason);
    }

    public void setListener (AccLoginListener l)
    {
        listener = l;
    }
    public void setLoginDetails(String email, String pass)
    {
        this.email = email;
        this.pass = pass;
    }
    @Override
    protected String doInBackground(Void... params) {

        String ans = new String();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(NetworkUtils.BASE_URL + "/user/login");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            JSONObject jsonParam = JSONHelper.generateLoginJSON(email,pass);
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
                UserSession sessionObj = JSONHelper.parseUserLoginJSON(ans);
                if (sessionObj == null)
                {
                    //???? error.
                }
                else
                {
                    Model.getInstance().loginSuccess(sessionObj);
                    success = true;
                }

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
                listener.loginSuccess("good... good...");
            }
            else
            {
                if (result == null)
                {
                    result = "Something really went wrong";
                }
                listener.loginFailure(result);
            }
        }
        else
        {
            Log.d("AccLoginTask", "wat..");
        }

    }
}
