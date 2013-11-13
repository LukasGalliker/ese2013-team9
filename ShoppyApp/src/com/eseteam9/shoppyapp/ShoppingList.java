package com.eseteam9.shoppyapp;

import java.util.Date;
/**
 * This class provides the main structure of a ShoppingList.
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class ShoppingList {
	public final int id;
	public final String title;
	public final String onlineKey;
	public final boolean archived;
	public final Date timestamp;

	public ShoppingList (int id, String title, String onlineKey, boolean archived, Date timestamp) {
		this.id = id;
		this.title = title;
		this.onlineKey = onlineKey;
		this.archived = archived;
		this.timestamp = timestamp;
	}
	
	public ShoppingList (String title) {
		this.id = 0;
		this.title = title;
		this.onlineKey = "0";
		this.archived = false;
		this.timestamp = null;
	}
	
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof ShoppingList))return false;
	    ShoppingList that = (ShoppingList)other;
	    
	    return this.title.equals(that.title)
	    		&& this.onlineKey.equals(that.onlineKey)
	    		&& this.archived == that.archived;
	}
}