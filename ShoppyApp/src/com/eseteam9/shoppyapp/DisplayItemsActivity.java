package com.eseteam9.shoppyapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class DisplayItemsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_items);
		ListView lv = (ListView)findViewById(R.id.itemoverview);
		registerForContextMenu(lv);
		LocalDatabaseHandler db = new LocalDatabaseHandler(this);
		ListItemAdapter adapter = new ListItemAdapter(this, R.id.itemoverview,  db.getAllItems(1));
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_items, menu);
		return true;
	}

}
