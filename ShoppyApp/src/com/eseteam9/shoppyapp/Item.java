package com.eseteam9.shoppyapp;

import java.util.Date;

public class Item {
	public final int id;
	public final int listId;
	public final String name;
	public final String quantity;
	public final String onlineKey;
	public final boolean bought;
	public final Date timestamp;
	
	public Item(int id, int listId, String name, String quantity, boolean bought, Date timestamp, String onlineKey) {
		this.id = id;
		this.listId = listId;
		this.name = name;
		this.quantity = quantity;
		this.bought = bought;
		this.timestamp = timestamp;
		this.onlineKey = onlineKey;
	}
	
	public Item(String name, String quantity, int listId) {
		this.id = 0;
		this.listId = listId;
		this.name = name;
		this.quantity = quantity;
		this.bought = false;
		this.timestamp = null;
		this.onlineKey = "0";
	}
}