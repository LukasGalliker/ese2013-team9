package com.eseteam9.shoppyapp.activities;

import java.util.Locale;
import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.R.id;
import com.eseteam9.shoppyapp.R.layout;
import com.eseteam9.shoppyapp.R.menu;
import com.eseteam9.shoppyapp.R.string;
import com.eseteam9.shoppyapp.fragments.DisplayFragment;
import com.eseteam9.shoppyapp.fragments.DisplayListsFragment;
import com.eseteam9.shoppyapp.fragments.DisplayNotificationsFragment;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
		
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
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
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		private DisplayFragment listFragment;
		private DisplayFragment notificationsFragment;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0){
				listFragment = new DisplayListsFragment();
				return listFragment;
			}
			
			notificationsFragment = new DisplayNotificationsFragment();
			return notificationsFragment;
		}

		public void update(){
			listFragment.updateAdapter();
			notificationsFragment.updateAdapter();
		}
		
		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
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
	            new OnlineDatabaseHandler(this).getSharedLists();
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
