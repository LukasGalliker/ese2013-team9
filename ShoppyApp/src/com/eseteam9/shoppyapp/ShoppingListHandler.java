package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ShoppingListHandler extends LocalDatabaseHandler{
	private final Context context;
	
    public static final String TABLE_NAME = "shopping_lists";
    
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_ONLINE_KEY = "onlineKey";
    private static final String KEY_ARCHIVED = "archived";
    private static final String KEY_TIMESTAMP = "timestamp";
    
    public ShoppingListHandler(Context context) {
    	super(context);
    	this.context = context;
    }
    
    public static void createTable(SQLiteDatabase db) {
        String CREATE_SHOPPING_LISTS_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        		+ KEY_TITLE + " TEXT, "
                + KEY_ONLINE_KEY + " TEXT,"
                + KEY_ARCHIVED + " INTEGER,"
                + KEY_TIMESTAMP + "DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
        db.execSQL(CREATE_SHOPPING_LISTS_TABLE);
    }
    
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
    public ShoppingListValueSet add(ShoppingListValueSet valueSet) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, valueSet.title);
        values.put(KEY_ONLINE_KEY, valueSet.onlineKey);
        values.put(KEY_ARCHIVED, valueSet.archived ? 1 : 0);

        db.insert(TABLE_NAME, null, values);
        
        db.close();
        db = this.getWritableDatabase();
        
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        ShoppingListValueSet returnValueSet = new ShoppingListValueSet(cursor);
        
        db.close();
        return returnValueSet;
    }
    
    public ShoppingListValueSet getById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        ShoppingListValueSet valueSet = new ShoppingListValueSet(cursor);
        db.close();
        return valueSet;
    }
    
    public ShoppingList[] getAll() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        ShoppingList returnArray[] = new ShoppingList[cursor.getCount()];
     
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
                returnArray[i] = new ShoppingList(context, cursor);
                i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return returnArray;
    }
    
    public void update(ShoppingListValueSet valueSet) {
    	  SQLiteDatabase db = this.getWritableDatabase();
    	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET "
    			  + KEY_TITLE + " = '" + valueSet.title + "', "
    			  + KEY_ONLINE_KEY + " = '" + valueSet.onlineKey + "', "
    			  + KEY_ARCHIVED + " = " + (valueSet.archived ? 1 : 0) + " "
    			  + "WHERE " + KEY_ID + " = " + valueSet.id );
    	  stmt.execute();
    	  db.close();
      }
    
    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }
    
    public boolean existsTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " = '" + title + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        	return true;
        db.close();
        return false;
    }
    
    public boolean existsOnlineKey(String onlineKey) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ONLINE_KEY + " = '" + onlineKey + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        	return true;
        db.close();
        return false;
    }
}
