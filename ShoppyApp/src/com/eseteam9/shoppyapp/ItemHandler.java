package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ItemHandler extends AbstractHandler{
    public static final String TABLE_NAME = "shopping_lists";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_BOUGHT = "bought";
    private static final String KEY_TIMESTAMP = "timestamp";
    
    public static void onCreate(SQLiteDatabase db) {
    	String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
    			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT, " + KEY_QUANTITY
    			+ "INTEGER, " + KEY_BOUGHT + " BOOLEAN NOT NULL DEFAULT FALSE, " + KEY_TIMESTAMP
    			+ "DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
    	db.execSQL(CREATE_TABLE);
    }
    
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
    
    public static void add(Item item) {
    	//not sure if this works ...
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.name);
        values.put(KEY_QUANTITY, item.quantity);
        values.put(KEY_BOUGHT, item.bought);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public static void delete(int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }
}
