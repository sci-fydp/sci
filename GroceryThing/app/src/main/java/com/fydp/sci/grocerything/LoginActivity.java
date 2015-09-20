package com.fydp.sci.grocerything;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fydp.sci.grocerything.DataModel.Model;
import com.fydp.sci.grocerything.NetworkUtils.AccLoginAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.NetworkUtils;


public class LoginActivity extends Activity implements AccLoginAsyncTask.AccLoginListener {

    EditText usernameInput;
    EditText passwordInput;
    Button loginButton;
    Button registerButton;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INITIALIZE MODEL?
        //Model.getInstance();
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

                //debugging
                //FIXME TODO remove.
                if (usernameInput.getText().toString().length() == 0 && passwordInput.getText().toString().length() == 0)
                {
                    //Call server and check for correctness.
                    Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(intent);
                }
                else
                {
                    progressDialog = ProgressDialog.show(LoginActivity.this, "Logging in...",
                            "Wait a second...", true);
                    NetworkUtils.getInstance().login(usernameInput.getText().toString(), passwordInput.getText().toString(), LoginActivity.this);
                }


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

    @Override
    public void loginSuccess(String msg) {
        progressDialog.dismiss();
        Toast.makeText(this, "hello " + msg, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginFailure(String reason) {

        progressDialog.dismiss();
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
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
