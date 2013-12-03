package com.eseteam9.shoppyapp.activities;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.adapters.SectionsPagerAdapter;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.shoppingclasses.Users;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
/**
 * Displays the Main Screen with the two tabs "Lists" and "Notifications".
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends FragmentActivity
 * @implements ActionBar.TabListener
 */

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	static SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	static public final int PICK_CONTACT = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_lists);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), 
				this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_lists, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
		FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	public void onBackPressed()
	{
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
	}
	
	//Options on Taskbar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.add_list:
	        	new ListDialog(this).addDialog();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        case R.id.action_refresh:
	        	OnlineDatabaseHandler oHandler = new OnlineDatabaseHandler(this);
	            oHandler.getSharedLists();
	            updateAdapter();
	            return true;
	        case R.id.add_friend:
	            new ListDialog(this).addFriendDialog();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Checks an item and updates the Online Database.
	 * @param view
	 */
	public void checkItem(View view){
		boolean status = ((CheckBox) view).isChecked();
		int id = (Integer) view.getTag();
		Item item = new Item(this, id);
		item.bought(status);
		new OnlineDatabaseHandler(this).checkItem(item.onlineKey(), status);
	}
	
	public static void updateAdapter(){
		mSectionsPagerAdapter.update();
	}

	public void openItemView(View view){
        Intent intent = new Intent(this, DisplayItemsActivity.class);
        int listId = (Integer)view.getTag();
        intent.putExtra("LIST_ID", listId);
        intent.putExtra("LIST_NAME", new ShoppingList(this, listId).title());
	    startActivity(intent);
	}

}
