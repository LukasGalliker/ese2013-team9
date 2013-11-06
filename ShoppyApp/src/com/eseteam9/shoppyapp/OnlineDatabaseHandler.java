package com.eseteam9.shoppyapp;

import java.util.List;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class OnlineDatabaseHandler {
	Context context;
	
	public OnlineDatabaseHandler(Context context){
		this.context = context;
		Parse.initialize(context, "siN9uAfK3is01V4Yyad62BztutNZN761smpPFdhQ", "zWJquwUQlEw9NHvBuZFFdpMrMIQoIXRy8CjslwY3"); 
	}
	
	public void putUser(User user){
		ParseObject onlineUser = new ParseObject("Users");
		onlineUser.put("name", user.name);
		onlineUser.put("key", user.email);
		onlineUser.saveEventually();
	}
	
	public void putList(final ShoppingList list, User user){
		final ParseObject listObject = new ParseObject("List");
		listObject.put("title", list.title);
		listObject.put("owner", user.email);
		listObject.saveEventually(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                	String listKey = listObject.getObjectId();
                	new ShoppingListHandler(context).updateOnlineKey(list.id, listKey);
            		
            		Item[] items = new ItemHandler(context).getListItems(list.id);
            		for (Item i: items)
            			putItem(listKey, i);
                }
            }
        });
	}
	
	public void putItem(String listKey, Item item){
		ParseObject itemObject = new ParseObject("Item");
		itemObject.put("name", item.name);
		itemObject.put("quantity", item.quantity);
		itemObject.put("bought", item.bought);
		itemObject.put("list", listKey);
		itemObject.saveEventually();
	}
	
	public void updateItem(String listKey, final Item item){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
		 
		query.getInBackground(item.onlineKey, new GetCallback<ParseObject>() {
		  public void done(ParseObject itemObject, ParseException e) {
		    if (e == null) {
		      itemObject.put("bought", item.bought);
		      itemObject.put("name", item.name);
		      itemObject.put("quantity", item.quantity);
		      itemObject.saveInBackground();
		    }
		  }
		});
	}
	
	public void shareList(final String onlineKey, final User[] users){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedList");
		 
		query.getInBackground(onlineKey, new GetCallback<ParseObject>() {
		  public void done(ParseObject sharedList, ParseException e) {
		    if (e == null) {
		      for (User u: users){
		    	  sharedList.put("ListKey", onlineKey);
		    	  sharedList.put("UserKey",  u.email);
		    	  sharedList.saveEventually();
		      }
		    }
		  }
		});		
	}
	
	public void getUser(final String key){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
		query.whereEqualTo("key", key);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> users, ParseException e) {
		        if (e == null) {
		        	ParseObject userObject = users.get(0);
		            User user = new User(userObject.getString("name"), key);
		            new UserHandler(context).add(user);
		        }
		    }
		});
	}
	
}
