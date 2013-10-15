//
// NAME		User.java
// VERION	1.0
// DATE		12-10-2013
// AUTHOR	Marc Schneiter (marc.e.schneiter@gmail.com)
//
///////////////////////////////////////////////////////////////////////////////////////

package com.eseteam9.shoppyapp;

public class User {
	public final int id;
	public final String name, key;

	public User (int id, String name, String key) {
		this.id = id;
		this.name = name;
		this.key = key;
	}
}
