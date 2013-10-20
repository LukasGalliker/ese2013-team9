
package com.eseteam9.shoppyapp;

public class ShoppingList {
	public final int id;
	public final String title;
	public final String owner;
	public final boolean bought;
	public final String timestamp;

	public ShoppingList (int id, String title, String owner, boolean bought, String timestamp) {
		this.id = id;
		this.title = title;
		this.owner = owner;
		this.bought = bought;
		this.timestamp = timestamp;
	}
	
	public ShoppingList (String title, String owner) {
		this.id = 0;
		this.title = title;
		this.owner = owner;
		this.bought = false;
		this.timestamp = null;
	}
}
