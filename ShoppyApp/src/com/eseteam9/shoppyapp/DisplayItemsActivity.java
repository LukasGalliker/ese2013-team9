package com.eseteam9.shoppyapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.RelativeLayout;

public class DisplayItemsActivity extends Activity {
	private int listId;
	private ListItemAdapter adapter;
	private List<Item> items; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_items);
		
		//Get ListID and Name
		Intent intent = getIntent();
		this.listId = intent.getIntExtra("LIST_ID", 0);
		this.setTitle(intent.getStringExtra("LIST_NAME"));
		
		//Initialize ListView
		ListView lv = (ListView)findViewById(R.id.itemoverview);
		registerForContextMenu(lv);
		lv.setClickable(true);
		
		//Create Adapter
		this.items = new ItemHandler(this).getListItems(this.listId);
		this.adapter = new ListItemAdapter(this, R.id.itemoverview,  this.items);
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
	    int itemId = items.get(menuInfo.position).id;
	    LocalDatabaseHandler db = new LocalDatabaseHandler(this);
	    
	    switch (item.getItemId()) {
		  case 0:
			new ItemHandler(this).delete(itemId);
		    updateView();
		    return true;
		    
		  default:
		    return super.onContextItemSelected(item);
		}
	}
	public void updateView(){
		adapter.clear();
		adapter.addAll(new ItemHandler(this).getListItems(this.listId));
		adapter.notifyDataSetChanged();
	}
	
	public void checkItem(View view){
		boolean status = ((CheckBox) view).isChecked();
		ListView lv = (ListView) findViewById(R.id.itemoverview);
		Item item = items.get(lv.getPositionForView(view));
		int id = item.id;
		new ItemHandler(this).checked(id, status);	
		
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
