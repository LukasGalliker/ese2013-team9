package com.eseteam9.shoppyapp;
/**
 * This class provides the main structure of a notification.
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class Notification {
	public int notificationId;
	public String key;
	
	
	Notification(int notificationId, String key){
		this.notificationId = notificationId;
		this.key = key;
	}
	
}
