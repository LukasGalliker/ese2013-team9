package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.*;
import java.util.Date;

public class ItemHandlerTest extends AndroidTestCase {
	private ItemHandler iHandler(){
		return new ItemHandler(getContext());
	}
	
	protected void setUp(){
		new LocalDatabaseHandler(getContext()).onUpgrade(
				new LocalDatabaseHandler(getContext()).getWritableDatabase() , 1, 1);
	}
	
	public void testConstructor(){
		iHandler();
	}
	
	public void testAddAndGetListItemsMethods(){
		Item[] setList = {
				new Item("name1", "1", 0),
				new Item("name2", "2", 0),
				new Item("name3", "3", 0),
				new Item("name4", "4", 1)};
		for(int i = 0; i<=3; i++) iHandler().add(setList[i]);
		
		Item[][] getList = {iHandler().getListItems(0), iHandler().getListItems(1)};
		assertEquals(setList.length, getList[0].length + getList[1].length);
		for(int i = 0; i<=2; i++) assertEquals(setList[i], getList[0][i]);
		assertEquals(setList[3], getList[1][0]);
	}
	
	public void testDeleteMethod(){
		iHandler().add(new Item("", "", 0));
		
		iHandler().delete(
				iHandler().getListItems(0)[0].id);
		
		assertTrue(iHandler().getListItems(0).length == 0);
	}
	
	public void testChechedMethod(){
		iHandler().add(new Item("", "", 0));
		assertFalse(iHandler().getListItems(0)[0].bought);
		
		iHandler().checked(iHandler().getListItems(0)[0].id, true);
		assertTrue(iHandler().getListItems(0)[0].bought);
		
		iHandler().checked(iHandler().getListItems(0)[0].id, false);
		assertFalse(iHandler().getListItems(0)[0].bought);
	}
	
	public void testGetCountMethod(){
		assertEquals(iHandler().getCount(0),
				iHandler().getListItems(0).length);
		
		iHandler().add(new Item("", "", 0));
		iHandler().add(new Item("", "", 1));
		assertEquals(iHandler().getCount(0),
				iHandler().getListItems(0).length);
		assertEquals(iHandler().getCount(2),
				iHandler().getListItems(2).length);
	}
	
	public void testGetCountUnboughtMethod(){
		assertEquals(iHandler().getCountUnbought(0), 0);
		
		iHandler().add(new Item("", "", 0));
		iHandler().add(new Item("", "", 1));
		assertEquals(iHandler().getCountUnbought(0), 1);
		
		iHandler().checked(iHandler().getListItems(0)[0].id, true);
		assertEquals(iHandler().getCountUnbought(0), 0);
	}
	
	public void testGetUnboughtMethod(){
		Item[] setList = {
				new Item("name1", "1", 0),
				new Item("name2", "2", 0),
				new Item("name3", "3", 0),
				new Item("name4", "4", 1)};
		for(int i = 0; i<=3; i++) iHandler().add(setList[i]);
		
		iHandler().checked(iHandler().getListItems(0)[1].id, true);
		iHandler().checked(iHandler().getListItems(0)[2].id, true);
		iHandler().checked(iHandler().getListItems(1)[0].id, true);
		
		Item[] expectedList = {
				new Item(0, "name1", "1", true, new Date(), "0"),
				new Item(0, "name3", "3", true, new Date(), "0")};
		
		Item[] getList = iHandler().getUnbought(0);
		assertEquals(expectedList.length, getList.length);
		//for(int i = 0; i<=1; i++) assertEquals(expectedList[i], getList[i]);
	}
	
	public void testUpdateOnlineKeyMethod(){
		iHandler().add(new Item("", "", 0));
		assertEquals(iHandler().getListItems(0)[0].onlineKey, "0");
		
		iHandler().updateOnlineKey(iHandler().getListItems(0)[0].id, "1234");
		assertEquals(iHandler().getListItems(0)[0].onlineKey,"1234");
	}
	
	public void testUpdateMethod(){
		iHandler().add(new Item("", "", 0));
		assertEquals(iHandler().getListItems(0)[0].name, "");
		assertEquals(iHandler().getListItems(0)[0].quantity, "");
		
		iHandler().update(iHandler().getListItems(0)[0].id, "Testname", "1234");
		assertEquals(iHandler().getListItems(0)[0].name,"Testname");
		assertEquals(iHandler().getListItems(0)[0].quantity, "1234");
	}
}
