package com.eseteam9.shoppyapp.activities;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.adapters.ItemAdapter;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.Items;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class ItemDialog extends Dialog {
	private Context context;
	
	public ItemDialog(Context context) {
		super(context);
		this.context = context;
	}
	
	public void addDialog(final int listId, final ItemAdapter adapter) {
		AlertDialog.Builder alert = new AlertDialog.Builder(context);

		alert.setTitle("Add Item");
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = infalInflater.inflate(R.layout.dialog_add_item, null);
		alert.setView(view);
		
		AutoCompleteTextView act=(AutoCompleteTextView)view.findViewById(R.id.item_name);
		ArrayAdapter<String> arr = new ArrayAdapter<String>(context,android.R.layout.simple_dropdown_item_1line, Items.getAllNames(context));
		act.setAdapter(arr);
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  EditText nameView = (EditText) view.findViewById(R.id.item_name);
		  String itemname = nameView.getText().toString();
		  EditText quantityView = (EditText) view.findViewById(R.id.quantity);
		  String quantity = quantityView.getText().toString();
		  
			if (itemname.length() == 0)
		    	Toast.makeText(context, "Please enter a name", Toast.LENGTH_SHORT).show();
			else {
				ShoppingList oldList = new ShoppingList(context, listId);
				Item item = new Item(context, listId, itemname, quantity);
				
				if (oldList.onlineKey().length() > 8)
					new OnlineDatabaseHandler(context).putItem(oldList.onlineKey(), listId, item, adapter);
				
				adapter.clear();
				adapter.updateItems(context, listId);
				adapter.notifyDataSetChanged();
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
