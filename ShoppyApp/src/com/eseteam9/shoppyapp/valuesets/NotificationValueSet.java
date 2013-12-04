package com.eseteam9.shoppyapp.valuesets;

import com.eseteam9.shoppyapp.handlers.NotificationHandler;

import android.database.Cursor;
import android.content.ContentValues;

public class NotificationValueSet extends AbstractValueSet {
	public final int id;
	public final String key;
	public final String data;
	public final String message;

	public NotificationValueSet(int id, String data, String message) {
		this.id = id;
		this.key = null;
		this.data = data;
		this.message = message;
	}

	public NotificationValueSet(String key, String data, String message) {
		this.id = 0;
		this.data = data;
		this.key = key;
		this.message = message;
	}

	public NotificationValueSet(Cursor c) {
		this.id = c.getInt(0);
		this.key = c.getString(1);
		this.data = c.getString(2);
		this.message = c.getString(3);
	}

	public ContentValues getContentValues(boolean withId) {
		ContentValues returnValues = new ContentValues();
		if(withId) returnValues.put(NotificationHandler.KEY_ID, id);
		returnValues.put(NotificationHandler.KEY_KEY, key);
		returnValues.put(NotificationHandler.KEY_DATA, data);
		returnValues.put(NotificationHandler.KEY_MESSAGE, message);
		return returnValues;
	}
}
