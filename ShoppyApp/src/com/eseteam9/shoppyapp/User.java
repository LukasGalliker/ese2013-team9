package com.eseteam9.shoppyapp;

public class User {
	public final int id;
	public final String name, key;

	public User (int id, String name, String key) {
		this.id = id;
		this.name = name;
		this.key = key;
	}
	
	public User (String name, String key) {
		this.id = 0;
		this.name = name;
		this.key = key;
	}
}
