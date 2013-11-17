package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;
import com.eseteam9.shoppyapp.handlers.LocalDatabaseHandler;
import android.content.Context;

public class ShoppingListTest extends AndroidTestCase {
	private Context c(){return getContext();}

	public void setUp() {
		new LocalDatabaseHandler(c()).onUpgrade(
			new LocalDatabaseHandler(c()).getWritableDatabase() , 1, 1);
	}

	public void testInitialConstructor() {
		ShoppingList testShoppingList = new ShoppingList(c(), "Testshoppinglist");

		assertEquals(testShoppingList.id(), 1);
		assertEquals(testShoppingList.title(), "Testshoppinglist");
	}

	public void testIdConstructor() {
		new ShoppingList(c(), "Testshoppinglist");

		ShoppingList testShoppingList = new ShoppingList(c(), 1);

		assertEquals(testShoppingList.id(), 1);
		assertEquals(testShoppingList.title(), "Testshoppinglist");
	}
}
