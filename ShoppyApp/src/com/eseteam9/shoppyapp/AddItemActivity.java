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
	private int itemId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		Intent intent = getIntent();
		this.listId = intent.getIntExtra("LIST_ID", 0);
		
		//Get ItemID
		this.itemId = intent.getIntExtra("ITEM_ID", 0);
		
		//If Edit, change fields
		if (this.itemId!=0){
			String itemname = intent.getStringExtra("ITEM_NAME");
			String quantity = intent.getStringExtra("ITEM_QUANTITY");
			
			EditText quantityField = (EditText) findViewById(R.id.quantity);
			quantityField.setText(quantity);
			EditText editName = (EditText) findViewById(R.id.item_name);
			editName.setText(itemname);
			editName.setSelection(editName.getText().length());
			this.setTitle("Edit " + itemname);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
	}

	//Called when Savebutton is pressed
	public void manageItem(View view){

	    EditText editTextName = (EditText) findViewById(R.id.item_name);
	    String itemname = editTextName.getText().toString().trim();

	    EditText editTextQuantity = (EditText) findViewById(R.id.quantity);
	    String quantity = editTextQuantity.getText().toString().trim();
	    
	    //Check and Convert quantity
		if (quantity.length() == 0)
			quantity = "1";

		//Check if Item has name or already exists
		if (itemname.length() == 0)
	    	Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
		else{
			if (this.itemId==0)
				new ItemHandler(this).add(new Item(itemname, quantity, this.listId));
			else
				new ItemHandler(this).update(this.itemId, itemname, quantity);
		    //Switch to DisplayItemsActivity
		    Intent intent = new Intent(this, DisplayItemsActivity.class);
		    intent.putExtra("LIST_ID", this.listId);
		    startActivity(intent);
		}
	}
	
}
