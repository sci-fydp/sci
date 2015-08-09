package com.fydp.sci.grocerything;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fydp.sci.grocerything.DataModel.Grocery;
import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.NetworkUtils.NetworkUtils;
import com.fydp.sci.grocerything.NetworkUtils.SearchItemsAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class SearchActivity extends Activity implements SearchItemsAsyncTask.SearchListener, Model.ModelSaveShoppingListListener {
    ListView groceryListView;
    ShoppingList theShoppingList;
    ArrayList<Purchase> purchases;
    StableArrayAdapter groceryListAdapter;
    AutoCompleteTextView searchTextView;
    RelativeLayout mainLayout;
    ArrayAdapter<Grocery> groceryNameAdapter;

    private static final String SHOPPING_LIST_NAME_KEY = "LISTNAMEKEY";
    private static final String NEW_SHOPPING_LIST_KEY = "NEWKEY";
    private static final int SEARCH_DROP_DOWN_HEIGHT = 300;
    int searchTerrorTag = 0; // this is terror.
    HashSet<Grocery> savedGroceries = new HashSet<Grocery>();//Real terror.
    private String shoppingListName;
    private boolean isNew;
    private ProgressDialog progressDialog;
	ArrayList<Purchase> additionalPurchases = new ArrayList<Purchase>();
	ArrayList<Purchase> deletedPurchases = new ArrayList<Purchase>();
			
    //TODO STICK EXISTING GROCERIES IN?.... OR VIA MODEL..... hmm.....
    public static Intent createIntent(Context context, String name)
    {
        Intent intent = new Intent(context, SearchActivity.class);
        Bundle b = new Bundle();
        b.putBoolean(NEW_SHOPPING_LIST_KEY, true);
        b.putString(SHOPPING_LIST_NAME_KEY, name);
        intent.putExtras(b);
        return intent;
    }

    public static Intent createIntent(Context context, ShoppingList list, ArrayList<Purchase> purchases)
    {
        //FIXME
        //Yea so uh.... i dont remember how to pass these special objs as bundles.... something about creatorstuff, so temporary hack here.

        Intent intent = new Intent(context, SearchActivity.class);
        Bundle b = new Bundle();
        b.putBoolean(NEW_SHOPPING_LIST_KEY, false);
        b.putString(SHOPPING_LIST_NAME_KEY, list.getName());
        intent.putExtras(b);

        Model.getInstance().FIXMEHack(list, purchases);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle b = getIntent().getExtras();
        this.shoppingListName = b.getString(SHOPPING_LIST_NAME_KEY);
        this.isNew = b.getBoolean(NEW_SHOPPING_LIST_KEY);

        purchases = new ArrayList<Purchase>();
        if (!isNew)
        {
            theShoppingList = Model.getInstance().FIXMEList();
            purchases.addAll(Model.getInstance().FIXMEPurchase());
        }
        else
        {
            //hmm...... do i really need that number lol.
            theShoppingList = new ShoppingList(shoppingListName, 3589083);
        }

        setTitle(shoppingListName);
        init();
        groceryListAdapter = new StableArrayAdapter(this,
                R.layout.shopping_list_row, purchases);

        groceryListView.setAdapter(groceryListAdapter);
        mainLayout = (RelativeLayout) findViewById(R.id.search_mainLayout);


        //final ArrayList<String> groceryNames = getGroceryNames();
        groceryNameAdapter = new ArrayAdapter<Grocery>(this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<Grocery>());


        searchTextView.setAdapter(groceryNameAdapter);
        searchTextView.setDropDownHeight(SEARCH_DROP_DOWN_HEIGHT);
        searchTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Grocery selection = (Grocery) parent.getItemAtPosition(position);
				Purchase purchase = new Purchase(selection);
                purchases.add(purchase);
				
				if (!isNew)
				{
					additionalPurchases.add(purchase);
				}
				
                groceryListAdapter.notifyDataSetChanged();
                Log.d("SEARCHACITITY", "SELECTED! " + selection);
                searchTextView.setText("");
                //searchTextView.clearFocus();
                mainLayout.requestFocus();
            }

        });

        searchTextView.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                searchTerrorTag++;
                //FIXME lets test only on 3., uhhh this only updates & appears on 4, may be problem zzz.
                if (str.trim().length() == 3)
                {
                    NetworkUtils.getInstance().findGroceryNames(str, searchTerrorTag, SearchActivity.this);
                }
            }
        });
    }

    private void init()
    {
        groceryListView = (ListView)findViewById(R.id.search_groceryList);

        searchTextView = (AutoCompleteTextView)findViewById(R.id.search_groceryInput);
    }


    private class StableArrayAdapter extends ArrayAdapter<Purchase> {


        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Purchase> objects) {
            super(context, textViewResourceId, objects);

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            // View rowView = convertView;
            final ShoppingListItemViewHolder viewHolder;

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                convertView  = inflater.inflate(R.layout.shopping_list_row, parent, false);

                viewHolder = new ShoppingListItemViewHolder();

                viewHolder.checkboxImage=(CheckBox)convertView.findViewById(R.id.shopping_list_row_checkBox);
                viewHolder.nameText=(TextView)convertView.findViewById(R.id.shopping_list_row_text);
                viewHolder.nextImage=(ImageView)convertView.findViewById(R.id.shopping_list_row_arrow);
				
				final int purchasePosition = position;
				
				viewHolder.deleteButton =(Button)convertView.findViewById(R.id.shopping_list_row_delete_button);
				viewHolder.deleteButton.setOnClickListener(new View.OnClickListener()
				{
                    @Override
					public void onClick(View v)
					{
                        Purchase purchase = purchases.get(purchasePosition);
						//.... some are you sure dialog.
						
						//add to deleted purchases if not new
						if (!isNew)
						{
							deletedPurchases.add(purchase);
						}


                        purchases.remove(purchasePosition);
                        groceryListAdapter.notifyDataSetChanged();
					}
				});



                convertView.setTag(viewHolder);

            }

            else{
                viewHolder= (ShoppingListItemViewHolder)convertView.getTag();
            }

            viewHolder.nameText.setText(purchases.get(position).getName());

            return convertView;

        } 


    }

    static class ShoppingListItemViewHolder
    {
        public TextView nameText;
        public ImageView nextImage;
        public CheckBox checkboxImage;
		public Button deleteButton;
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
        if (id == R.id.action_shoppingList_save) {
            saveToServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Search Async task
    @Override
    public void searchComplete(ArrayList<Grocery> newGroceries, int tag) {
        //searchTerrorTag == tag &&
        if (newGroceries != null)
        {
            //Apparently dupes will appear. if not a set.
            savedGroceries.addAll(newGroceries);
            groceryNameAdapter.clear();
            groceryNameAdapter.addAll(savedGroceries);

            groceryNameAdapter.notifyDataSetChanged();
        }
    }

    private void saveToServer()
    {
        progressDialog = ProgressDialog.show(SearchActivity.this, "Saving",
                "Generic Saving Message", true);

        Model.getInstance().saveShoppingList(this, theShoppingList, isNew, purchases, additionalPurchases, deletedPurchases);
    }

    //Model Response
    @Override
    public void success(ShoppingList list) {
        progressDialog.dismiss();
        theShoppingList = list;
        isNew = false;

        Bundle b = getIntent().getExtras();
        b.putBoolean(NEW_SHOPPING_LIST_KEY, false);
    }

    @Override
    public void failure(String reason) {
        progressDialog.dismiss();
    }
}
