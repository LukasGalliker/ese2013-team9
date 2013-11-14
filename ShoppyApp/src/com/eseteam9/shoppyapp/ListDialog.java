package com.eseteam9.shoppyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Provides the Options context menu on a List.
 * 
 * @author S�bastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
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
		
		//ArrayAdapter<String> arr = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, new UserHandler(context).getAllNames());
		ArrayAdapter<String> arr = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, Users.getAllNames(context));
		input.setAdapter(arr);
		/*
		alert.setNegativeButton("Browse", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				   Intent intent = new Intent(Intent.ACTION_PICK);
				   intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
			}	   
		});
		*/
		alert.setPositiveButton("Share", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String key = list.onlineKey();
				Editable value = input.getText();
				String email = value.toString();
				if (email.length() != 0){
					//User me = new UserHandler(context).get();
					User me = Users.getOwner(context);
					OnlineDatabaseHandler handler = new OnlineDatabaseHandler(context);
					//if (key.trim() == "0")
				    handler.putList(list, me);
					handler.shareList(key, email);   
					OnlineDatabaseHandler.notify(2, me);
				}
			}
		 });
		alert.show();
	}

	//Edit a list
	void editDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Edit List");
		alert.setMessage("Enter new Listname:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(context);
		alert.setView(input);
		input.setText(list.title());
		input.setSelection(list.title().length());
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  Editable value = input.getText();
		  String listname = value.toString();
		  
			if (listname.length() == 0)
		    	Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
			//else if (!new ShoppingListHandler(context).existsEntry(listname)){
			else if (!ShoppingLists.existsTitle(context, listname)){
				//new ShoppingListHandler(context).update(list.id, listname);
				list.title(listname);
				//updateAdapter();
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
		
		alert.setNegativeButton("Browse", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				   Intent intent = new Intent(Intent.ACTION_PICK);
				   intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
			}	   
		});
		
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
}
