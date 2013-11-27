package com.eseteam9.shoppyapp.shoppingclasses;

import com.eseteam9.shoppyapp.handlers.UserHandler;
import com.eseteam9.shoppyapp.valuesets.UserValueSet;

import android.content.Context;

public abstract class Users {
	public static User getOwner(Context context){
		return new UserHandler(context).getOwner();
	}

	public static String[] getAllNames(Context context){
		return new UserHandler(context).getAllNames();
	}

	public static boolean existsUser(Context context){
		return new UserHandler(context).existsUser();
	}

	public static boolean existsUserByEmail(Context context, String email){
		return new UserHandler(context).existsUserByEmail(email);
	}
	
	public static void add(Context context, String email, String name){
		new UserHandler(context).add(new UserValueSet(email, name));
	}
}
