package com.eseteam9.shoppyapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

public class ListDialog extends Dialog {
	private Context context;
	
	public ListDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public void shareDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Share List");
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
		
		alert.setPositiveButton("Share", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Editable value = input.getText();
				String email = value.toString();
				if (email.length() != 0){
					OnlineDatabaseHandler handler = new OnlineDatabaseHandler(context);
					User me = new UserHandler(context).get();
					if (list.onlineKey !="0")
						handler.putList(list, me);
					handler.shareList(list.onlineKey, email);   
					OnlineDatabaseHandler.notify(2, me);
				}
			}
		 });
		 alert.show();
	}

	
	void editDialog(final ShoppingList list) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Edit List");
		alert.setMessage("Enter new Listname:");

		// Set an EditText view to get user input 
		final EditText input = new EditText(context);
		alert.setView(input);
		input.setText(list.title);
		input.setSelection(list.title.length());

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  Editable value = input.getText();
		  String listname = value.toString();
		  
			if (listname.length() == 0)
		    	Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
			else if (!new ShoppingListHandler(context).existsEntry(listname)){
				new ShoppingListHandler(context).update(list.id, listname);
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
}
