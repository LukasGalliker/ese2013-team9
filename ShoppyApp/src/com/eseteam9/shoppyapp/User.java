package com.eseteam9.shoppyapp;

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
}