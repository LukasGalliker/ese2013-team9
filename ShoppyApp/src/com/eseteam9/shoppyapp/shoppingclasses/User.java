package com.eseteam9.shoppyapp.shoppingclasses;

import com.eseteam9.shoppyapp.handlers.UserHandler;
import com.eseteam9.shoppyapp.valuesets.UserValueSet;
import android.content.Context;

public class User {
	private final Context context;

	private int id;
		public int id(){return id;}

	private String name;
		public String name(){return name;}
		public void name(String name){this.name = name;}

	private String email;
		public String email(){return email;}
		public void email(String email){this.email = email;}

	public User(Context context, String name, String email) {
		this.context = context;
		UserValueSet valueSet = lHandler().add(new UserValueSet(name, email));
		copyValues(valueSet);
	}

	public User(Context context, int id) {
		this.context = context;
		loadById(id);
	}

	private void loadById(int id) {
		copyValues(lHandler().getById(id));
	}

	private UserHandler lHandler() {
		return new UserHandler(context);
	}

	private void copyValues(UserValueSet set) {
		this.id = set.id;
		this.name = set.name;
		this.email = set.email;
	}
}
