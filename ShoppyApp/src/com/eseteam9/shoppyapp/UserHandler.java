package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends LocalDatabaseHandler{
	private final Context context;
	
    public static final String TABLE_NAME = "users";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "key";
    
    public UserHandler(Context context) {
    	super(context);
    	this.context = context;
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
    	SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, valueSet.name);
        values.put(KEY_EMAIL, valueSet.email);

        db.insert(TABLE_NAME, null, values);
        db.close();
        db = this.getWritableDatabase();
        
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToLast();
        UserValueSet returnValueSet = new UserValueSet(cursor);
        
        db.close();
        return returnValueSet;
    }
    
    public UserValueSet getById(int id) {
    	SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        UserValueSet returnValueSet = new UserValueSet(cursor);

        db.close();
        return returnValueSet;
    }
    
    public User getOwner(){
    	SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        User returnUser = new User(context, cursor);

        db.close();
        return returnUser;
    }
    
	public String[] getAllNames(){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        String returnNames[] = new String[cursor.getCount()];
        
        if (cursor.moveToFirst()) {
        	int i = 0;
            do {
            	returnNames[i] = cursor.getString(2);
            	i++;
            } while (cursor.moveToNext());
        }

        db.close();
        return returnNames;
	}
    
    public boolean existsUser(){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0){
        	db.close();
        	return true;
        }
        db.close();
        return false;
    }
    
    public boolean existsUserByEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0){
        	db.close();
        	return true;
        }
        db.close();
        return false;
    }
}
