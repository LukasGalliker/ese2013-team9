package com.eseteam9.shoppyapp.test;

import com.eseteam9.shoppyapp.*;
import android.content.Context;

public class ShoppingListHandlerTest extends EmulatorTestclass {
	private Context context;
	private ShoppingList[] shoppingList;
	
	public ShoppingListHandlerTest(Context context){
		super("ShoppingListHandler-test");
		this.context = context;
	}
	
	protected void setUp(){
		shoppingList = new ShoppingList[] {
				new ShoppingList("dinner","fred"),
				new ShoppingList("to buy","max")};
	}
	
	public boolean run(){start();
		caseCreateHandler();
		caseAddShoppingLists();
		caseGetAllShoppingLists();
		caseDeleteShoppingList();
		caseCountShoppingList();
		caseExistsEntry();
		caseUpdate();
	return reportAndEnd();}
	
	private void caseCreateHandler() {startCase("create handler"); try {
		new ShoppingListHandler(context);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseAddShoppingLists() {startCase("add two shopping lists"); try {
		new ShoppingListHandler(context).add(shoppingList[0]);
		new ShoppingListHandler(context).add(shoppingList[1]);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseGetAllShoppingLists() {startCase("get all shopping lists"); try {
		ShoppingList[] getList = new ShoppingListHandler(context).getAll();
		
		assertTrue(getList.length == 2);
		assertTrue(getList[0].title.equals(shoppingList[0].title));
		assertTrue(getList[0].owner.equals(shoppingList[0].owner));
		assertTrue(getList[1].title.equals(shoppingList[1].title));
		assertTrue(getList[1].owner.equals(shoppingList[1].owner));
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseDeleteShoppingList() {startCase("delete shopping list"); try {
		new ShoppingListHandler(context).delete((new ShoppingListHandler(context).getAll()[0].id));
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseCountShoppingList() {startCase("count shopping lists"); try {
		new ShoppingListHandler(context).getCount();
		
		int count = new ShoppingListHandler(context).getAll().length;
		assertTrue(new ShoppingListHandler(context).getCount() == count);
		
		new ShoppingListHandler(context).add(shoppingList[0]);
		assertTrue(new ShoppingListHandler(context).getCount() == count+1);
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}

	private void caseExistsEntry() {startCase("exists shopping list by name"); try {
		assertTrue(new ShoppingListHandler(context).existsEntry("existsTest") == false);
		new ShoppingListHandler(context).add(new ShoppingList("existsTest",""));
		assertTrue(new ShoppingListHandler(context).existsEntry("existsTest"));
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
	
	private void caseUpdate() {startCase("update shopping list"); try {
		new ShoppingListHandler(context).update(new ShoppingListHandler(context).getAll()[0].id, "updateTest");
		assertTrue(new ShoppingListHandler(context).getAll()[0].title.equals("updateTest"));
	trySucceeded();} catch (Exception e) {tryFailed(e);} endCase();}
}