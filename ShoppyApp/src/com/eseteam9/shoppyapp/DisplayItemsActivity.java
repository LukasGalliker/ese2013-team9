package com.eseteam9.shoppyapp;

import java.util.ArrayList;
import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DisplayItemsActivity extends Activity {
	private int listId;
	private String listname;
	private ItemAdapter adapter;
	private Item[] items; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_items);
		
		//Get ListID and Name
		Intent intent = getIntent();
		this.listId = intent.getIntExtra("LIST_ID", 0);
		this.listname = intent.getStringExtra("LIST_NAME");
		this.setTitle(listname);
		
		//Initialize ListView
		ListView lv = (ListView)findViewById(R.id.itemoverview);
		registerForContextMenu(lv);
		lv.setClickable(true);
		
		//Create Adapter
		this.items = new ItemHandler(this).getListItems(this.listId);
		ArrayList<Item> list = new ArrayList<Item>(Arrays.asList(this.items));
		this.adapter = new ItemAdapter(this, R.id.itemoverview,  list);
		lv.setAdapter(adapter);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_items, menu);
		return true;
	}
	
	
	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.itemoverview) {
	    menu.setHeaderTitle("Options");
	    String[] menuItems = getResources().getStringArray(R.array.context_menu);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}
	
	//Reads what is selected from ContextMenu
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo(); 
	    Item listItem = items[menuInfo.position];
	    
	    switch (item.getItemId()) {
		  case 0:
			new ItemHandler(this).delete(listItem.id);
		    updateView();
		    return true;
		  case 1:
			  Intent intent = new Intent(this, AddItemActivity.class);
			  intent.putExtra("LIST_ID", listId);
			  intent.putExtra("ITEM_ID", listItem.id);
			  intent.putExtra("ITEM_NAME", listItem.name);
			  intent.putExtra("ITEM_QUANTITY", listItem.quantity);
			  startActivity(intent);
			  return true;
		  default:
		    return super.onContextItemSelected(item);
		}
	}
	public void updateView(){
		this.items = new ItemHandler(this).getListItems(this.listId);
		ArrayList<Item> list = new ArrayList<Item>(Arrays.asList(this.items));
		adapter.clear();
		adapter.addAll(list);
		adapter.notifyDataSetChanged();
	}
	
	//When checked an item
	public void checkItem(View view){
		boolean status = ((CheckBox) view).isChecked();
		ListView lv = (ListView) findViewById(R.id.itemoverview);
		Item item = items[lv.getPositionForView(view)];
		int id = item.id;
		new ItemHandler(this).checked(id, status);	
		
	}
	
	//Backbutton is pressed
	public void onBackPressed()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);  
	}
	
	//Options on Taskbar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_list:
	            openAddListView();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openAddListView(){
		Intent intent = new Intent(this, AddItemActivity.class);
		intent.putExtra("LIST_ID", this.listId);
		startActivity(intent);
	}

}
