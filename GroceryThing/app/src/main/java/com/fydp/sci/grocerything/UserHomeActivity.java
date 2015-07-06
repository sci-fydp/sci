package com.fydp.sci.grocerything;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UserHomeActivity extends ActionBarActivity {

    Button newListButton;
    Button viewListButton;

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
                //Log.d("Main", "Hello Login attempt");
                //Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });

        viewListButton = (Button)findViewById(R.id.userHome_viewListButton);
        viewListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Main", "Hello Login attempt");
                //Intent intent = new Intent(UserHomeActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });
    }
}
