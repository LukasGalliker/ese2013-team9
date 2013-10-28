package com.eseteam9.shoppyapp;

import java.util.HashMap;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupClickListener;

public class DisplayListsFragment extends Fragment{
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	private ExpandableListAdapter adapter;
	private ShoppingList[] lists; 
	private HashMap<ShoppingList, Item[]> content = new HashMap<ShoppingList, Item[]>();
	
	
	public DisplayListsFragment() {
	}
	
	//Displays Lists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_display_lists,container, false);
        ExpandableListView elv = (ExpandableListView)view.findViewById(R.id.listoverview);
		registerForContextMenu(elv);
		elv.setClickable(true);
		
		//Get Lists and add items directly if 5 or less items to buy
		this.lists = new ShoppingListHandler(getActivity()).getAll();
		Item[] empty = null;
		for (ShoppingList l : this.lists)
			if (new ItemHandler(getActivity()).getCountUnbought(l.id) < 6)
				this.content.put(l, new ItemHandler(getActivity()).getUnbought(l.id));
			else
				this.content.put(l, empty);
		
		//Set Adapter
		this.adapter = new ExpandableListAdapter(getActivity(), this.lists,  this.content);
        elv.setAdapter(adapter);
        for(int i=0; i < adapter.getGroupCount(); i++)
        	if (adapter.getChildrenCount(i) > 0)
        		elv.expandGroup(i);
        
        //Set Listener
        elv.setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,long id) {
                Intent intent = new Intent(getActivity(), DisplayItemsActivity.class);
                intent.putExtra("LIST_ID", lists[groupPosition].id);
                intent.putExtra("LIST_NAME", lists[groupPosition].title);
        	    startActivity(intent);
        	    return true;
            };
        });
		return view;
		
	}

	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.listoverview) {
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
		ExpandableListContextMenuInfo menuInfo = (ExpandableListContextMenuInfo) item.getMenuInfo(); 
		ShoppingList list = lists[ExpandableListView.getPackedPositionGroup(menuInfo.packedPosition)];
	    int listId = list.id;
	    String listname = list.title;
	    switch (item.getItemId()) {
		  case 0:
			new ShoppingListHandler(getActivity()).delete(listId);
			updateAdapter();
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
	
	public void updateAdapter(){
		this.lists = new ShoppingListHandler(getActivity()).getAll();
		for (ShoppingList l : this.lists)
			//if (new ItemHandler(getActivity()).getCountUnbought(l.id) < 6)
				this.content.put(l, new ItemHandler(getActivity()).getListItems(l.id));
			//else
				//this.content.put(l, null);
		
		this.adapter.update(this.lists, this.content);
		this.adapter.notifyDataSetChanged();
	}
	
}
