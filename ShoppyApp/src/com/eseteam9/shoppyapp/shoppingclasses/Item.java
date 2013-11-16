package com.eseteam9.shoppyapp.shoppingclasses;

import java.util.Date;

import com.eseteam9.shoppyapp.handlers.ItemHandler;
import com.eseteam9.shoppyapp.valuesets.ItemValueSet;
import android.content.Context;
import android.database.Cursor;

public class Item {
	private final Context context;

	private int id;
		public int id(){return this.id;}

	private String onlineKey;
		public String onlineKey(){return this.onlineKey;}
		public void onlineKey(String onlineKey){this.onlineKey = onlineKey; update();}

	private int listId;
		public int listId(){return this.listId;}
		public void listId(int listId){this.listId = listId; update();}

	private String name;
		public String name(){return this.name;}
		public void name(String name){this.name = name; update();}

	private String quantity;
		public String quantity(){return this.quantity;}
		public void quantity(String quantity){this.quantity = quantity; update();}

	private boolean bought;
		public boolean bought(){return this.bought;}
		public void bought(boolean bought){this.bought = bought; update();}

	private Date timestamp;
		public Date timestamp(){return timestamp;}
		public void timestamp(Date timestamp){this.timestamp = timestamp; update();}

	public Item(Context context, int listId, String name, String quantity) {
		this.context = context;
		ItemValueSet valueSet = lHandler().add(new ItemValueSet(listId, name, quantity));
		copyValues(valueSet);
	}

	public Item(Context context, int id) {
		this.context = context;
		loadById(id);
	}

	private void update() {
		lHandler().update(new ItemValueSet(id, onlineKey, listId, name, quantity, bought, timestamp));
		loadById(id);
	}

	private void loadById(int id) {
		copyValues(lHandler().getById(id));
	}

	private ItemHandler lHandler() {
		return new ItemHandler(context);
	}

	private void copyValues(ItemValueSet set) {
		this.id = set.id;
		this.listId = set.listId;
		this.onlineKey = set.onlineKey;
		this.name = set.name;
		this.quantity = set.quantity;
		this.bought = set.bought;
		this.timestamp = set.timestamp;
	}
}
