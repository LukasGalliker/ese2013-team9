package com.eseteam9.shoppyapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddItemActivity extends Activity {
	private int listId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		Intent intent = getIntent();
		this.listId = intent.getIntExtra("LIST_ID", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	//Called when Savebutton is pressed
	public void addItem(View view){

	    EditText editTextName = (EditText) findViewById(R.id.item_name);
	    String itemname = editTextName.getText().toString();

	    EditText editTextQuantity = (EditText) findViewById(R.id.quantity);
	    String quantity = editTextQuantity.getText().toString();
		
	    EditText editTextUnit = (EditText) findViewById(R.id.unit);
	    String unit = editTextUnit.getText().toString();
	    
	    //Check and Convert quantity
		if (quantity.length() == 0)
			quantity = "1";
		
		quantity = quantity + " " + unit;
		//Check if List has name or already exists
		if (itemname.length() == 0)
	    	Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
		else{
		    //Add Entry in DB
		    new ItemHandler(this).add(new Item(itemname, quantity, this.listId));
		    
		    //Switch to DisplayItemsActivity
		    Intent intent = new Intent(this, DisplayItemsActivity.class);
		    intent.putExtra("LIST_ID", this.listId);
		    startActivity(intent);
		}
	}
	
}
