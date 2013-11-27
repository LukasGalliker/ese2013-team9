package com.eseteam9.shoppyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
        String message = "";
        int layout = 0;
        Notification notification = notifications[position];
        if (notification != null) {
        	switch (notification.notificationId){
        	case 1: message = notification.data + " has added you to Friendlist";
        			break;
        	case 2: message = notification.data + " has shared a list with you";
					break;        	
        	case 3: message = "Something was bought the list " + notification.data;
					break;  
        	case 4: message = "No new Notifications!";
        			break;
        	}
        	
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layout==0)
            	convertView = vi.inflate(R.layout.notification_row, null);
            else
            	convertView = vi.inflate(R.layout.notification_row2, null);
            TextView text = (TextView) convertView.findViewById(R.id.notificationText);
            if (text != null) {
            	text.setText(message);
            }
            }
        return convertView;
    }
	
	public void update(Notification[] notifications){
		this.notifications = notifications;
	}
	
	@Override
	public int getCount() {
	    return notifications.length;
	}
}
