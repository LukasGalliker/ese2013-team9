package com.eseteam9.shoppyapp.valuesets;

import com.eseteam9.shoppyapp.handlers.UserHandler;
import android.content.ContentValues;
import android.database.Cursor;

public class UserValueSet extends AbstractValueSet {
	public final int id;
	public final String name, email;

	public UserValueSet(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public UserValueSet(String name, String email) {
		this.id = 0;
		this.name = name;
		this.email = email;
	}

	public UserValueSet(Cursor c) {
		this.id = Integer.parseInt(c.getString(0));
		this.name = c.getString(1);
		this.email = c.getString(2);
	}

	public ContentValues getContentValues(boolean withId) {
		ContentValues returnValues = new ContentValues();
		if(withId) returnValues.put(UserHandler.KEY_ID, id);
		returnValues.put(UserHandler.KEY_NAME, name);
		returnValues.put(UserHandler.KEY_EMAIL, email);
		return returnValues;
	}

	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof UserValueSet)) return false;
		UserValueSet that = (UserValueSet)other;

		return this.name.equals(that.name)
	    		&& this.email.equals(that.email);
	}
}
