package com.eseteam9.shoppyapp.tests;

import com.eseteam9.shoppyapp.*;
import android.content.Context;

public class UserHandlerTest extends EmulatorTestclass {
	private Context context;
	private User user;
	
	public UserHandlerTest(Context context){
		super("UserHandler-test");
		this.context = context;
	}
	
	protected void setUp(){
		user = new User("fred", "aj52h4v");
	}
	
	public boolean run(){start();
		caseCreateHandler();
		caseExistsUserWithNoEntry();
		caseAddUser();
		caseExistsUserWithEntry();
		caseGetUser();
		
		print("Note: database must be empty for this test");
	return reportAndEnd();}
	
	private void caseCreateHandler() {startCase("create handler"); try {
		new UserHandler(context);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseExistsUserWithNoEntry() {startCase("existsUser with no Entry"); try {
		assertTrue(new UserHandler(context).existsUser() == false);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseAddUser() {startCase("add user"); try {
		new UserHandler(context).add(user);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseExistsUserWithEntry() {startCase("existsUser with Entry"); try {
		assertTrue(new UserHandler(context).existsUser());
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseGetUser() {startCase("get user"); try {new UserHandler(context).get();
		assertTrue(new UserHandler(context).get() != null);
		assertTrue(user.name.equals(new UserHandler(context).get().name));
		assertTrue(user.key.equals(new UserHandler(context).get().key));
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
}
