package com.eseteam9.shoppyapp.activities;

import java.util.Arrays;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
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
		alert.setTitle(context.getString(R.string.share_list));
		alert.setMessage(context.getString(R.string.enter_email));
		final AutoCompleteTextView input = new AutoCompleteTextView(context);
		alert.setView(input);

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

		alert.setTitle(context.getString(R.string.share_list));

		// Set an EditText view to get user input 
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = infalInflater.inflate(R.layout.dialog_add_list, null);
		alert.setView(view);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			EditText nameView = (EditText) view.findViewById(R.id.list_name);
			String listname = nameView.getText().toString();
		    
			if (listname.length() == 0)
		    	Toast.makeText(context, context.getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
			else if (!ShoppingLists.existsTitle(context, listname)){
				list.title(listname);
				new OnlineDatabaseHandler(context).updateList(list.onlineKey());
		    } 
		    else
		    	Toast.makeText(context, context.getString(R.string.list_exists), Toast.LENGTH_SHORT).show();
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
		alert.setMessage(context.getString(R.string.enter_email));
		final EditText input = new EditText(context);
		alert.setView(input);

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

		alert.setTitle(context.getString(R.string.add_list));

		// Set an EditText view to get user input 
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = infalInflater.inflate(R.layout.dialog_add_list, null);
		alert.setView(view);
        
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		    EditText nameView = (EditText) view.findViewById(R.id.list_name);
		    String listname = nameView.getText().toString();
		  
			if (listname.length() == 0)
		    	Toast.makeText(context, context.getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
			else{
			    new ShoppingList(context, listname);
			    MainActivity.updateAdapter();
		    } 
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
