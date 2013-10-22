package com.eseteam9.shoppyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class New_LocalDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "local.db";
 
    public New_LocalDatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        AbstractHandler.setHandler(this);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        UserHandler.onCreate(db);
        ShoppingListHandler.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	UserHandler.dropTable(db);
    	ShoppingListHandler.dropTable(db);
    	
        onCreate(db);
    }
    
    public boolean existsDatabase () {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + UserHandler.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0)
        	return true;
        db.close();
        return false;
    }
}