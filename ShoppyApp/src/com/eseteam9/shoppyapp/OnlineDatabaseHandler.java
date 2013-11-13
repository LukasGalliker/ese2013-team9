package com.eseteam9.shoppyapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.parse.SaveCallback;
/**
 * This class communicates between the Online Database and the User.
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class OnlineDatabaseHandler {
	Context context;
	Notification[] notifications;
	
	public OnlineDatabaseHandler(Context context){
		this.context = context;
		Parse.initialize(context, "siN9uAfK3is01V4Yyad62BztutNZN761smpPFdhQ", "zWJquwUQlEw9NHvBuZFFdpMrMIQoIXRy8CjslwY3"); 
	}
	
	//USER
	public void putUser(User user){
		ParseObject onlineUser = new ParseObject("Users");
		onlineUser.put("name", user.name);
		onlineUser.put("key", user.email);
		onlineUser.saveEventually();
	}
	
	public void getUser(final String key){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Users");
		if (!new UserHandler(context).existsUser(key)){
			query.whereEqualTo("key", key);
			query.findInBackground(new FindCallback<ParseObject>() {
			    public void done(List<ParseObject> users, ParseException e) {
			        if (e == null) {
				        if (users.size()>0){
				        	ParseObject userObject = users.get(0);
				            User user = new User(userObject.getString("name"), key);
				            
				            new UserHandler(context).add(user);
			        	}
			        	else
			        		Toast.makeText(context, "No such user found", Toast.LENGTH_SHORT).show();
			        	
			        }
			        else
			        	Toast.makeText(context, "No connection. Please try again later", Toast.LENGTH_SHORT).show();
			    }
			});
		}
	}
	
	//LISTS
	public void putList(final ShoppingList list, User user){
		final ParseObject listObject = new ParseObject("List");
		listObject.put("title", list.title);
		listObject.put("owner", user.email);
		try {
			listObject.save();
	    	final String listKey = listObject.getObjectId();
	    	new ShoppingListHandler(context).updateOnlineKey(list.id, listKey);
	    	putItems(listKey, list.id);
			Toast.makeText(context, "List '" + list.title + "' is now shared", Toast.LENGTH_SHORT).show();
		} catch (ParseException e) {
			Toast.makeText(context, "No connection. Please try again later", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void getList(String listKey) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("List");
		query.getInBackground(listKey, new GetCallback<ParseObject>() {
			  public void done(ParseObject parseList, ParseException e) {
				    if (e == null) {
						ShoppingList list = new ShoppingList(0,
				    		  parseList.getString("title"), 
				    		  parseList.getObjectId(), 
				    		  false,
				    		  parseList.getDate("createdAt"));
						 new ShoppingListHandler(context).add(list);
						 list = new ShoppingListHandler(context).get(parseList.getObjectId());
					     getListItems(parseList.getObjectId(), list.id);
				     }
				 }
		});
	}
	
	public void shareList(final String onlineKey, final String email){
		ParseObject onlineUser = new ParseObject("SharedList");
		onlineUser.put("userKey", email);
		onlineUser.put("listKey", onlineKey);
		try {
			onlineUser.save();
		} catch (ParseException e) {
			Toast.makeText(context, "No connection. Please try again later", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void getSharedLists(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedList");
		User me = new UserHandler(context).get();
		query.whereEqualTo("userKey", me.email);
		query.findInBackground(new FindCallback<ParseObject>() {
			  public void done(List<ParseObject> parseLists, ParseException e) {
				    if (e == null) {
				      boolean noEntry = true;
				      for (int i=0; i < parseLists.size(); i++){
				    	  ParseObject sharedListObject = parseLists.get(i);
				    	  String key = sharedListObject.getString("listKey");
				    	  boolean existsEntry = new ShoppingListHandler(context).existsKey(key);
				    	  if (!existsEntry){
				    		  noEntry = false;
				    		  getList(key);
				    	  }
				    	  	
				      }
				      if (noEntry)
				    	  Toast.makeText(context, "No new Lists", Toast.LENGTH_SHORT).show();
				 }
			  }
		});
	}
	
	protected void syncList(final ShoppingList list) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("List");
		if (list.onlineKey != "0"){
			query.getInBackground(list.onlineKey, new GetCallback<ParseObject>() {
				  public void done(ParseObject parseList, ParseException e) {
					    if (e == null) {
					    	if (parseList != null){
							new ShoppingListHandler(context).update(list.id, parseList.getString("title"));
							//Sync items of list
					    	}
					     }
					 }
			});
		}
	}
	
	
	//ITEMS
	public void putItems(final String listKey, final int listid){
		Item[] items = new ItemHandler(context).getListItems(listid);
		List<ParseObject> parseItems = new ArrayList<ParseObject>();
		
	    for (int i = 0; i < items.length; i++ ){
	    	ParseObject item = new ParseObject("Item");
	    	item = parseItem(items[i]);
	    	item.put("list", listKey);
	    	parseItems.add(i, item);
	    }
	    
	    //Save Items and update local OnlineKey (to Change!)
	    ParseObject.saveAllInBackground(parseItems, new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                	ItemHandler handler = new ItemHandler(context);
                	handler.deleteListItems(listid);
                	getListItems(listKey, listid);
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
			Toast.makeText(context, "List downloaded", Toast.LENGTH_SHORT).show();
		    }
		  }
		});
	}
	
	public void checkItem(String itemKey, final boolean status){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
		 
		query.getInBackground(itemKey, new GetCallback<ParseObject>() {
		  public void done(ParseObject itemObject, ParseException e) {
		    if (e == null) {
		      itemObject.put("bought", status);
		      itemObject.saveInBackground();
		    }
		  }
		});
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
	
	public void deleteItem(String itemKey){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
		 
		query.getInBackground(itemKey, new GetCallback<ParseObject>() {
		  public void done(ParseObject itemObject, ParseException e) {
		    if (e == null) {
		      itemObject.deleteEventually();
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
	
	
	//NOTIFICATIONS
	public static void notify(int notificationId, User user){
			ParseObject onlineUser = new ParseObject("Notifications");
			onlineUser.put("userKey", user.email);
			onlineUser.put("notification", notificationId);
			onlineUser.saveEventually();
	}

	public Notification[] getNotifications(String email) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Notification");
		query.whereEqualTo("userKey", email);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> parseItems, ParseException e) {
		        if (e == null) {
		        	notifications = new Notification[parseItems.size()];
				    for (int i=0; i < parseItems.size(); i++){
				    	  ParseObject itemObject = parseItems.get(i);
				    	  Notification notification = new Notification(itemObject.getInt("notification"),
					    		  						itemObject.getString("userKey"));
					      notifications[i] = notification;
				      }
		        }
		        
		    }
		});
		return notifications;	
	}
	
	
}
