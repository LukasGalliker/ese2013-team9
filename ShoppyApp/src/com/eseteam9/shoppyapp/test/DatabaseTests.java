package com.eseteam9.shoppyapp.test;

import com.eseteam9.shoppyapp.LocalDatabaseHandler;
import android.content.Context;

public class DatabaseTests{
	public DatabaseTests(Context context){
		new LocalDatabaseHandler(context).onUpgrade(new LocalDatabaseHandler(context).getWritableDatabase(), 1, 1);
		
		if(
		new UserHandlerTest(context).run() &
		new ShoppingListHandlerTest(context).run() &
		new ItemHandlerTest(context).run()
		) System.out.println("test:\ntest: FINAL REPORT OK");
		else System.out.println("test:\ntest: FINAL REPORT FAIL");
		
		new LocalDatabaseHandler(context).onUpgrade(new LocalDatabaseHandler(context).getWritableDatabase(), 1, 1);
	}
}