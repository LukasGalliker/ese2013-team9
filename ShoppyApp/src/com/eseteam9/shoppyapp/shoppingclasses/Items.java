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
	
	public static boolean existsOnlineKey(Context context, String onlineKey){
		return new ItemHandler(context).existsOnlineKey(onlineKey);
	}
	
	public static boolean existsName(Context context, int listId, String name){
		return new ItemHandler(context).existsName(listId, name);
	}
	
	public static Item getByOnlineKey(Context context, String key){
		return new ItemHandler(context).getByOnlineKey(key);
	}
	
	public static Item getByName(Context context, int listId, String name){
		return new ItemHandler(context).getByName(listId, name);
	}

	public static String[] getAllNames(Context context){
		return new ItemHandler(context).getAllRow(2);
	}
	
	public static String[] getAllKeys(Context context){
		return new ItemHandler(context).getAllRow(6);
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
	
	public static void deleteByKey(Context context, String key){
		new ItemHandler(context).deleteByKey(key);
	}

	public static void deleteByListId(Context context, int listId){
		new ItemHandler(context).deleteByListId(listId);
	}
}
