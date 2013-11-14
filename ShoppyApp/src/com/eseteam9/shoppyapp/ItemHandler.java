package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class ItemHandler extends LocalDatabaseHandler{
	private final Context context;
	
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
		this.context = context;
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
	    
	    public ItemValueSet add(ItemValueSet valueSet) {
	    	SQLiteDatabase db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        int bought = valueSet.bought ? 1 : 0;
	        
	        values.put(KEY_NAME, valueSet.name);
	        values.put(KEY_LIST_ID, valueSet.listId);
	        values.put(KEY_QUANTITY, valueSet.quantity);
	        values.put(KEY_BOUGHT, bought);
	        values.put(KEY_ONLINEKEY, valueSet.onlineKey);

	        db.insert(TABLE_NAME, null, values);
	        
	        db.close();
	        db = this.getWritableDatabase();
	        
	        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        cursor.moveToLast();
	        ItemValueSet returnValueSet = new ItemValueSet(cursor);
	        
	        db.close();
	        return returnValueSet;
	    }
	    
	    public void deleteById(int id) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_NAME, KEY_ID + "=" + id, null);
	        db.close();
	    }
	    
	    public void deleteByListId(int listId) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        db.delete(TABLE_NAME, KEY_LIST_ID + "=" + listId, null);
	        db.close();
	    }
	    
	    public ItemValueSet getById(int id) {
	        SQLiteDatabase db = this.getWritableDatabase();

	        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        if (cursor != null)
	            cursor.moveToFirst();
	        
	        ItemValueSet valueSet = new ItemValueSet(cursor);
	        db.close();
	        return valueSet;
	    }
	    
	    public Item[] getUnboughtByListId(int listId){
	        SQLiteDatabase db = this.getWritableDatabase();
	        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId;
	        Cursor cursor = db.rawQuery(selectQuery, null);

	        Item returnArray[] = new Item[cursor.getCount()];
	        
	        if (cursor.moveToFirst()) {
	                int i = 0;
	            do {
	                    returnArray[i] = new Item(context, cursor);
	                    i++;
	            } while (cursor.moveToNext());
	        }
	        
	        db.close();
	        return returnArray;
	    }
	    
	    public Item[] getByListId(int listId) {
	        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_LIST_ID + " = " + listId + " ORDER BY " + KEY_BOUGHT;
	        
	        SQLiteDatabase db = this.getWritableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	     
	        Item returnArray[] = new Item[cursor.getCount()];
	        
	        if (cursor.moveToFirst()) {
	        	int i = 0;
	            do {
	            	returnArray[i] = new Item(context, cursor);
	            	i++;
	            } while (cursor.moveToNext());
	        }

	        db.close();
	        return returnArray;
	    }
	    
		public void update(ItemValueSet valueSet) {
		  	  SQLiteDatabase db = this.getWritableDatabase();
		  	  SQLiteStatement stmt = db.compileStatement("UPDATE " + TABLE_NAME + " SET "
		  			  + KEY_ONLINEKEY + " = '" + valueSet.onlineKey + "', "
		  			  + KEY_LIST_ID + " = " + valueSet.listId + ", "
		  			  + KEY_NAME + " = '" + valueSet.name + "', "
		  			  + KEY_QUANTITY + " = '" + valueSet.quantity + "', "
		  			  + KEY_BOUGHT + " = " + (valueSet.bought ? 1 : 0) + " "
		  			  + "WHERE " + KEY_ID + " = " + valueSet.id );
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

		public int getCount(int listId){
	        SQLiteDatabase db = this.getWritableDatabase();
	        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_LIST_ID + " = " + listId;
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        
	        int returnInt = cursor.getCount();
	        db.close();
	        return returnInt;
		}
		
		public int getUnboughtCount(int listId){
			SQLiteDatabase db = this.getWritableDatabase();
	        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_BOUGHT + " = " + 0 + " AND " + KEY_LIST_ID + " = " + listId;
	        Cursor cursor = db.rawQuery(selectQuery, null);

	        int returnInt = cursor.getCount();
	        db.close();
	        return returnInt;
		}
}
