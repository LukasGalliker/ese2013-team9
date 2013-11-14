package com.eseteam9.shoppyapp;
/**
 * This class provides the main structure of a notification.
 * 
 * @author S�bastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class Notification {
	public int notificationId;
	public String key;
	
	
	public Notification(int notificationId, String key){
		this.notificationId = notificationId;
		this.key = key;
	}
	
}
