package com.eseteam9.shoppyapp.shoppingclasses;

import java.util.Date;
import com.eseteam9.shoppyapp.handlers.ShoppingListHandler;
import com.eseteam9.shoppyapp.valuesets.ShoppingListValueSet;
import android.content.Context;

public class ShoppingList {
	private final Context context;

	private int id;
		public int id(){return id;}

	private String onlineKey;
		public String onlineKey(){return onlineKey;}
		public void onlineKey(String onlineKey){this.onlineKey = onlineKey; update();}

	private String title;
		public String title(){return title;}
		public void title(String title){this.title = title; update();}

	private boolean archived;
		public boolean archived(){return archived;}
		public void archived(boolean archived){this.archived = archived; update();}

	private Date timestamp;
		public Date timestamp(){return timestamp;}
		public void timestamp(Date timestamp){this.timestamp = timestamp; update();}

	public ShoppingList(Context context, String title) {
		this.context = context;
		ShoppingListValueSet valueSet = lHandler().add(new ShoppingListValueSet(title));
		copyValues(valueSet);
	}

	public ShoppingList(Context context, int id) {
		this.context = context;
		loadById(id);
	}

	private void update(){
		lHandler().update(new ShoppingListValueSet(id, onlineKey, title, archived, timestamp));
		loadById(id);
	}

	private void loadById(int id) {
		copyValues(lHandler().getById(id));
	}

	private ShoppingListHandler lHandler() {
		return new ShoppingListHandler(context);
	}

	private void copyValues(ShoppingListValueSet set) {
		this.id = set.id;
		this.onlineKey = set.onlineKey;
		this.title = set.title;
		this.archived = set.archived;
		this.timestamp = set.timestamp;
	}
}
