package com.eseteam9.shoppyapp;

import java.util.ArrayList;
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
                	final String listKey = listObject.getObjectId();
                	new ShoppingListHandler(context).updateOnlineKey(list.id, listKey);
            		
            		Item[] items = new ItemHandler(context).getListItems(list.id);
            		List<ParseObject> parseItems = new ArrayList<ParseObject>();
            		
            	    for (int i = 0; i < items.length; i++ ){
            	    	ParseObject item = new ParseObject("Item");
            	    	item = parseItem(items[i]);
            	    	item.put("list", listKey);
            	    	parseItems.add(i, item);
            	    }
            	    
            	    ParseObject.saveAllInBackground(parseItems, new SaveCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                            	final String listKey = listObject.getObjectId();
                            	ItemHandler handler = new ItemHandler(context);
                            	handler.deleteListItems(list.id);
                            	getListItems(listKey, list.id);
                            }
                        }
                    });  
                }
            }
        });
	}
	
	public ParseObject parseItem(Item item){
		ParseObject itemObject = new ParseObject("Item");
		itemObject.put("name", item.name);
		itemObject.put("quantity", item.quantity);
		itemObject.put("bought", item.bought);
		return itemObject;
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
	
	public void getListItems(final String onlineKey, final int listId){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
		query.whereEqualTo("list", onlineKey);
		query.findInBackground(new FindCallback<ParseObject>() {
		  public void done(List<ParseObject> parseItems, ParseException e) {
		    if (e == null) {
		      Item[] items = new Item[parseItems.size()];
		      for (int i=0; i < parseItems.size(); i++){
		    	  ParseObject itemObject = parseItems.get(i);
			      Item item = new Item(listId, 
			    		  				itemObject.getString("name"), 
			    		  				itemObject.getString("quantity"), 
			    		  				itemObject.getBoolean("bought"), 
			    		  				itemObject.getDate("createdAt"), 
			    		  				itemObject.getObjectId());
			      items[i] = item;
		      }
			new ItemHandler(context).addListItems(listId, items);
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
