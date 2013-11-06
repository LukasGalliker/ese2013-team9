package com.eseteam9.shoppyapp;

import android.content.Context;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class OnlineDatabaseHandler {
	Context context;
	
	public OnlineDatabaseHandler(Context context){
		this.context = context;
		Parse.initialize(context, "siN9uAfK3is01V4Yyad62BztutNZN761smpPFdhQ", "zWJquwUQlEw9NHvBuZFFdpMrMIQoIXRy8CjslwY3"); 
	}
	
	public void putUser(User user){
		ParseObject onlineUser = new ParseObject("User");
		onlineUser.put("name", user.name);
		onlineUser.put("key", user.key);
		onlineUser.saveEventually();
	}
	
	public void putList(ShoppingList list, User user){
		ParseObject List = new ParseObject("List");
		List.put("title", list.title);
		List.put("owner", user.key);
		List.saveEventually();
	}
	
	public void putItem(String listKey, Item item){
		ParseObject Item = new ParseObject("Item");
		Item.put("name", item.name);
		Item.put("quantity", item.quantity);
		Item.put("bought", item.bought);
		Item.put("list", listKey);
		Item.saveEventually();
	}
	
	public void shareList(final String onlineKey, final User[] users){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedList");
		 
		query.getInBackground(onlineKey, new GetCallback<ParseObject>() {
		  public void done(ParseObject sharedList, ParseException e) {
		    if (e == null) {
		      for (User u: users){
		    	  sharedList.put("ListKey", onlineKey);
		    	  sharedList.put("UserKey",  u.key);
		    	  sharedList.saveEventually();
		      }
		    }
		  }
		});		
	}
	
}