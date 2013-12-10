package com.eseteam9.shoppyapp.fragments;

import java.util.HashMap;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.activities.DisplayItemsActivity;
import com.eseteam9.shoppyapp.activities.ListDialog;
import com.eseteam9.shoppyapp.adapters.ExpandableListAdapter;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.Items;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingLists;

import android.text.Editable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
/**
 * This class displays all the lists and provides the add button. 
 * Is one of the tabs in the "home-screen".
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends DisplayFragment
 */
public class DisplayListsFragment extends DisplayFragment{
	private ExpandableListAdapter adapter;
	private ShoppingList[] lists; 
	private HashMap<ShoppingList, Item[]> content = new HashMap<ShoppingList, Item[]>();
	private ExpandableListView elv;
	
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
        elv = (ExpandableListView)view.findViewById(R.id.listoverview);
		registerForContextMenu(elv);
		elv.setClickable(true);
		elv.setGroupIndicator(null);
		elv.setOnGroupClickListener(new OnGroupClickListener(){
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
					Context context = getActivity();
			        Intent intent = new Intent(context, DisplayItemsActivity.class);
			        int listId = (Integer)v.getTag();
			        intent.putExtra("LIST_ID", listId);
			        intent.putExtra("LIST_NAME", new ShoppingList(context, listId).title());
				    startActivity(intent);
				    return true;
			}
		});
		
		this.lists = ShoppingLists.getAll(getActivity());
		for (ShoppingList l : this.lists)
			this.content.put(l, Items.getUnboughtByListId(getActivity(), l.id()));
		
		//Set Adapter
		this.adapter = new ExpandableListAdapter(getActivity(), this.lists,  this.content);
        elv.setAdapter(adapter);
        
		return view;
		
	}

	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
	    super.onCreateContextMenu(menu, v, menuInfo);
	    ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Options");
	    String[] menuItems = getResources().getStringArray(R.array.list_context_menu);
	    int i;
	    for (i = 0; i<menuItems.length; i++)
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    
	    ShoppingList list = lists[ExpandableListView.getPackedPositionGroup(info.packedPosition)];
	    if (list.onlineKey().length() > 7)
	    	menu.add(Menu.NONE, i, i, "Unshare");
	}
	
	//Reads what is selected from ContextMenu
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo menuInfo = (ExpandableListContextMenuInfo) item.getMenuInfo(); 
		ShoppingList list = lists[ExpandableListView.getPackedPositionGroup(menuInfo.packedPosition)];
	    int listId = list.id();
	    String onlineKey = list.onlineKey();
	    Context context = getActivity();
	    switch (item.getItemId()) {
		  case 0:
			ShoppingLists.deleteById(context, listId);
			if (onlineKey.length() > 7)
				new OnlineDatabaseHandler(context).deleteSharedLists(onlineKey);
			updateAdapter();
		    return true;
		  case 1:
			  editDialog(list);
			  return true;
		  case 2:
			  new ListDialog(context).shareDialog(list);
			  return true;
		  case 3:
			  new OnlineDatabaseHandler(context).deleteSharedLists(onlineKey);
			  list.onlineKey("0");
			  updateAdapter();
			  return true;
		}
		return true;
	}

	public void updateAdapter(){
		Context context = getActivity();
		this.lists = ShoppingLists.getAll(context);
		for (ShoppingList l : this.lists)
			this.content.put(l, Items.getUnboughtByListId(context, l.id()));
		this.adapter.update(this.lists, this.content);
		this.adapter.notifyDataSetChanged();
	}
	
	void editDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Edit List");
		alert.setMessage("Enter new Listname:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(getActivity());
		alert.setView(input);
		input.setText(list.title());
		input.setSelection(list.title().length());

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  Editable value = input.getText();
		  String listname = value.toString();
		  
			if (listname.length() == 0)
		    	Toast.makeText(getActivity(), "Please enter a name", Toast.LENGTH_SHORT).show();
			else if (!ShoppingLists.existsTitle(getActivity(), listname)){
				list.title(listname);
				new OnlineDatabaseHandler(getActivity()).updateList(list.onlineKey());
				updateAdapter();
		    } 
		    else
		    	Toast.makeText(getActivity(), "This list already exists", Toast.LENGTH_SHORT).show();
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    dialog.cancel();
		  }
		});

		alert.show();
	}
	
}
