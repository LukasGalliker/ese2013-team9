package com.example.shoppyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class WelcomeScreen extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.NICKNAME";
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}
	
	//Called when clicking on "Save"-Button
	public void createDatabase(View view){
	    Intent intent = new Intent(this, DisplayListsActivity.class);
	    EditText editText = (EditText) findViewById(R.id.nickname);
	    String nickname = editText.getText().toString();
	    
	    //Add Entry in DB
        LocalDatabaseHandler db = new LocalDatabaseHandler(this);
        db.addUser(new User(1, nickname, "fj3fjh"));
        //Switch to DisplayListsActivity
	    intent.putExtra(EXTRA_MESSAGE, nickname);
	    startActivity(intent);
	}

}
