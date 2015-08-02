package com.fydp.sci.grocerything;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.DataModel.Purchase;
import com.fydp.sci.grocerything.DataModel.ShoppingList;
import com.fydp.sci.grocerything.NetworkUtils.GetShoppingListsAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserHomeActivity extends Activity implements Model.ModelGetShoppingListListener, Model.ModelDeleteShoppingListListener, Model.ModelGetShoppingListItemsListener {
    ListView historyListView;
    ArrayList<ShoppingList> shoppingLists;
    StableArrayAdapter historyListAdapter;
    Button newListButton, reloadListButton;
    ProgressDialog progressDialog;
    //Button viewListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        init();
        
    }

    private void init()
    {
        reloadListButton = (Button)findViewById(R.id.userHome_reloadListButton);
        reloadListButton.setOnClickListener(new View.OnClickListener() {

                                             @Override
                                             public void onClick(View v) {

                                                 progressDialog = ProgressDialog.show(UserHomeActivity.this, "Fetching Data",
                                                         "Generic Processing Message", true);

                                                 Model.getInstance().getUserShoppingLists(UserHomeActivity.this, false);
                                             }
                                         }

        );
        newListButton = (Button)findViewById(R.id.userHome_newListButton);
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserHomeActivity.this);
                builder.setTitle("Title");

                final EditText input = new EditText(UserHomeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //TODO PASS groceries in.
                        Intent intent = SearchActivity.createIntent(UserHomeActivity.this, input.getText().toString());
                        finish();
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                //Log.d("Main", "Hello Login attempt");
                //Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });


        shoppingLists = new ArrayList<ShoppingList>();
        progressDialog = ProgressDialog.show(UserHomeActivity.this, "Fetching Data",
                "Generic Processing Message", true);

        Model.getInstance().getUserShoppingLists(this, false);
        historyListView = (ListView)findViewById(R.id.userHome_savedList);
        historyListAdapter = new StableArrayAdapter(this,
                R.layout.full_shopping_list_row, shoppingLists);

        historyListView.setAdapter(historyListAdapter);

        historyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ShoppingList list = shoppingLists.get(position);
                progressDialog = ProgressDialog.show(UserHomeActivity.this, "Fetching list",
                        "Generic Processing Message", true);

                Model.getInstance().getShoppingListItems(UserHomeActivity.this, list);

            }
        });

        /*
        viewListButton = (Button)findViewById(R.id.userHome_viewListButton);
        viewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Main", "Hello Login attempt");
                //Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });*/
    }

    private class StableArrayAdapter extends ArrayAdapter<ShoppingList> {


        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<ShoppingList> objects) {
            super(context, textViewResourceId, objects);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            // View rowView = convertView;
            final ShoppingListViewHolder viewHolder;

            if (convertView == null) {

                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


                convertView  = inflater.inflate(R.layout.full_shopping_list_row, parent, false);

                viewHolder = new ShoppingListViewHolder();

                viewHolder.checkboxImage=(CheckBox)convertView.findViewById(R.id.full_shopping_list_row_checkBox);
                viewHolder.nameText=(TextView)convertView.findViewById(R.id.full_shopping_list_row_text);
                viewHolder.nextImage=(ImageView)convertView.findViewById(R.id.full_shopping_list_row_arrow);
                Button deleteButton = (Button) convertView.findViewById(R.id.full_shopping_list_delete_button);
                final int slot = position;
                deleteButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Model.getInstance().deleteShoppingList(UserHomeActivity.this, shoppingLists.get(slot));
                    }
                });

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

            viewHolder.nameText.setText(shoppingLists.get(position).getName());

            return convertView;

        } //close getView public View getView(int position, View convertView, ViewGroup parent) {


    }

    static class ShoppingListViewHolder
    {
        public TextView nameText;
        public ImageView nextImage;
        public CheckBox checkboxImage;
    }


    ///HMMM maybe i should've made this cleaner ..... lol.
    //Getting shopping list back.
    @Override
    public void success(ArrayList<ShoppingList> list, boolean wasCached) {

       // historyListAdapter.clear();
        shoppingLists.clear();
        shoppingLists.addAll(list);
        historyListAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    //Delete
    @Override
    public void success(String str, ShoppingList deleteList) {
       // historyListAdapter.clear();
        shoppingLists.remove(deleteList);
        historyListAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void success(ArrayList<Purchase> purchases, ShoppingList list) {
        progressDialog.dismiss();
        //TODO PASS groceries in.
        Intent intent = SearchActivity.createIntent(UserHomeActivity.this, list, purchases);
        startActivity(intent);
    }

    @Override
    public void failure(String reason) {
        progressDialog.dismiss();
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
        //Error

    }

}
