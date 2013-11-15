package com.eseteam9.shoppyapp.handlers;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ObjectHandler extends LocalDatabaseHandler {
	protected SQLiteDatabase db;
		protected void openDB(){db = this.getWritableDatabase();}
		protected void closeDB(){db.close();}

	protected final Context context;
	private final String TABLE_NAME;

	public ObjectHandler(Context context, String TABLE_NAME) {
		super(context);
		this.TABLE_NAME = TABLE_NAME;
		this.context = context;
	}

	protected Cursor getCursor(String sqlQuery) {
		openDB();
		return db.rawQuery(sqlQuery, null);
	}

	protected Cursor getAll() {
		openDB();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	}

	protected Cursor getAll(String whereClause) {
		openDB();
		return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + whereClause, null);
	}

	protected void execute(String executeQuery) {
		openDB();
		db.execSQL(executeQuery);
		closeDB();
	}

	protected void insert(ContentValues values) {
		openDB();
		db.insert(TABLE_NAME, null, values);
		closeDB();
	}

	protected void update(ContentValues values, String whereClause) {
		openDB();
		db.update(TABLE_NAME, values, whereClause, null);
		closeDB();
	}

	protected void delete(String whereClause) {
		openDB();
		db.delete(TABLE_NAME, whereClause, null);
		closeDB();
	}
}
