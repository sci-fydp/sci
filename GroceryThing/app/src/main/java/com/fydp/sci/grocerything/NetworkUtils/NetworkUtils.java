package com.fydp.sci.grocerything.NetworkUtils;


public class NetworkUtils {

    private static NetworkUtils INSTANCE = null;
    public static String BASE_URL = "http://10.0.3.2:9000"; //GENYMOTION EMULATOR
    //public static String BASE_URL = "http://10.0.2.2:9000"; //ANDROID EMULATOR
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

    public void login(String emailAddress, String password, AccLoginAsyncTask.AccLoginListener listener)
    {
        AccLoginAsyncTask task = new AccLoginAsyncTask();
        task.setListener(listener);
        task.setLoginDetails(emailAddress, password);
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
