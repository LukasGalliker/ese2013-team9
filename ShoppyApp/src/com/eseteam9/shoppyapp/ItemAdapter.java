package com.eseteam9.shoppyapp;

import java.util.ArrayList;

import com.eseteam9.shoppyapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ItemAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    private Activity activity;
    
    public ItemAdapter(Activity a, int textViewResourceId, ArrayList<Item> list){
    	super(a, textViewResourceId, list);
        this.items = list;
        this.activity = a;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.item_row, null);
        }
 
        Item item = items.get(position);
        if (item != null) {
            TextView name = (TextView) v.findViewById(R.id.itemname);
            if (name != null) {
            	name.setText(item.name);
            }
            TextView quantity = (TextView) v.findViewById(R.id.quantityText);
            if (quantity != null) {
            	quantity.setText(item.quantity);
            }
            CheckBox CheckBought = (CheckBox) v.findViewById(R.id.status);
            if (CheckBought != null) {
            	if (item.bought){
            		CheckBought.setChecked(true);
            	}	
            	else
            		CheckBought.setChecked(false);	
            }
        }
        return v;
    }
}
