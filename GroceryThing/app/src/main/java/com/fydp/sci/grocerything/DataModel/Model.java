package com.fydp.sci.grocerything.DataModel;


import android.util.Log;

import com.fydp.sci.grocerything.NetworkUtils.AbstractShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.DeleteShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListItemsAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListsAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.NewShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.SaveShoppingListItemAsyncTask;
import com.fydp.sci.grocerything.UserHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public interface ModelGetShoppingListItemsListener
    {
        void success(ArrayList<Purchase> purchases, ShoppingList list);
        void failure(String reason);
    }

    public interface ModelGetShoppingListListener
    {
        void success(ArrayList<ShoppingList> list, boolean wasCached);
        void failure(String reason);
    }

    public interface ModelDeleteShoppingListListener
    {
        void success(String str, ShoppingList deletedList);
        void failure(String reason);
    }

    public interface ModelSaveShoppingListListener
    {
        void success(ShoppingList list);
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

    public void saveShoppingList(final ModelSaveShoppingListListener listener, ShoppingList shopList, boolean isNew, List<Purchase> purchases)
    {
        final List<Purchase> savePurchases = purchases;
        if (isNew)
        {
            NewShoppingListAsyncTask task = new NewShoppingListAsyncTask();
            task.setShoppingTitleName(shopList.getName());
            task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

                @Override
                public void success(AbstractShoppingListAsyncTask task, Object obj) {
                    ShoppingList list = (ShoppingList) obj;
                    if (savePurchases.size() == 0)
                    {
                        listener.success(list);
                    }
                    else
                    {
                        saveShoppingListGroceries(listener, list, savePurchases);
                    }
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
            saveShoppingListGroceries(listener, shopList, savePurchases);
        }
    }

    public void deleteShoppingList(final ModelDeleteShoppingListListener listener, ShoppingList shopList)
    {

        final ShoppingList savedList = shopList;
        DeleteShoppingListAsyncTask task = new DeleteShoppingListAsyncTask();
        task.setShoppingList(shopList);

        task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

            @Override
            public void success(AbstractShoppingListAsyncTask task, Object obj) {
                String str = (String) obj;
                listener.success(str, savedList);
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


    private void saveShoppingListGroceries(final ModelSaveShoppingListListener listener, ShoppingList list, List<Purchase> purchases)
    {

        final ShoppingList savedList = list;
        for (Purchase purchase : purchases)
        {
            SaveShoppingListItemAsyncTask task = new SaveShoppingListItemAsyncTask();

            task.setParams(list, purchase);
            task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

                @Override
                public void success(AbstractShoppingListAsyncTask task, Object obj) {
                    //String str = (String) obj;
                    listener.success(savedList);
                }

                @Override
                public void failure(AbstractShoppingListAsyncTask task, String reason) {
                    listener.failure(reason);
                }
            });

            task.execute();
        }
    }

    public void getShoppingListItems(final ModelGetShoppingListItemsListener listener, final ShoppingList list) {


        GetShoppingListItemsAsyncTask task = new GetShoppingListItemsAsyncTask();
        task.setParams(list);

        task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

            @Override
            public void success(AbstractShoppingListAsyncTask task, Object obj) {
                ArrayList<Purchase> purchases = (ArrayList<Purchase>) obj;
                listener.success(purchases, list);

            }

            @Override
            public void failure(AbstractShoppingListAsyncTask task, String reason) {
                listener.failure(reason);
            }
        });

        task.execute();
    }

    ShoppingList list;
    List<Purchase> purchases;
    public void FIXMEHack(ShoppingList list, List<Purchase> purchases)
    {
        this.list = list;
        this.purchases = purchases;
    }

    public List<Purchase> FIXMEPurchase()
    {
        return purchases;
    }

    public ShoppingList FIXMEList()
    {
        return list;
    }

}
