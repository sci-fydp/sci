package com.fydp.sci.grocerything;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fydp.sci.grocerything.DataModel.Model;


public class LoginActivity extends Activity {

    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INITIALIZE MODEL?
        Model.getInstance();
        init();
        //Hmm
    }

    private void init()
    {
        loginButton = (Button)findViewById(R.id.login_loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "Hello Login attempt");
                Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });

        registerButton = (Button)findViewById(R.id.login_registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "Hello Login attempt");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        usernameInput = (EditText)findViewById(R.id.login_nameInput);
        passwordInput = (EditText)findViewById(R.id.login_passwordInput);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
    }*/
}
