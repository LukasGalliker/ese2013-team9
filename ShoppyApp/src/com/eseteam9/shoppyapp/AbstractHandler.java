package com.eseteam9.shoppyapp;

public abstract class AbstractHandler {
	protected static New_LocalDatabaseHandler dbHandler;
	
	public static void setHandler(New_LocalDatabaseHandler dbHandler) {
		AbstractHandler.dbHandler = dbHandler;
	}
}
