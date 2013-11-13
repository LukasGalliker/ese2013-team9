package com.eseteam9.shoppyapp;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
/**
 * This class is responsible that action on the items (e.g. adding items to a list, deleting items, check items ) 
 * are saved correctly and it provides methods to let other classes display all items in a list, all unbought items etc..
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends LocalDatabaseHandler
 */
public class ItemHandler extends LocalDatabaseHandler{
    public static final String TABLE_NAME = "items";
    
    private static final String KEY_ID = "id";
    private static final String KEY_LIST_ID = "listid";
    private static final String KEY_NAME = "name";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_BOUGHT = "bought";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_ONLINEKEY = "onlinekey";
    
    public ItemHandler(Context context) {
    	super(context);
    }
    
    public static void createTable(SQLiteDatabase db) {
    	String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
    			+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LIST_ID
    			+ " INTEGER,"+ KEY_NAME + " TEXT," + KEY_QUANTITY + " TEXT,"
    			+ KEY_BOUGHT + " INTEGER," + KEY_TIMESTAMP
    			+ " DATETIME DEFAULT CURRENT_TIMESTAMP," + KEY_ONLINEKEY + " TEXT" + ")";
    	db.execSQL(CREATE_TABLE);
    }
    
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
    
    public void add(Item item) {
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int bought = item.bought ? 1 : 0;
        
        values.put(KEY_NAME, item.name);
        values.put(KEY_LIST_ID, item.listId);
        values.put(KEY_QUANTITY, item.quantity);
        values.put(KEY_BOUGHT, bought);
        values.put(KEY_ONLINEKEY, item.onlineKey);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
        db.close();
    }
    
    public void deleteListItems(int listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_LIST_ID + "=" + listId, null);
        db.close();
    }
    
    public void addListItems(int listId, Item[] items) {
    	for (Item item: items){
    		SQLiteDatabase db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        int bought = item.bought ? 1 : 0;
	        
	        values.put(KEY_NAME, item.name);
	        values.put(KEY_LIST_ID, listId);
	        values.put(KEY_QUANTITY, item.quantity);
	        values.put(KEY_BOUGHT, bought);
	        values.put(KEY_ONLINEKEY, item.onlineKey);
	        
	        db.insert(TABLE_NAME, null, values);
	        db.close();
    	}
    }
    
    public Item get(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        Item item = parseItem(cursor);
        db.close();
        return item;
    }
    
    public Item[] getListItems(int listId) {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_LIST_ID + " = " + listId + " ORDER BY " + KEY_BOUGHT;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        Item returnArray[] = new Item[cursor.getCount()];
        
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
            	returnArray[i] = parseItem(cursor);
            	i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return returnArray;
    }
    
    public void checked(int id, boolean status) {
    	  SQLiteDatabase db = this.getWritableDatabase();
    	  //db.update(TABLE_SHOPPING_LISTS, null, S_KEY_ID+ "="+ id, null);	
    	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " + KEY_BOUGHT + " = " + (status ? 1 : 0) + " WHERE "+ KEY_ID +" = "+ id );
    	  stmt.execute();
    	  db.close();
    }
    
    public int getCount(int listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_LIST_ID + " = " + listId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        int returnInt = cursor.getCount();
        db.close();
        return returnInt;
    }
    
    public int getCountUnbought(int listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int returnInt = cursor.getCount();
        db.close();
        return returnInt;
    }
    
    public Item[] getUnbought(int listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        Item returnArray[] = new Item[cursor.getCount()];
        
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
            	returnArray[i] = parseItem(cursor);
            	i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return returnArray;
    }
    
    private Item parseItem(Cursor c) {
    	return new Item(c.getInt(0),
    			c.getInt(1),
    			c.getString(2),
    			c.getString(3),
                c.getInt(4) == 1,
                new Date(c.getLong(5)),
                c.getString(6));
    }

	public void update(int itemId, String itemname, String quantity) {
	  	  SQLiteDatabase db = this.getWritableDatabase();
	  	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " + KEY_NAME + " = '" + itemname + "', " + KEY_QUANTITY + " = '" + quantity + "' WHERE "+ KEY_ID +" = "+ itemId );
	  	  stmt.execute();
	  	  db.close();
		
	}
	
	public void updateOnlineKey(int itemId, String key) {
	  	  SQLiteDatabase db = this.getWritableDatabase();
	  	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET " + KEY_ONLINEKEY + " = '" + key + "' WHERE "+ KEY_ID +" = "+ itemId );
	  	  stmt.execute();
	  	  db.close();
		
	}
	
	public String[] getAllNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        String names[] = new String[cursor.getCount()];
        
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
            	names[i] = cursor.getString(2);
            	i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return names;
	}
}