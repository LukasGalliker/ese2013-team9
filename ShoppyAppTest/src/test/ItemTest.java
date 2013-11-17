package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.shoppingclasses.Item;
import com.eseteam9.shoppyapp.handlers.LocalDatabaseHandler;
import android.content.Context;

public class ItemTest extends AndroidTestCase {
	private Context c(){return getContext();}

	public void setUp() {
		new LocalDatabaseHandler(c()).onUpgrade(
			new LocalDatabaseHandler(c()).getWritableDatabase() , 1, 1);
	}

	public void testInitialConstructor() {
		Item testItem = new Item(c(), 1, "Testitem", "1kg");

		assertEquals(testItem.id(), 1);
		assertEquals(testItem.name(), "Testitem");
		assertEquals(testItem.quantity(), "1kg");
	}

	public void testIdConstructor() {
		new Item(c(), 1, "Testitem", "1kg");

		Item testItem = new Item(c(), 1);

		assertEquals(testItem.id(), 1);
		assertEquals(testItem.name(), "Testitem");
		assertEquals(testItem.quantity(), "1kg");
	}
}
