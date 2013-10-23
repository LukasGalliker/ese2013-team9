package com.eseteam9.shoppyapp;

import com.eseteam9.shoppyapp.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddListActivity extends Activity {
	private int listid;
	private String listname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list);
		
		//Get ListID and Listname
		Intent intent = getIntent();
		this.listid = intent.getIntExtra("LIST_ID", 0);
		
		//If Edit, change fields
		if (this.listid!=0){
			this.listname = intent.getStringExtra("LIST_NAME");
			EditText editName = (EditText) findViewById(R.id.list_name);
			TextView title = (TextView) findViewById(R.id.add_textview);
			title.setText("Edit Listname:");
			editName.setText(this.listname);
			editName.setSelection(editName.getText().length());
			this.setTitle("Edit " + this.listname);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_list, menu);
		return true;
	}

	//Called when Savebutton is pressed
	public void manageList(View view){

	    EditText editText = (EditText) findViewById(R.id.list_name);
	    String listname = editText.getText().toString();
		
		//Check if List has name or already exists
		if (listname.length() == 0)
	    	Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
		else if (!new ShoppingListHandler(this).existsEntry(listname)){
		    // Add or Edit Entry in DB
			if (this.listid==0)
				new ShoppingListHandler(this).add(new ShoppingList(listname, "Owner"));
			else
				new ShoppingListHandler(this).update(this.listid, listname);
		    
		    //Switch to DisplayListsActivity
		    Intent intent = new Intent(this, DisplayListsActivity.class);
		    startActivity(intent);
	    } 
	    else
	    	Toast.makeText(this, "This list already exists", Toast.LENGTH_SHORT).show();
	}
}
