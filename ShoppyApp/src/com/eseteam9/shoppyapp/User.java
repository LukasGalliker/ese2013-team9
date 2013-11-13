package com.eseteam9.shoppyapp;
/**
 * This class provides the main structure of an User.
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 */
public class User {
	public final int id;
	public final String name, email;

	public User (int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public User (String name, String email) {
		this.id = 0;
		this.name = name;
		this.email = email;
	}
	
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof User))return false;
	    User that = (User)other;
	    
	    return this.name.equals(that.name)
	    		&& this.email.equals(that.email);
	}
}