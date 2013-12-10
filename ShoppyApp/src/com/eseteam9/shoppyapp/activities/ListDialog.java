package com.eseteam9.shoppyapp.activities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingLists;
import com.eseteam9.shoppyapp.shoppingclasses.Users;
/**
 * Provides the Options context menu on a List.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends Dialog
 */
public class ListDialog extends Dialog {
	private Context context;
	
	public ListDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	//Share a list
	public void shareDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Share List");
		alert.setMessage("Enter Email or choose from Contacts");
		final AutoCompleteTextView input = new AutoCompleteTextView(context);
		alert.setView(input);
		
		List<String> userList = Arrays.asList(Users.getAllNames(context));
		Set<String> s = new HashSet<String>(userList);
		ArrayAdapter<String> arr = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, Arrays.asList(Users.getAllNames(context)));
		input.setAdapter(arr);

		alert.setPositiveButton("Share", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String listKey = list.onlineKey();
				Editable value = input.getText();
				String email = value.toString();
				if (email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && email.length() > 0){
					OnlineDatabaseHandler handler = new OnlineDatabaseHandler(context);
					if (listKey.length() < 9)
						handler.putList(list, email);
					else
						handler.shareList(listKey, email);				
					
					if (!Users.existsUserByEmail(context, email))
						handler.getUser(email);	
				}
				else
					Toast.makeText(context, "Email is invalid", Toast.LENGTH_SHORT).show();
			}
		 });
		alert.show();
	}

	//Edit a list
	void editDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Edit List");

		// Set an EditText view to get user input 
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = infalInflater.inflate(R.layout.dialog_add_list, null);
		alert.setView(view);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			EditText nameView = (EditText) view.findViewById(R.id.list_name);
			String listname = nameView.getText().toString();
		    
			if (listname.length() == 0)
		    	Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
			else if (!ShoppingLists.existsTitle(context, listname)){
				list.title(listname);
				new OnlineDatabaseHandler(context).updateList(list.onlineKey());
		    } 
		    else
		    	Toast.makeText(context, "This list already exists", Toast.LENGTH_SHORT).show();
		  }
		});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    dialog.cancel();
		  }
		});

		alert.show();
	}
	
	//Share a list
	public void addFriendDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Add Friend");
		alert.setMessage("Enter Email or choose from Contacts");
		final EditText input = new EditText(context);
		alert.setView(input);
		
		/*
		alert.setNegativeButton("Browse", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				   Intent intent = new Intent(Intent.ACTION_PICK);
				   intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
			}	   
		});
		*/
		alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Editable value = input.getText();
				String email = value.toString();
				if (email.length() != 0)
					new OnlineDatabaseHandler(context).getUser(email);
			}
		 });
		alert.show();
	}
	
	/**
	 * Opens "Add List" Dialog
	 */
	void addDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Add List");

		// Set an EditText view to get user input 
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = infalInflater.inflate(R.layout.dialog_add_list, null);
		alert.setView(view);
        
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		    EditText nameView = (EditText) view.findViewById(R.id.list_name);
		    String listname = nameView.getText().toString();
		  
			if (listname.length() == 0)
		    	Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
			else if (!ShoppingLists.existsTitle(context, listname)){
				  
  			  // generate testobjects
			    if (listname.equals("test"))
				    initializeList();
			    else
			    	new ShoppingList(context, listname);
			    MainActivity.updateAdapter();
		    } 
		    else
		    	Toast.makeText(context, "This list already exists", Toast.LENGTH_SHORT).show();
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
		  ShoppingList groceries = new ShoppingList(context, "Groceries"); 
		  ShoppingList household_articles = new ShoppingList(context, "Household articles");
		  
		  new Item(context, groceries.id(), "Apples", "1kg");
		  new Item(context, groceries.id(), "Rice", "2kg");
		  new Item(context, groceries.id(), "Peanuts", "1");
		  new Item(context, groceries.id(), "Coffe powder", "500g");
		  new Item(context, groceries.id(), "Bottled water", "2l");
		  
		  new  Item(context, household_articles.id(), "Bulb, (E26)-screw", "1");
		  new  Item(context, household_articles.id(), "Toilet pape", "1");
		  new  Item(context, household_articles.id(), "Sponge", "2");
	}
}
