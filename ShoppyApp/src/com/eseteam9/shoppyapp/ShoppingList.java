package com.eseteam9.shoppyapp;

import java.util.Date;

public class ShoppingList {
	public final int id;
	public final String title;
	public final String owner;
	public final boolean archived;
	public final Date timestamp;

	public ShoppingList (int id, String title, String owner, boolean archived, Date timestamp) {
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.archived = archived;
		this.timestamp = timestamp;
	}
	
	public ShoppingList (String title, String owner) {
		this.id = 0;
		this.title = title;
		this.owner = owner;
		this.archived = false;
		this.timestamp = null;
	}
}