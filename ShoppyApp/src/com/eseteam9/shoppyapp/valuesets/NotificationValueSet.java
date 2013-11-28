package com.eseteam9.shoppyapp.valuesets;

import com.eseteam9.shoppyapp.handlers.NotificationHandler;
import android.database.Cursor;
import android.content.ContentValues;

public class NotificationValueSet extends AbstractValueSet {
	public final int id;
	public final String key;
	public final int notificationId;

	public NotificationValueSet(int notificationId) {
		this.id = 0;
		this.key = null;
		this.notificationId = notificationId;
	}

	public NotificationValueSet(String key, int notificationId) {
		this.id = 0;
		this.key = key;
		this.notificationId = notificationId;
	}

	public NotificationValueSet(Cursor c) {
		this.id = c.getInt(0);
		this.key = c.getString(1);
		this.notificationId = c.getInt(2);
	}

	public ContentValues getContentValues(boolean withId) {
		ContentValues returnValues = new ContentValues();
		if(withId) returnValues.put(NotificationHandler.KEY_ID, id);
		returnValues.put(NotificationHandler.KEY_KEY, key);
		returnValues.put(NotificationHandler.KEY_NOTIFICATION_ID, notificationId);
		return returnValues;
	}
}
