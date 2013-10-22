package com.eseteam9.shoppyapp;

import java.util.List;

import com.eseteam9.shoppyapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListItemAdapter extends ArrayAdapter<Item> {
    private List<Item> items;
    private Activity activity;
    
    public ListItemAdapter(Activity a, int textViewResourceId, List<Item> items){
    	super(a, textViewResourceId, items);
        this.items = items;
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
            TextView itemView = (TextView) v.findViewById(R.id.name);
            if (itemView != null) {
                itemView.setText(item.name);
            }
            CheckBox itemView2 = (CheckBox) v.findViewById(R.id.status);
            if (itemView2 != null) {
            	if (item.bought)
            		itemView2.setChecked(true);
            	else
            		itemView2.setChecked(false);	
            }
        }
        return v;
    }
}
