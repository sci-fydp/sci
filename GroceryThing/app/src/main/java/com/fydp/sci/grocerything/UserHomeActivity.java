package com.fydp.sci.grocerything;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UserHomeActivity extends Activity {
    ListView historyListView;
    ArrayList<String> shoppingListNames;
    StableArrayAdapter historyListAdapter;
    Button newListButton;
    //Button viewListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        init();
        
    }

    private void init()
    {
        newListButton = (Button)findViewById(R.id.userHome_newListButton);
        newListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                startActivity(intent);
                //Log.d("Main", "Hello Login attempt");
                //Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });

        shoppingListNames = new ArrayList<String>();
        shoppingListNames.add("HELLO");
        shoppingListNames.add("LIST NUMBER @");

        historyListView = (ListView)findViewById(R.id.userHome_savedList);
        historyListAdapter = new StableArrayAdapter(this,
                R.layout.full_shopping_list_row, shoppingListNames);

        historyListView.setAdapter(historyListAdapter);

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

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
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

            viewHolder.nameText.setText(shoppingListNames.get(position));

            return convertView;

        } //close getView public View getView(int position, View convertView, ViewGroup parent) {


    }

    static class ShoppingListViewHolder
    {
        public TextView nameText;
        public ImageView nextImage;
        public CheckBox checkboxImage;
    }
}
