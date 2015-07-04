package com.fydp.sci.grocerything;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends Activity {
    ListView groceryListView;
    ArrayList<Grocery> groceries;
    StableArrayAdapter groceryListAdapter;
    AutoCompleteTextView searchTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        createRandomData();
        groceryListAdapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, groceries);


        ArrayList<String> groceryNames = getGroceryNames();
        ArrayAdapter<String> groceryNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, groceryNames);

        searchTextView.setAdapter(groceryNameAdapter);
        groceryListView.setAdapter(groceryListAdapter);
    }

    private ArrayList<String> getGroceryNames()
    {
        ArrayList<String> groceries = new ArrayList<String>();
        groceries.add("Drugs");
        groceries.add("More Drug");
        groceries.add("Apple");
        //TODO get from model.
        return groceries;
    }
    private void init()
    {
        groceryListView = (ListView)findViewById(R.id.search_groceryList);

        searchTextView = (AutoCompleteTextView)findViewById(R.id.search_groceryInput);
    }

    private void createRandomData() {
        groceries = new ArrayList<Grocery>();
        groceries.add(new Grocery("Hello"));
        groceries.add(new Grocery("Grocery2"));
        groceries.add(new Grocery("Drugs"));
    }

    private class StableArrayAdapter extends ArrayAdapter<Grocery> {

        HashMap<Grocery, Integer> mIdMap = new HashMap<Grocery, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Grocery> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            Grocery item = getItem(position);
            return mIdMap.get(item);
        }

    }
}
