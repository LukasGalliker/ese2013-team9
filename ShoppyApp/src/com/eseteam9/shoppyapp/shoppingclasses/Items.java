package com.eseteam9.shoppyapp.shoppingclasses;

import com.eseteam9.shoppyapp.handlers.ItemHandler;

import android.content.Context;

public abstract class Items {
	public static Item[] getByListId(Context context, int listId){
		return new ItemHandler(context).getByListId(listId);
	}
	
	public static Item[] getUnboughtByListId(Context context, int listId){
		return new ItemHandler(context).getUnboughtByListId(listId);
	}
	
	public static String[] getAllNames(Context context){
		return new ItemHandler(context).getAllNames();
	}
	
	public static int getCount(Context context, int listId){
		return new ItemHandler(context).getCount(listId);
	}
	
	public static int getUnboughtCount(Context context, int listId){
		return new ItemHandler(context).getUnboughtCount(listId);
	}
	
	public static void deleteById(Context context, int id){
		new ItemHandler(context).deleteById(id);
	}
	
	public static void deleteByListId(Context context, int listId){
		new ItemHandler(context).deleteByListId(listId);
	}
}