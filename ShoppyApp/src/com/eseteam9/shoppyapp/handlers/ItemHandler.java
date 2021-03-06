package com.eseteam9.shoppyapp.handlers;

import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.valuesets.ItemValueSet;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ItemHandler extends ObjectHandler {
	public static final String TABLE_NAME = "items";

	public static final String KEY_ID = "id";
	public static final String KEY_ONLINE_KEY = "onlinekey";
	public static final String KEY_LIST_ID = "listid";
	public static final String KEY_NAME = "name";
	public static final String KEY_QUANTITY = "quantity";
	public static final String KEY_BOUGHT = "bought";
	public static final String KEY_TIMESTAMP = "timestamp";

	public ItemHandler(Context context) {
		super(context, TABLE_NAME);
	}

	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_LIST_ID + " INTEGER,"
				+ KEY_NAME + " TEXT,"
				+ KEY_QUANTITY + " TEXT,"
				+ KEY_BOUGHT + " INTEGER,"
				+ KEY_TIMESTAMP + " INTEGER,"
				+ KEY_ONLINE_KEY + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

	public ItemValueSet add(ItemValueSet valueSet) {
		insert(valueSet.getContentValues(false));
		return getLatestRow();
	}

	public ItemValueSet getById(int id) {
		Cursor cursor = getAll(KEY_ID + " = " + id);
		cursor.moveToFirst();

		ItemValueSet returnValueSet = new ItemValueSet(cursor);
		closeDB();
		return returnValueSet;
	}

	public Item[] getByListId(int listId) {
		Cursor cursor = getAll(KEY_LIST_ID + " = " + listId + " ORDER BY " + KEY_BOUGHT);

		Item[] returnArray = new Item[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnArray[cursor.getPosition()] = new Item(context, cursor.getInt(0));
			while(cursor.moveToNext());

		closeDB();
		return returnArray;
	}

	public Item[] getUnboughtByListId(int listId) {
		Cursor cursor = getAll(KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId);

		Item[] returnArray = new Item[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnArray[cursor.getPosition()] = new Item(context, cursor.getInt(0));
			while(cursor.moveToNext());

		closeDB();
		return returnArray;
	}

	public void update(ItemValueSet valueSet) {
		update(valueSet.getContentValues(), KEY_ID + " = " + valueSet.id);
	}

	public void deleteById(int id) {
		delete(KEY_ID + " = " + id);
	}

	public void deleteByListId(int listId) {
		delete(KEY_LIST_ID + " = " + listId);
	}

	public String[] getAllRow(int row) {
		Cursor cursor = getAll();

		String[] returnNames = new String[cursor.getCount()];
		if(cursor.moveToFirst())
			do returnNames[cursor.getPosition()] = cursor.getString(row);
			while(cursor.moveToNext());

		closeDB();
		return returnNames;
	}

	public int getCount(int listId) {
		int returnInt = getAll(KEY_LIST_ID + " = " + listId).getCount();
		closeDB();
		return returnInt;
	}

	public int getUnboughtCount(int listId) {
		int returnInt = getAll(KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId).getCount();
		closeDB();
		return returnInt;
	}

	private ItemValueSet getLatestRow() {
		Cursor cursor = getAll();
		cursor.moveToLast();

		ItemValueSet returnValueSet = new ItemValueSet(cursor);
		closeDB();
		return returnValueSet;
	}
	
	public boolean existsOnlineKey(String onlineKey) {
		boolean returnBoolean = getAll(KEY_ONLINE_KEY + " = '" + onlineKey + "'").getCount() > 0;
		closeDB();
		return returnBoolean;
	}
	
	public boolean existsName(int listId, String name) {
		boolean returnBoolean = getAll(KEY_NAME + " = '" + name + "' AND " + KEY_LIST_ID + " = " + listId).getCount() > 0;
		closeDB();
		return returnBoolean;
	}
	
	public Item getByOnlineKey(String key) {
		Cursor cursor = getAll(KEY_ONLINE_KEY + " = '" + key + "'");
		cursor.moveToFirst();

		ItemValueSet returnValueSet = new ItemValueSet(cursor);
		closeDB();
		return new Item(context, returnValueSet.id);
	}
	
	public Item getByName(int listId, String name) {
		Cursor cursor = getAll(KEY_NAME + " = '" + name + "' AND " + KEY_LIST_ID + " = " + listId);
		cursor.moveToFirst();

		ItemValueSet returnValueSet = new ItemValueSet(cursor);
		closeDB();
		return new Item(context, returnValueSet.id);
	}

	public void deleteByKey(String key) {
		delete(KEY_ONLINE_KEY + " = '" + key + "'");
	}
}
