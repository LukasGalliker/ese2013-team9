package com.eseteam9.shoppyapp;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.*;

public class DisplayListsFragment extends ListFragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private ExpandableListAdapter adapter;
	private List<ShoppingList> lists; 
	private HashMap<ShoppingList, List<Item>> content = new HashMap<ShoppingList, List<Item>>();
	
	
	public DisplayListsFragment() {
	}
	
	//Displays Lists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ExpandableListView lv = (ExpandableListView) getListView();
		registerForContextMenu(lv);
		lv.setClickable(true);
		this.lists = new ShoppingListHandler(getActivity()).getAll();
		for (ShoppingList l : this.lists){
			this.content.put(l, new ItemHandler(getActivity()).getListItems(l.id));
		}
			
		this.adapter = new ExpandableListAdapter(getActivity(), this.lists,  this.content);
		lv.setAdapter(adapter);
	}
	
	@Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getActivity(), DisplayItemsActivity.class);
        intent.putExtra("LIST_ID", lists.get(position).id);
        intent.putExtra("LIST_NAME", lists.get(position).title);
	    startActivity(intent);
	  }
	
	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==getListView().getId()) {
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
	    int listId = lists.get(menuInfo.position).id;
	    String listname = lists.get(menuInfo.position).title;
	    switch (item.getItemId()) {
		  case 0:
			new ShoppingListHandler(getActivity()).delete(listId);
		    updateView();
		    return true;
		  case 1:
			  Intent intent = new Intent(this.getActivity(), AddListActivity.class);
			  intent.putExtra("LIST_ID", listId);
			  intent.putExtra("LIST_NAME", listname);
			  startActivity(intent);    
		  default:
		    return super.onContextItemSelected(item);
		}
	}
	
	public void updateView(){
		adapter.notifyDataSetChanged();
	}
	
}
