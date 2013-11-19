package com.eseteam9.shoppyapp.handlers;

import com.eseteam9.shoppyapp.shoppingclasses.User;
import com.eseteam9.shoppyapp.valuesets.UserValueSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends ObjectHandler {
	public static final String TABLE_NAME = "users";

	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_EMAIL = "email";

	public UserHandler(Context context) {
		super(context, TABLE_NAME);
	}

	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public UserValueSet add(UserValueSet valueSet) {
		insert(valueSet.getContentValues(false));
		return getLatestRow();
	}

	public UserValueSet getById(int id) {
		Cursor cursor = getAll(KEY_ID + " = " + id);
		cursor.moveToFirst();

		UserValueSet returnValueSet = new UserValueSet(cursor);
		closeDB();
		return returnValueSet;
	}

	public User getOwner() {
		Cursor cursor = getAll();
		cursor.moveToFirst();

		User returnUser = new User(context, cursor.getInt(0));
		closeDB();
		return returnUser;
	}

	public String[] getAllNames() {
		Cursor cursor = getAll();

		String[] returnNames = new String[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnNames[cursor.getPosition()] = cursor.getString(2);
			while(cursor.moveToNext());

		closeDB();
		return returnNames;
	}

	public boolean existsUser() {
		boolean returnBoolean = getAll().getCount() > 0;
		closeDB();
		return returnBoolean;
	}

	public boolean existsUserByEmail(String email) {
		boolean returnBoolean = getAll(KEY_EMAIL + " = '" + email + "'").getCount() > 0;
		closeDB();
		return returnBoolean;
	}

	private UserValueSet getLatestRow() {
		Cursor cursor = getAll();
		cursor.moveToLast();

		UserValueSet returnValueSet = new UserValueSet(cursor);
		closeDB();
		return returnValueSet;
	}
}
