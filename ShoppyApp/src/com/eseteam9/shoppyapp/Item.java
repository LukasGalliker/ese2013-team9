package com.eseteam9.shoppyapp;

import java.util.Date;

public class Item {
	public final int id;
	public final int listId;
	public final String name;
	public final String quantity;
	public final boolean bought;
	public final Date timestamp;
	
	public Item(int id, int listId, String name, String quantity, boolean bought, Date timestamp) {
		this.id = id;
		this.listId = listId;
		this.name = name;
		this.quantity = quantity;
		this.bought = bought;
		this.timestamp = timestamp;
	}
	
	public Item(String name, String quantity, int listId) {
		this.id = 0;
		this.listId = listId;
		this.name = name;
		this.quantity = quantity;
		this.bought = false;
		this.timestamp = null;
	}
}