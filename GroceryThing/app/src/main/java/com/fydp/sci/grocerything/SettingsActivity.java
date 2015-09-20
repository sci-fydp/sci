package com.fydp.sci.grocerything;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.fydp.sci.grocerything.DataModel.Model;


public class SettingsActivity extends Activity {

	Switch pushNotifSwtich;
	Button deleteListsButton;
    ProgressDialog progressDialog;
	
	
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
		init();
    }
	
	private void init()
    {
        pushNotifSwtich = (Switch)findViewById(R.id.settings_pushNotifSwitch);
		pushNotifSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				//chcked.

				//CHECKED.
				progressDialog = ProgressDialog.show(SettingsActivity.this, "hmm",
						"Updating...", true);
				Model.getInstance().updateUserPushNotificationSettings(isChecked, new Model.ModelGenericListener(){
					@Override
					public void success(String str)
					{

					}

					public void failure(String str)
					{

					}
				});

			}
		});
		
        deleteListsButton = (Button)findViewById(R.id.settings_deleteListsButton);
		deleteListsButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
							progressDialog = ProgressDialog.show(SettingsActivity.this, "Updating...",
									"Updating...", true);
				        	//DELETE ALL LISTS.
					        Model.getInstance().deleteAllShoppingLists(new Model.ModelGenericListener(){
							 	@Override
							 	public void success(String str)
							 	{
	        						progressDialog.dismiss();
							 	}

							 	public void failure(String str)
							 	{
	        						progressDialog.dismiss();
							 	}
							 });
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
			}
		});
	}

}
