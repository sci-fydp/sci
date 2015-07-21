package com.fydp.sci.grocerything;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fydp.sci.grocerything.DataModel.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SearchActivity extends Activity {
    ListView groceryListView;
    ArrayList<Grocery> groceries;
    StableArrayAdapter groceryListAdapter;
    AutoCompleteTextView searchTextView;
    RelativeLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        createRandomData();
        groceryListAdapter = new StableArrayAdapter(this,
                R.layout.shopping_list_row, groceries);

        groceryListView.setAdapter(groceryListAdapter);
        mainLayout = (RelativeLayout) findViewById(R.id.search_mainLayout);


        final ArrayList<String> groceryNames = getGroceryNames();
        ArrayAdapter<String> groceryNameAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, groceryNames);
        searchTextView.setAdapter(groceryNameAdapter);
        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String) parent.getItemAtPosition(position);
                groceries.add(new Grocery(selection));
                groceryListAdapter.notifyDataSetChanged();
                Log.d("SEARCHACITITY", "SELECTED! " + selection);
                searchTextView.setText("");
                //searchTextView.clearFocus();
                mainLayout.requestFocus();
            }

        });
    }



    private ArrayList<String> getGroceryNames()
    {
       ArrayList<String> ans = Model.getInstance().getGroceriesSearchable();

       /* ArrayList<String> groceries = new ArrayList<String>();
        groceries.add("Drugs");
        groceries.add("More Drug");
        groceries.add("Apple");
        *///TODO get from model.
        return ans;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            // View rowView = convertView;
            final ShoppingListViewHolder viewHolder;

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                convertView  = inflater.inflate(R.layout.shopping_list_row, parent, false);

                viewHolder = new ShoppingListViewHolder();

                viewHolder.checkboxImage=(CheckBox)convertView.findViewById(R.id.shopping_list_row_checkBox);
                viewHolder.nameText=(TextView)convertView.findViewById(R.id.shopping_list_row_text);
                viewHolder.nextImage=(ImageView)convertView.findViewById(R.id.shopping_list_row_arrow);

             /*
                viewHolder.downloadImageButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("DOWNLOAD PRESSED");

                        viewHolder.downloadImageButton = (ImageView)v.findViewById(R.id.downloadImageButton);
                        viewHolder.downloadImageButton.setImageResource(R.drawable.icon_ok);
                        viewHolder.downloadImageButton.setTag("downloaded");
                        //rowView.setTag("downloaded");


                    }
                });
*/


                convertView.setTag(viewHolder);

            }

            else{
                viewHolder= (ShoppingListViewHolder)convertView.getTag();
            }

            viewHolder.nameText.setText(groceries.get(position).name);

            return convertView;

        } //close getView public View getView(int position, View convertView, ViewGroup parent) {


    }

    static class ShoppingListViewHolder
    {
        public TextView nameText;
        public ImageView nextImage;
        public CheckBox checkboxImage;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
