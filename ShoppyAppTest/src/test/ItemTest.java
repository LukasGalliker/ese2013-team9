package test;

import junit.framework.TestCase;

import com.eseteam9.shoppyapp.shoppingclasses.Item;

import java.util.Date;

public class ItemTest extends TestCase {

	public void testThinConstructor(){
		new Item(null, null, 0);
		
		Item testItem = new Item("Testname", "12345", 10);
		
		assertEquals(testItem.id, 0);
		assertEquals(testItem.listId, 10);
		assertEquals(testItem.name, "Testname");
		assertEquals(testItem.quantity, "12345");
		assertEquals(testItem.bought, false);
		assertEquals(testItem.timestamp, null);
		assertEquals(testItem.onlineKey, "0");
	}
	
	public void testMediumConstructor(){
		new Item(0, null, null, false, null, null);
		
		Date timestamp = new Date();
		Item testItem = new Item(10, "Testname", "12345", true, timestamp, "10");
		
		assertEquals(testItem.id, 0);
		assertEquals(testItem.listId, 10);
		assertEquals(testItem.name, "Testname");
		assertEquals(testItem.quantity, "12345");
		assertEquals(testItem.bought, true);
		assertEquals(testItem.timestamp, timestamp);
		assertEquals(testItem.onlineKey, "10");
	}
	
	public void testThickConstructor(){
		new Item(0, 0, null, null, false, null, null);
		
		Date timestamp = new Date();
		Item testItem = new Item(20, 10, "Testname", "12345", true, timestamp, "10");
		
		assertEquals(testItem.id, 20);
		assertEquals(testItem.listId, 10);
		assertEquals(testItem.name, "Testname");
		assertEquals(testItem.quantity, "12345");
		assertEquals(testItem.bought, true);
		assertEquals(testItem.timestamp, timestamp);
		assertEquals(testItem.onlineKey, "10");
	}
	
	public void testSetListIdMethod(){
		Item testItem = new Item(null, null, 0);
		assertEquals(testItem.listId, 0);
		
		testItem.setListId(10);
		assertEquals(testItem.listId, 10);
	}
	
	public void testEqualsMethod(){
		Date[] date = {new Date(), new Date()};
		Item[] item = {new Item(0, 0, "name1", "1", true, date[0], "1234"),
				new Item(10, 0, "name1", "1", true, date[1], "1234"),
				new Item(0, 0, "name1", "1", false, date[0], "1234"),
				new Item(0, 0, "name1", "2", true, date[0], "4321"),
				new Item(0, 1, "name2", "1", true, date[0], "1234")};
		
		assertFalse(item[0].equals(null));
		assertFalse(item[0].equals(true));
		assertEquals(item[0], item[0]);
		assertEquals(item[0], item[1]);
		assertFalse(item[1].equals(item[2]));
		assertFalse(item[1].equals(item[3]));
		assertFalse(item[1].equals(item[4]));
		assertFalse(item[2].equals(item[3]));
		assertFalse(item[3].equals(item[4]));
	}
}
