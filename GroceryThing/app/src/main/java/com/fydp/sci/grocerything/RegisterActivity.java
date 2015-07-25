package com.fydp.sci.grocerything;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fydp.sci.grocerything.NetworkUtils.AccRegistrationAsyncTask;
import com.fydp.sci.grocerything.NetworkUtils.NetworkUtils;


public class RegisterActivity extends Activity implements AccRegistrationAsyncTask.AccRegistrationListener {
    
    EditText emailInput;
    EditText confirmPasswordInput;
    EditText passwordInput;
    Button submitButton;
    ProgressDialog progressDialog;
    //progress.dismiss();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }


    private void init()
    {

        submitButton = (Button)findViewById(R.id.register_submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.getText().toString().equals(confirmPasswordInput.getText().toString())) {
                    progressDialog = ProgressDialog.show(RegisterActivity.this, "Generic Processing Title",
                            "Generic Processing Message", true);

                    NetworkUtils.getInstance().register(emailInput.getText().toString(), passwordInput.getText().toString(), RegisterActivity.this);
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_LONG).show();

                }

                //Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                //startActivity(intent);

            }
        });
        emailInput = (EditText)findViewById(R.id.register_emailInput);
        passwordInput = (EditText)findViewById(R.id.register_passwordInput);
        confirmPasswordInput = (EditText)findViewById(R.id.register_passwordInputConfirm);
    }

    //AccRegistrationListener

    public void registrationSuccess(String session)
    {
        progressDialog.dismiss();
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
        //TODO register to model. ? or not
        finish();
    }
    public void registrationFailure(String reason)
    {
        progressDialog.dismiss();
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();

    }

}
