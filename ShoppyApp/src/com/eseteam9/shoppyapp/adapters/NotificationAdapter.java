package com.eseteam9.shoppyapp.adapters;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.activities.DisplayItemsActivity;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.Notification;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingLists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * This class converts the Array of notification to it's String representation based on the NotificationID.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends ArrayAdapter<Notification>
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {
	private Context context;
	private Notification[] notifications;
	
	
	public NotificationAdapter(Context context, int resource, Notification[] notifications) {
		super(context, resource);
		this.context=context;
		this.notifications = notifications;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Notification notification = notifications[position];
        View v = convertView;
        if (notification != null) {  
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.notification_row, null);
            TextView text = (TextView) v.findViewById(R.id.notificationText);
            v.setTag(notification.data);
            if (text != null)
            	text.setText(notification.message);
            
            v.setOnClickListener(new OnClickListener(){
                @Override 
                public void onClick(View v) {
                    Intent intent = new Intent(context, DisplayItemsActivity.class);
                    String info = (String)v.getTag();
                    if (ShoppingLists.existsOnlineKey(context, info)){
                    	int listId = ShoppingLists.getByOnlineKey(context, info).id();
	                    intent.putExtra("LIST_ID", listId);
	                    intent.putExtra("LIST_NAME", new ShoppingList(context, listId).title());
	            	    context.startActivity(intent);
                    }
                    else
                    	new OnlineDatabaseHandler(context).getUser(info);
                }
            }); 
        }
        return v;
    }
	
	public void update(Notification[] notifications){
		this.clear();
		this.notifications = notifications;
		this.addAll(notifications);
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
	    return notifications.length;
	}
}
