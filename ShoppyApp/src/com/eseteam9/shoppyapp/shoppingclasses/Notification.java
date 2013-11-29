package com.eseteam9.shoppyapp.shoppingclasses;
/**
 * This class provides the main structure of a notification.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class Notification {
	public int notificationId;
	public String email;
	public String data;

	public Notification(int notificationId, String email, String data){
		this.notificationId = notificationId;
		this.email = email;
		this.data = data;
	}
}
