package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.*;

public class ShoppingListHandlerTest extends AndroidTestCase {
	private ShoppingListHandler sHandler(){
		return new ShoppingListHandler(getContext());
	}
	
	protected void setUp(){
		new LocalDatabaseHandler(getContext()).onUpgrade(
				new LocalDatabaseHandler(getContext()).getWritableDatabase() , 1, 1);
	}
	
	public void testConstructor(){
		sHandler();
	}
	
	public void testAddAndGetAllMethods(){
		ShoppingList[] setList = {
				new ShoppingList("title1"),
				new ShoppingList("title2"),
				new ShoppingList("title3")};
		for(int i = 0; i<=2; i++) sHandler().add(setList[i]);
		
		ShoppingList[] getList = sHandler().getAll();
		assertEquals(setList.length, getList.length);
		for(int i = 0; i<=2; i++) assertEquals(setList[i], getList[i]);
	}
	
	public void testDeleteMethod(){
		sHandler().add(new ShoppingList(""));
		
		sHandler().delete(
				sHandler().getAll()[0].id);
		
		assertTrue(sHandler().getAll().length == 0);
	}
	
	public void testGetCountMethod(){
		assertEquals(sHandler().getCount(),
				sHandler().getAll().length);
		
		sHandler().add(new ShoppingList(""));
		assertEquals(sHandler().getCount(),
				sHandler().getAll().length);
	}
	
	public void testExistsEntryMethod(){
		assertFalse(sHandler().existsEntry("Testtitle"));
		
		sHandler().add(new ShoppingList("Testtitle"));
		assertTrue(sHandler().existsEntry("Testtitle"));
	}
	
	public void testGetMethods(){
		ShoppingList setList = new ShoppingList("Testtitle");
		sHandler().add(setList);
		
		assertEquals(setList, sHandler().get(sHandler().getAll()[0].id));
		assertEquals(setList, sHandler().get(sHandler().getAll()[0].onlineKey));
	}
	
	public void testUpdateMethod(){
		sHandler().add(new ShoppingList(""));
		
		sHandler().update(sHandler().getAll()[0].id, "Testtitle");
		assertEquals(sHandler().getAll()[0].title, "Testtitle");
	}
	
	public void testUpdateOnlineKey(){
		sHandler().add(new ShoppingList(""));
		
		sHandler().updateOnlineKey(sHandler().getAll()[0].id, "12345");
		assertEquals(sHandler().getAll()[0].onlineKey, "12345");
	}
}
