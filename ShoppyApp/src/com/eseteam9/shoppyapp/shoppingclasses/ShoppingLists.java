package com.eseteam9.shoppyapp.shoppingclasses;

import com.eseteam9.shoppyapp.handlers.ShoppingListHandler;
import android.content.Context;

public abstract class ShoppingLists {
	public static ShoppingList[] getAll(Context context){
		return new ShoppingListHandler(context).getAllLists();
	}

	public static boolean existsTitle(Context context, String title){
		return new ShoppingListHandler(context).existsTitle(title);
	}

	public static boolean existsOnlineKey(Context context, String onlineKey){
		return new ShoppingListHandler(context).existsOnlineKey(onlineKey);
	}

	public static void deleteById(Context context, int id){
		new ShoppingListHandler(context).deleteById(id);
	}
	
	public static ShoppingList getByOnlineKey(Context context, String key){
		return new ShoppingListHandler(context).getByOnlineKey(key);
	}
}
