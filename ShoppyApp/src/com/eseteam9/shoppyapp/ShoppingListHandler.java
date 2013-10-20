package com.eseteam9.shoppyapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ShoppingListHandler extends AbstractHandler{
    public static final String TABLE_NAME = "shopping_lists";
    
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_OWNER = "owner";
    private static final String KEY_ARCHIVED = "archived";
    private static final String KEY_TIMESTAMP = "timestamp";
    
    public static void onCreate(SQLiteDatabase db) {
    	String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
    			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT, " + KEY_OWNER
    			+ "TEXT, " + KEY_ARCHIVED + " BOOLEAN NOT NULL DEFAULT FALSE, " + KEY_TIMESTAMP
    			+ "DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
    	db.execSQL(CREATE_TABLE);
    }
    
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
    public static void add(ShoppingList shoppingList) {
    	//not sure if this works ...
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, shoppingList.title);
        values.put(KEY_OWNER, shoppingList.owner);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public static void delete(int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }
    
    public static boolean existsEntry(String name) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " = '" + name + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        db.close();
        if (cursor.getCount() > 0)
        	return true;
        return false;
    }    

    public static ShoppingList get(int id) {
    	//not sure if this works ...
        SQLiteDatabase db = dbHandler.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_NAME, new String[] { KEY_ID,
                KEY_TITLE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
 
        ShoppingList shoppingList = new ShoppingList(cursor.getInt(0), cursor.getString(1),
        		cursor.getString(2), cursor.getString(3) == "TRUE", cursor.getString(4));

        db.close();
        return shoppingList;
    }
    
    public static List<ShoppingList> getAll() {
    	//not sure if this works ...
        List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                ShoppingList shoppingList = new ShoppingList(cursor.getInt(0), cursor.getString(1),
                		cursor.getString(2), cursor.getString(3) == "TRUE", cursor.getString(4));
                shoppingLists.add(shoppingList);
            } while (cursor.moveToNext());
        }
        
        db.close();
        return shoppingLists;
    }
}