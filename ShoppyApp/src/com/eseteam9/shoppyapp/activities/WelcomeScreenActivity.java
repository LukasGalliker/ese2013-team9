package com.eseteam9.shoppyapp.activities;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.User;
import com.eseteam9.shoppyapp.shoppingclasses.Users;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Is called ONLY on first App Startup and asks the user to specify his name and email
 * address (used for the share feauture).
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends Activity
 */
public class WelcomeScreenActivity extends Activity {

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
       
        Parse.initialize(this, "siN9uAfK3is01V4Yyad62BztutNZN761smpPFdhQ", "zWJquwUQlEw9NHvBuZFFdpMrMIQoIXRy8CjslwY3"); 
        ParseAnalytics.trackAppOpened(getIntent());
        //Checks if database of program exists, otherwise continues to DisplayLists
        if (Users.existsUser(this)){
            Intent intent = new Intent(this, MainActivity.class);
    	    startActivity(intent);
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		EditText editText = (EditText) findViewById(R.id.email);
		if (getEmail(this) != null)
			editText.setVisibility(8);
		return true;
	}
    
	//Called when clicking on "Save"-Button
	public void createDatabase(View view){
	    EditText editText = (EditText) findViewById(R.id.nickname);
	    String nickname = editText.getText().toString().trim();
	    EditText editText2 = (EditText) findViewById(R.id.email);
	    String email = editText2.getText().toString().trim();
		
	    if (email.length() == 0)
			email = getEmail(this);
	    
		if (nickname.length() == 0)
	    	Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
		else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
			Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
		else{
		    //Add Entry in DB    
			User user = new User(this, nickname, email);
		    new OnlineDatabaseHandler(this).putUser(user);
		    
	        //Switch to MainActivity
	        Intent intent = new Intent(this, MainActivity.class);
		    startActivity(intent);
		}
	}
	

	  static String getEmail(Context context) {
	    AccountManager accountManager = AccountManager.get(context); 
	    Account account = getAccount(accountManager);

	    if (account == null) {
	      return null;
	    } else {
	      return account.name;
	    }
	  }
		  
	  private static Account getAccount(AccountManager accountManager) {
	    Account[] accounts = accountManager.getAccountsByType("com.google");
	    Account account;
	    if (accounts.length > 0) {
	      account = accounts[0];      
	    } else {
	      account = null;
	    }
	    return account;
	  }

}
