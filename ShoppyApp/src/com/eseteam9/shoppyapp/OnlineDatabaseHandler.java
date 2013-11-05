package com.eseteam9.shoppyapp;

import android.content.Context;
import com.parse.Parse;
import com.parse.ParseObject;

public class OnlineDatabaseHandler {
	Context context;
	
	public OnlineDatabaseHandler(Context context){
		this.context = context;
		Parse.initialize(context, "siN9uAfK3is01V4Yyad62BztutNZN761smpPFdhQ", "zWJquwUQlEw9NHvBuZFFdpMrMIQoIXRy8CjslwY3"); 
	}
	
	public void putUser(User user){
		ParseObject gameScore = new ParseObject("User");
		gameScore.put("name", user.name);
		gameScore.put("key", user.key);
		gameScore.saveInBackground();
	}
	
}
