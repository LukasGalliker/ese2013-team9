package test;

import junit.framework.TestCase;

import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;

import java.util.Date;

public class ShoppingListTest extends TestCase {
	
	public void testThinConstructor(){
		new ShoppingList(null);
		
		ShoppingList testShoppingList = new ShoppingList("Testtitle");
		
		assertEquals(testShoppingList.id, 0);
		assertEquals(testShoppingList.title, "Testtitle");
		assertEquals(testShoppingList.onlineKey, "0");
		assertEquals(testShoppingList.archived, false);
		assertEquals(testShoppingList.timestamp, null);
	}
	
	public void testThickConstructor(){
		new ShoppingList(0, null, null, false, null);
		
		Date timestamp = new Date();
		ShoppingList testShoppingList = new ShoppingList(10, "Testtitle", "Testowner", true, timestamp);
		
		assertEquals(testShoppingList.id, 10);
		assertEquals(testShoppingList.title, "Testtitle");
		assertEquals(testShoppingList.onlineKey, "Testowner");
		assertEquals(testShoppingList.archived, true);
		assertEquals(testShoppingList.timestamp, timestamp);
	}
	
	public void testEqualsMethod(){
		Date[] date = {new Date(), new Date()};
		ShoppingList[] sList = {new ShoppingList(0, "title1", "1234", true, date[0]),
				new ShoppingList(10, "title1", "1234", true, date[1]),
				new ShoppingList(0, "title1", "1234", false, date[0]),
				new ShoppingList(0, "title1", "4321", true, date[0]),
				new ShoppingList(0, "title2", "4321", true, date[0])};
		
		assertFalse(sList[0].equals(null));
		assertFalse(sList[0].equals(true));
		assertEquals(sList[0], sList[0]);
		assertEquals(sList[0], sList[1]);
		assertFalse(sList[1].equals(sList[2]));
		assertFalse(sList[1].equals(sList[3]));
		assertFalse(sList[1].equals(sList[4]));
		assertFalse(sList[2].equals(sList[3]));
		assertFalse(sList[3].equals(sList[4]));
	}
}
