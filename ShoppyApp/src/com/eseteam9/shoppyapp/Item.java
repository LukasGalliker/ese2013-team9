package com.eseteam9.shoppyapp;

public class Item {
	public final int id;
	public final String name;
	public final int quantity;
	public final boolean bought;
	public final String timestamp;
	
	public Item(int id, String name, int quantity, boolean bought, String timestamp) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.bought = bought;
		this.timestamp = timestamp;
	}
	
	public Item(String name, int quantity) {
		this.id = 0;
		this.name = name;
		this.quantity = quantity;
		this.bought = false;
		this.timestamp = null;
	}
}
