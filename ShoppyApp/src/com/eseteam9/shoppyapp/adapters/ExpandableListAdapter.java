package com.eseteam9.shoppyapp.adapters;

import java.util.HashMap; 

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.shoppingclasses.Items;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
 /**
  * This class is responisble to make the Lists expandable so it is responsible to display the items of a list
  * in an expandable list view
  * 
  * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
  * @extends BaseExpandableListAdapter
  */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ShoppingList[] listDataHeader;
    private HashMap<ShoppingList, Item[]> listDataChild;
 
    public ExpandableListAdapter(Context context, ShoppingList[] listDataHeader,
            HashMap<ShoppingList, Item[]> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }
 
    @Override
    public Item getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader[groupPosition])[childPosititon];
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
    	
    	Item[] items = Items.getByListId(this.context, listDataHeader[groupPosition].id());
    	Item item = items[childPosition];
        		
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_row, null);
        }
 
        TextView childName = (TextView) convertView
                .findViewById(R.id.itemname);
        TextView childQuantity = (TextView) convertView
                .findViewById(R.id.quantityText);
        CheckBox childStatus = (CheckBox) convertView
                .findViewById(R.id.status);
        
        childName.setText(item.name());
        childQuantity.setText(item.quantity());
        childStatus.setChecked(item.bought());
        childStatus.setTag(item.id());
        
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
    	if (this.listDataChild.get(this.listDataHeader[groupPosition]) != null)
    		return this.listDataChild.get(this.listDataHeader[groupPosition]).length;
    	
    	return 0;
    }
 
    @Override
    public ShoppingList getGroup(int groupPosition) {
        return this.listDataHeader[groupPosition];
    }
 
    @Override
    public int getGroupCount() {
        return this.listDataHeader.length;
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_row, null);
        }
        ShoppingList list = getGroup(groupPosition);
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.listname);
        TextView number = (TextView) convertView.findViewById(R.id.itemsNum);
        ImageView shared = (ImageView)convertView.findViewById(R.id.imageView2);
        shared.setVisibility(0);
        if (list.onlineKey().length() < 9)
        	shared.setVisibility(8);
        
        lblListHeader.setText(list.title());
        ImageView arrow = (ImageView) convertView.findViewById(R.id.imageView1);
        arrow.setTag(list.id());
        
        int unbought = Items.getUnboughtCount(this.context, list.id());
        int total = Items.getCount(this.context, list.id());
        int bought = total - unbought;
        number.setText(bought + "/" + total);
        
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    
    public void update(ShoppingList[] listDataHeader, HashMap<ShoppingList, Item[]> listChildData){
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }
}