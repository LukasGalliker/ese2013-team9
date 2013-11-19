package test;

import junit.framework.TestCase;

import com.eseteam9.shoppyapp.handlers.MockDataBaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.ShoppingList;

public class DatabaseTest extends TestCase {
private MockDataBaseHandler tdb;

@Override
protected void setUp() throws Exception {
	tdb = new MockDataBaseHandler(null, "local.db", null,1 );
	}

public void testAddList(){
	//ShoppingList testList = new ShoppingList("Chuck Norris' List");
	//tdb.add(testList);
	//ShoppingList[] list = new ShoppingList[1];
	//list[1]= testList;
	//ShoppingList[] comparedList = tdb.getAll();
	//assertEquals(list, comparedList);
	}
}
