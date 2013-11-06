package com.eseteam9.shoppyapp;

import java.util.Date;

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
	
	public ShoppingList (String title, String owner) {
		this.id = 0;
		this.title = title;
		this.onlineKey = owner;
		this.archived = false;
		this.timestamp = null;
	}
}