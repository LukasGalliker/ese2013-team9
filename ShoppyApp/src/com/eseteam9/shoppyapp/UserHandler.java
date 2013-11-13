package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserHandler extends LocalDatabaseHandler{
    public static final String TABLE_NAME = "users";
    
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "key";
    
    public UserHandler(Context context) {
    	super(context);
    }
	
	public static void createTable(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}
	
	public static void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}
	
    public void add(User user) {
    	SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.name);
        values.put(KEY_EMAIL, user.email);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    
    public User get() {
    	SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor != null)
            cursor.moveToFirst();
 
        User user = parseUser(cursor);

        db.close();
        return user;
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
    
    public boolean existsUser () {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        	return true;
        db.close();
        return false;
    }
    
    public boolean existsUser (String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        	return true;
        db.close();
        return false;
    }
    
    private User parseUser(Cursor c){
    	return new User(Integer.parseInt(c.getString(0)),
                c.getString(1),
                c.getString(2));
    }
}