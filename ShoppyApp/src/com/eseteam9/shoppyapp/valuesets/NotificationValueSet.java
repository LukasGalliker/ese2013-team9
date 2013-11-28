package com.eseteam9.shoppyapp.valuesets;

import android.database.Cursor;

public class NotificationValueSet {
	public final String key;
	public final int notificationId;
	
	public NotificationValueSet(int notificationId) {
		this.key = null;
		this.notificationId = notificationId;
	}
	
	public NotificationValueSet(String key, int notificationId) {
		this.key = key;
		this.notificationId = notificationId;
	}
	
	public NotificationValueSet(Cursor c) {
		this.key = c.getString(0);
		this.notificationId = c.getInt(1);
	}
}
