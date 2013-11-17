package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.handlers.LocalDatabaseHandler;
import com.eseteam9.shoppyapp.handlers.ShoppingListHandler;
import com.eseteam9.shoppyapp.valuesets.ShoppingListValueSet;

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
		ShoppingListValueSet[] setList = {
				new ShoppingListValueSet("title1"),
				new ShoppingListValueSet("title2"),
				new ShoppingListValueSet("title3")};
		for(int i = 0; i<=2; i++) sHandler().add(setList[i]);

		for(int i = 0; i<=2; i++) assertEquals(setList[i], sHandler().getById(i + 1));
	}

	public void testDeleteMethod(){
		sHandler().add(new ShoppingListValueSet(""));
		sHandler().deleteById(1);
		assertTrue(sHandler().getAllLists().length == 0);
	}

	public void testExistsEntryMethod(){
		assertFalse(sHandler().existsTitle("Testtitle"));

		sHandler().add(new ShoppingListValueSet("Testtitle"));
		assertTrue(sHandler().existsTitle("Testtitle"));
	}

	public void testGetMethod(){
		ShoppingListValueSet setList = new ShoppingListValueSet("Testtitle");
		sHandler().add(setList);

		assertEquals(setList, sHandler().getById(sHandler().getAllLists()[0].id()));
	}

	public void testUpdateMethod(){
		sHandler().add(new ShoppingListValueSet(""));

		//sHandler().update();
		//assertEquals(sHandler().getById(1), );
	}
}
