package com.eseteam9.shoppyapp;

import java.util.Locale;
import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingLists;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Manages the "home-screen" with the two tabs, sets the general View of it.
 * 
 * @author S�bastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends FragmentActivity
 * @implements ActionBar.TabListener
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
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
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
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
		private DisplayListsFragment fragment;

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if (position == 0){
				fragment = new DisplayListsFragment();
				return fragment;
			}
			
			Fragment fragment = new ListFragment();
			return fragment;
		}

		public void update(){
			fragment.updateAdapter();
		}
		
		@Override
		public int getCount() {
			// Show 2 total pages.
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
	        	addDialog();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        case R.id.action_refresh:
	            new OnlineDatabaseHandler(this).getSharedLists();
	            mSectionsPagerAdapter.update();
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
		//ItemHandler handler = new ItemHandler(this);
		//Item item = handler.get(id);
		//handler.checked(id, status);
		Item item = new Item(this, id);
		item.bought(status);
		new OnlineDatabaseHandler(this).checkItem(item.onlineKey(), status);
		//mSectionsPagerAdapter.update();
	}
	
	/**
	 * Opens
	 */
	void addDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Add List");
		alert.setMessage("Enter new Listname:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);
        
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  Editable value = input.getText();
		  String listname = value.toString();
		  
		  // generate testobjects
		  if (listname.equals("test")) {
			  initializeList();
			  mSectionsPagerAdapter.update();
			  return;
		  }
		  
			if (listname.length() == 0)
		    	Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();
			//else if (!new ShoppingListHandler(getApplicationContext()).existsEntry(listname)){
			else if (!ShoppingLists.existsTitle(getApplicationContext(), listname)){
				//new ShoppingListHandler(getApplicationContext()).add(new ShoppingList(listname));
				new ShoppingList(getApplicationContext(), listname);
				mSectionsPagerAdapter.update();
		    } 
		    else
		    	Toast.makeText(getApplicationContext(), "This list already exists", Toast.LENGTH_SHORT).show();
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    dialog.cancel();
		  }
		});
		
		

		alert.show();
	}
	
	protected void initializeList() {
		  new ShoppingList(getApplicationContext(), "Groceries");
		  new ShoppingList(getApplicationContext(), "Household articles");
		  ShoppingList[] lists = ShoppingLists.getAll(this);
		  ShoppingList groceries = null, household_articles = null;
		  for (ShoppingList list : lists){
			  if (list.title().equals("Groceries")) groceries = list;
			  if (list.title().equals("Household articles")) household_articles = list;
		  }
		  
		  new Item(getApplicationContext(), groceries.id(), "Apples", "1kg");
		  new Item(getApplicationContext(), groceries.id(), "Rice", "2kg");
		  new Item(getApplicationContext(), groceries.id(), "Peanuts", "1");
		  new Item(getApplicationContext(), groceries.id(), "Coffe powder", "500g");
		  new Item(getApplicationContext(), groceries.id(), "Bottled water", "2l");
		  
		  new  Item(getApplicationContext(), household_articles.id(), "Bulb, (E26)-screw", "1");
		  new  Item(getApplicationContext(), household_articles.id(), "Toilet pape", "1");
		  new  Item(getApplicationContext(), household_articles.id(), "Sponge", "2");
	}

	public void openItemView(View view){
        Intent intent = new Intent(this, DisplayItemsActivity.class);
        int listId = (Integer)view.getTag();
        intent.putExtra("LIST_ID", listId);
        //intent.putExtra("LIST_NAME", new ShoppingListHandler(this).get(listId).title);
        intent.putExtra("LIST_NAME", new ShoppingList(this, listId).title());
	    startActivity(intent);
	}

}
