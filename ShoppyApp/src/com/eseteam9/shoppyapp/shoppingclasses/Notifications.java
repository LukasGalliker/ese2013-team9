package com.eseteam9.shoppyapp.shoppingclasses;

import com.eseteam9.shoppyapp.handlers.NotificationHandler;
import com.eseteam9.shoppyapp.handlers.ShoppingListHandler;
import android.content.Context;

public abstract class Notifications{
	public static Notification[] getAll(Context context){
		return new NotificationHandler(context).getAllNotifications();
	}
}
