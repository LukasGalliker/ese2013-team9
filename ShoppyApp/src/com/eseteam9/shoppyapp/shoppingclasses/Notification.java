package com.eseteam9.shoppyapp.shoppingclasses;

import android.content.Context;
import com.eseteam9.shoppyapp.handlers.NotificationHandler;
import com.eseteam9.shoppyapp.valuesets.NotificationValueSet;

/**
 * This class provides the main structure of a notification.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class Notification {
	public Context context;

	private int id;
	public int id(){return id;}
	
	private String key;
	public String onlineKey(){return key;}
	public void onlineKey(String key){this.key = key;}
	
	public String data;
	public String data(){return data;}
	public void data(String data){this.data = data;}
	
	public String message;
	public String message(){return message;}
	public void message(String message){this.message = message;}	

	public Notification(Context context, String key, String data, String message){
		this.context = context;
		NotificationValueSet valueSet = nHandler().add(new NotificationValueSet(key, data, message));
		copyValues(valueSet);
	}
	
	public Notification(String key, String data, String message){
		copyValues(new NotificationValueSet(key, data, message));
	}
	
	public Notification(Context context, int id){
		this.context = context;
		loadById(id);
	}
	
	private NotificationHandler nHandler() {
		return new NotificationHandler(context);
	}
	
	private void loadById(int id) {
		copyValues(nHandler().getById(id));
	}
	
	private void copyValues(NotificationValueSet set) {
		this.id = set.id;
		this.key = set.key;
		this.data = set.data;
		this.message = set.message;
	}
}
