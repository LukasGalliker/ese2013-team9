package com.eseteam9.shoppyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "local.db";

    public LocalDatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        UserHandler.createTable(db);
        ShoppingListHandler.createTable(db);
        ItemHandler.createTable(db);
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        UserHandler.dropTable(db);
        ShoppingListHandler.dropTable(db);
        ItemHandler.dropTable(db);

        onCreate(db);
    }
    
    protected void hExecSQL(String SQLStatement) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL(SQLStatement);
    	db.close();
    }
    
    protected Cursor hRawQuery(String sql, String[] selectionArgs) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor returnCursor = db.rawQuery(sql, selectionArgs);
    	db.close();
    	return returnCursor;
    }
    
    protected void hInsert(String table, String nullColumnHack, ContentValues values){
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.insert(table, nullColumnHack, values);
    	db.close();
    }
    
    protected void hDelete(String table, String whereClause, String[] whereArgs) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(table, whereClause, whereArgs);
    	db.close();
    }
}