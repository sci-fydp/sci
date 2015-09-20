package com.fydp.sci.grocerything.DataModel;


import android.util.Log;

import com.fydp.sci.grocerything.NetworkUtils.AbstractShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.DeleteShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.DeleteShoppingListItemAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListItemsAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListsAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.NewShoppingListAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.SaveShoppingListItemAsyncTask;
import com.fydp.sci.grocerything.UserHomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Model {


    private interface IChainSave
    {
        void success();
        void failure(String s);
    }

    public interface ModelGenericListener
    {
        void success(String str);
        void failure(String reason);
    }

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

    public interface NewShoppingListObserver
    {
        void shoppingListAdded(ShoppingList s);
    }

    private static ArrayList<NewShoppingListObserver> newShoppingListObservers;
    private static Model INSTANCE = null;
    private UserSession userSession;
    private ArrayList<ShoppingList> shoppingLists;

    public static Model getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new Model();
            INSTANCE.newShoppingListObservers = new ArrayList<NewShoppingListObserver>();
        }
        return INSTANCE;
    }

    public void subscribeNewShoppingListObserver(NewShoppingListObserver obs) {
        if (!newShoppingListObservers.contains(obs))
            newShoppingListObservers.add(obs);
    }

    public void unsubscribeNewShoppingListObserver(NewShoppingListObserver obs) {
        newShoppingListObservers.remove(obs);
    }

    private void notifyNewShoppingList(ShoppingList l)
    {
        for (NewShoppingListObserver obs : newShoppingListObservers)
            obs.shoppingListAdded(l);
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

    public void saveShoppingList(final ModelSaveShoppingListListener listener, final ShoppingList shopList, boolean isNew, List<Purchase> purchases, final ArrayList<Purchase> additionalPurchases, ArrayList<Purchase> deletedPurchases)
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

                    notifyNewShoppingList(list);

                    saveShoppingListGroceries(listener, list, savePurchases);
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
            //FIXME is it possible to make this atomic?.... probably not..zzzzz
            deleteShoppingListGroceries(new IChainSave() {

                @Override
                public void success() {
                    saveShoppingListGroceries(listener, shopList, additionalPurchases);
                }

                @Override
                public void failure(String s) {
                    listener.failure(s);
                }
            },shopList, deletedPurchases);
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

        if (purchases.size() == 0)
        {
            listener.success(list);
            return;
        }

        final ShoppingList savedList = list;

        SaveShoppingListItemAsyncTask task = new SaveShoppingListItemAsyncTask();

        task.setParams(list, purchases);
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


    private void deleteShoppingListGroceries(final IChainSave listener, ShoppingList list, List<Purchase> purchases)
    {
        if (purchases.size() == 0)
        {
            listener.success();
            return;
        }
        final ShoppingList savedList = list;

        DeleteShoppingListItemAsyncTask task = new DeleteShoppingListItemAsyncTask();

        task.setParams(list, purchases);
        task.addListener(new AbstractShoppingListAsyncTask.ShoppingListTaskListener() {

            @Override
            public void success(AbstractShoppingListAsyncTask task, Object obj) {
                //String str = (String) obj;
                listener.success();
            }

            @Override
            public void failure(AbstractShoppingListAsyncTask task, String reason) {
                listener.failure(reason);
            }
        });

        task.execute();

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

    public void updateUserPushNotificationSettings(boolean accept,final ModelGenericListener listener) {

    }

    public void deleteAllShoppingLists(final ModelGenericListener listener) {

        /*
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

        task.execute();*/
    }


    //Temporary hack to pass existing shopping list to searchactivity.... think about how to fix later. zzz....
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
