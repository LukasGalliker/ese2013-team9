package com.eseteam9.shoppyapp.shoppingclasses;

/**
 * This class provides the main structure of a notification.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class Notification {
	private String key;
	public String onlineKey(){return key;}
	public void onlineKey(String key){this.key = key;}
	
	private String data;
	public String data(){return data;}
	public void data(String data){this.data = data;}
	
	private String message;
	public String message(){return message;}
	public void message(String message){this.message = message;}	

	
	public Notification(String key, String data, String message){
		this.key = key;
		this.data = data;
		this.message = message;
	}
	
}
