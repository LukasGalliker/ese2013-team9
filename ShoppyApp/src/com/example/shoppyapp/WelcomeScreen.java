package com.example.shoppyapp;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class WelcomeScreen extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.NICKNAME";
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        if (!doesDatabaseExist(this, "/data/data/com.example.ShoppyApp/databases/ShoppyLocal.db")){
            Intent intent = new Intent(this, DisplayListsActivity.class);
    	    startActivity(intent);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}
	
    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile=context.getDatabasePath(dbName);
        return dbFile.exists();
    }
    
	//Called when clicking on "Save"-Button
	public void createDatabase(View view){
	    EditText editText = (EditText) findViewById(R.id.nickname);
	    String nickname = editText.getText().toString();
	    
	    //get Phone number if possible
	    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
	    String myNumber = tm.getLine1Number();
	    
	    //Add Entry in DB
        LocalDatabaseHandler db = new LocalDatabaseHandler(this);
        db.addUser(new User(0, nickname, myNumber));
        
        Intent intent = new Intent(this, DisplayListsActivity.class);
	    startActivity(intent);
	}

}
