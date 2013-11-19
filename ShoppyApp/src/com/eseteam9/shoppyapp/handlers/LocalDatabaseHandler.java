package com.eseteam9.shoppyapp.handlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHandler extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "local.db";

	public LocalDatabaseHandler (Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		UserHandler.createTable(db);
		ShoppingListHandler.createTable(db);
		ItemHandler.createTable(db);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		UserHandler.dropTable(db);
		ShoppingListHandler.dropTable(db);
		ItemHandler.dropTable(db);

		onCreate(db);
	}
}

