package com.example.shoppyapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_list, menu);
		return true;
	}

	//Called when Savebutton is pressed
	public void addList(View view){
	    Intent intent = new Intent(this, DisplayListsActivity.class);
	    EditText editText = (EditText) findViewById(R.id.list_add);
	    String listname = editText.getText().toString();
	    
	    //Add Entry in DB
        LocalDatabaseHandler db = new LocalDatabaseHandler(this);
        db.addShoppingList(new ShoppingList(0, listname));
        
        //Switch to DisplayListsActivity
	    startActivity(intent);
	}
}
