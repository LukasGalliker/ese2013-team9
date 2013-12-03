package com.eseteam9.shoppyapp.handlers;

import com.eseteam9.shoppyapp.shoppingclasses.Notification;
import com.eseteam9.shoppyapp.valuesets.NotificationValueSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotificationHandler extends ObjectHandler {
	public static final String TABLE_NAME = "notifications";

	public static final String KEY_ID = "id";
	public static final String KEY_KEY = "key";
	public static final String KEY_MESSAGE = "notification_id";
	public static final String KEY_DATA = "data";

	public NotificationHandler(Context context) {
		super(context, TABLE_NAME);
	}

	public static void createTable(SQLiteDatabase db) {
		String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_KEY + " TEXT,"
			+ KEY_DATA + " TEXT,"
			+ KEY_MESSAGE + " TEXT" + ")";
		db.execSQL(CREATE_NOTIFICATIONS_TABLE);
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public NotificationValueSet add(NotificationValueSet valueSet) {
		insert(valueSet.getContentValues(false));
		return getLatestRow();
	}

	public NotificationValueSet getById(int id) {
		Cursor cursor = getAll(KEY_ID + " = " + id);
		cursor.moveToFirst();

		NotificationValueSet returnValueSet = new NotificationValueSet(cursor);
		closeDB();
		return returnValueSet;
	}
	
	public Notification[] getAllNotifications() {
		Cursor cursor = getAll();

		Notification[] returnArray = new Notification[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnArray[cursor.getPosition()] = new Notification(context, cursor.getInt(0));
			while(cursor.moveToNext());

		closeDB();
		return returnArray;	
	}
	
	private NotificationValueSet getLatestRow() {
		Cursor cursor = getAll();
		cursor.moveToLast();

		NotificationValueSet returnValueSet = new NotificationValueSet(cursor);
		closeDB();
		return returnValueSet;
	}
	
	public NotificationValueSet getByKey(String key) {
		return null;
	}
}
