package com.eseteam9.shoppyapp.handlers;

import com.eseteam9.shoppyapp.valuesets.NotificationValueSet;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class NotificationHandler extends ObjectHandler {
	public static final String TABLE_NAME = "notifications";

	public static final String KEY_ID = "id";
	public static final String KEY_KEY = "key";
	public static final String KEY_NOTIFICATION_ID = "notification_id";

	public NotificationHandler(Context context) {
		super(context, TABLE_NAME);
	}

	public static void createTable(SQLiteDatabase db) {
		String CREATE_NOTIFICATIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_KEY + " TEXT,"
			+ KEY_NOTIFICATION_ID + " INTEGER" + ")";
		db.execSQL(CREATE_NOTIFICATIONS_TABLE);
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public NotificationValueSet add(String key) {
		return null;
	}

	public NotificationValueSet getByKey(String key) {
		return null;
	}
}
