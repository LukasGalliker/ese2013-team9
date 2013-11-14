package com.eseteam9.shoppyapp;

import java.util.HashMap;
import android.text.Editable;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
/**
 * This class displays all the lists and provides the add button. 
 * Is one of the tabs in the "home-screen".
 * 
 * @author S�bastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
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
		
		this.lists = new ShoppingListHandler(getActivity()).getAll();
		for (ShoppingList l : this.lists)
			//this.content.put(l, new ItemHandler(getActivity()).getUnbought(l.id()));
			this.content.put(l, Items.getUnboughtByListId(getActivity(), l.id()));
		
		//Set Adapter
		this.adapter = new ExpandableListAdapter(getActivity(), this.lists,  this.content);
        elv.setAdapter(adapter);
        
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
	    int listId = list.id();
	    switch (item.getItemId()) {
		  case 0:
			//new newShoppingListHandler(getActivity()).delete(listId);
			ShoppingLists.deleteById(getActivity(), listId);
			updateAdapter();
		    return true;
		  case 1:
			  //new ListDialog(getActivity()).editDialog(list);
			  editDialog(list);
			  return true;
		  case 2:
		    new ListDialog(getActivity()).shareDialog(list);
		}
		return true;
	}

	public void updateAdapter(){
		this.lists = new ShoppingListHandler(getActivity()).getAll();
		for (ShoppingList l : this.lists)
			//this.content.put(l, new ItemHandler(getActivity()).getUnbought(l.id()));
			this.content.put(l, Items.getUnboughtByListId(getActivity(), l.id()));
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
			//else if (!new newShoppingListHandler(getActivity()).existsEntry(listname)){
			else if (!ShoppingLists.existsTitle(getActivity(), listname)){
				//new newShoppingListHandler(getActivity()).update(list.id, listname);
				list.title(listname);
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
