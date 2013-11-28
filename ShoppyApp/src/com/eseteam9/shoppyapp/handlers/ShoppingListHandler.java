package com.eseteam9.shoppyapp.handlers;

import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.valuesets.ShoppingListValueSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ShoppingListHandler extends ObjectHandler {
	public static final String TABLE_NAME = "shopping_lists";

	public static final String KEY_ID = "id";
	public static final String KEY_ONLINE_KEY = "onlineKey";
	public static final String KEY_TITLE = "title";
	public static final String KEY_ARCHIVED = "archived";
	public static final String KEY_TIMESTAMP = "timestamp";

	public ShoppingListHandler(Context context) {
		super(context, TABLE_NAME);
	}

	public static void createTable(SQLiteDatabase db) {
		String CREATE_SHOPPING_LISTS_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_TITLE + " TEXT, "
			+ KEY_ONLINE_KEY + " TEXT,"
			+ KEY_ARCHIVED + " INTEGER,"
			+ KEY_TIMESTAMP + " INTEGER" + ")";
		db.execSQL(CREATE_SHOPPING_LISTS_TABLE);
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public ShoppingListValueSet add(ShoppingListValueSet valueSet) {
		insert(valueSet.getContentValues(false));
		return getLatest();
	}

	public ShoppingListValueSet getById(int id) {
		Cursor cursor = getAll(KEY_ID + " = " + id);
		cursor.moveToFirst();

		ShoppingListValueSet returnValueSet = new ShoppingListValueSet(cursor);
		closeDB();
		return returnValueSet;
	}

	public ShoppingList getByOnlineKey(String key) {
		Cursor cursor = getAll(KEY_ONLINE_KEY + " = '" + key + "'");
		cursor.moveToFirst();

		ShoppingListValueSet returnValueSet = new ShoppingListValueSet(cursor);
		closeDB();
		return new ShoppingList(context, returnValueSet.id);
	}
	
	public ShoppingList[] getAllLists() {
		Cursor cursor = getAll();

		ShoppingList[] returnArray = new ShoppingList[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnArray[cursor.getPosition()] = new ShoppingList(context, cursor.getInt(0));
			while(cursor.moveToNext());

		closeDB();
		return returnArray;
	}

	public void update(ShoppingListValueSet valueSet) {
		update(valueSet.getContentValues(), KEY_ID + " = " + valueSet.id);
	}

	public void deleteById(int id) {
		delete(KEY_ID + "=" + id);
	}

	public boolean existsTitle(String title) {
		boolean returnBoolean = getAll(KEY_TITLE + " = '" + title + "'").getCount() > 0;
		closeDB();
		return returnBoolean;
	}

	public boolean existsOnlineKey(String onlineKey) {
		boolean returnBoolean = getAll(KEY_ONLINE_KEY + " = '" + onlineKey + "'").getCount() > 0;
		closeDB();
		return returnBoolean;
	}

	private ShoppingListValueSet getLatest() {
		Cursor cursor = getAll();
		cursor.moveToLast();

		ShoppingListValueSet returnValueSet = new ShoppingListValueSet(cursor);
		closeDB();
		return returnValueSet;
	}
}
