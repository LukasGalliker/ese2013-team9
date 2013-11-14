package com.eseteam9.shoppyapp.valuesets;

import android.database.Cursor;

public class UserValueSet {
	public final int id;
	public final String name, email;

	public UserValueSet(int id, String name, String email){
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public UserValueSet(String name, String email){
		this.id = 0;
		this.name = name;
		this.email = email;
	}
	
    public UserValueSet(Cursor c){
    	this.id = Integer.parseInt(c.getString(0));
        this.name = c.getString(1);
        this.email = c.getString(2);
    }
    
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof UserValueSet))return false;
	    UserValueSet that = (UserValueSet)other;
	    
	    return this.name.equals(that.name)
	    		&& this.email.equals(that.email);
	}
}
