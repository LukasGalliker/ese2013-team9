package com.eseteam9.shoppyapp;

import android.content.Context;

public abstract class ShoppingLists {
	public static ShoppingList[] getAll(Context context){
		return new ShoppingListHandler(context).getAll();
	}
	
	public static boolean existsTitle(Context context, String title){
		return new ShoppingListHandler(context).existsTitle(title);
	}
	
	public static boolean existsOnlineKey(Context context, String onlineKey){
		return new ShoppingListHandler(context).existsOnlineKey(onlineKey);
	}
}
