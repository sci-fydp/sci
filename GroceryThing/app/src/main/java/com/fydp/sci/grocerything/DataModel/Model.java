package com.fydp.sci.grocerything.DataModel;


import android.os.AsyncTask;
import android.util.Log;

import com.fydp.sci.grocerything.Grocery;
import com.fydp.sci.grocerything.JSONHelper;
import com.fydp.sci.grocerything.NetworkUtils.AbstractShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListsAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.NewShoppingListAsyncTask;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Model {

    public interface ModelGetShoppingListListener
    {
        void success(ArrayList<ShoppingList> list, boolean wasCached);
        void failure(String reason);
    }

    public interface ModelSaveShoppingListListener
    {
        void success(String str);
        void failure(String reason);
    }

    private static Model INSTANCE = null;
    private UserSession userSession;
    private ArrayList<ShoppingList> shoppingLists;

    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }
    public void loginSuccess(UserSession session)
    {
        userSession = session;
        Log.d("USER SESSION", "user" + userSession.getSessionKey());
    }

    public String getSessionKey()
    {
        if (userSession == null)
            return null;
        return userSession.getSessionKey();
    }
    public int getUserId()
    {
        if (userSession == null)
            return 0;
        return userSession.getUserId();
    }

    public void logout()
    {
        clearData();
    }

    private void clearData()
    {
        userSession = null;
        shoppingLists = null;
    }

    public void getUserShoppingLists(final ModelGetShoppingListListener listener, boolean requireFresh)
    {
        if (shoppingLists != null && !requireFresh)
        {
            listener.success(shoppingLists, true);
        }
        else
        {
            GetShoppingListsAsyncTask task = new GetShoppingListsAsyncTask();
            task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener()
            {

                @Override
                public void success(AbstractShoppingListAsyncTask task, Object obj) {
                    shoppingLists = (ArrayList<ShoppingList>)obj;
                    listener.success(shoppingLists, false);
                }

                @Override
                public void failure(AbstractShoppingListAsyncTask task, String reason) {
                    listener.failure(reason);
                }
            });
            task.execute();
        }
    }

    public void saveShoppingList(final ModelSaveShoppingListListener listener, String name, boolean isNew, List<Grocery> groceries)
    {
        if (isNew)
        {
            NewShoppingListAsyncTask task = new NewShoppingListAsyncTask();
            task.setShoppingTitleName(name);
            task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

                @Override
                public void success(AbstractShoppingListAsyncTask task, Object obj) {
                    String str = (String) obj;
                    listener.success(str);
                    //TODO
                    //saveShoppingListGroceries(listener);
                }

                @Override
                public void failure(AbstractShoppingListAsyncTask task, String reason) {
                    listener.failure(reason);
                }
            });

            task.execute();
        }
        else
        {
            //TODO
            //saveShoppingListGroceries(listener);
        }
    }

    /*
    private void saveShoppingListGroceries(final ModelSaveShoppingListListener listener, String name, int id, List<Grocery> groceries)
    {
        //TODO
            NewShoppingListAsyncTask task = new NewShoppingListAsyncTask();
            task.setShoppingTitleName(name);
            task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

                @Override
                public void success(AbstractShoppingListAsyncTask task, Object obj) {
                    String str = (String) obj;
                    listener.success(str);
                }

                @Override
                public void failure(AbstractShoppingListAsyncTask task, String reason) {
                    listener.failure(reason);
                }
            });

            task.execute();

    }*/
}
