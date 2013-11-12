package com.eseteam9.shoppyapp;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NotificationAdapter extends ArrayAdapter<Notification> {
	private Context context;
	private ArrayList<Notification> notifications;
	
	
	public NotificationAdapter(Context context, int resource, ArrayList<Notification> notifications) {
		super(context, resource);
		this.context=context;
		this.notifications = notifications;
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.notification_row, null);
        }
        
        String message = "";
        Notification notification = notifications.get(position);
        if (notification != null) {
        	switch (notification.notificationId){
        	case 1: message = "User has added you to Friendlist";
        			break;
        	case 2: message = "User has shared a list with you";
					break;        	
        	case 3: message = "User bought some stuff from a list";
					break;  
        	}
            TextView text = (TextView) v.findViewById(R.id.notificationText);
            if (text != null) {
            	text.setText(message);
            }
        }
        return v;
    }
	
	
}
