package com.fydp.sci.grocerything.NetworkUtils;


public class NetworkUtils {

    private static NetworkUtils INSTANCE = null;

    public static NetworkUtils getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new NetworkUtils();
        }
        return INSTANCE;
    }
    public void register(String emailAddress, String password, AccRegistrationAsyncTask.AccRegistrationListener listener)
    {
        AccRegistrationAsyncTask task = new AccRegistrationAsyncTask();
        task.setListener(listener);
        task.setRegistrationDetails(emailAddress, password);
        task.execute();
    }

    public void findGroceryNames(String likeStr, int tag, SearchItemsAsyncTask.SearchListener lis)
    {
        SearchItemsAsyncTask task = new SearchItemsAsyncTask();
        task.setListener(lis);
        task.setDetails(likeStr, tag);
        task.execute();
    }

}
