package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.shoppingclasses.User;
import com.eseteam9.shoppyapp.handlers.LocalDatabaseHandler;
import android.content.Context;

public class UserTest extends AndroidTestCase {
	private Context c(){return getContext();}

	public void setUp() {
		new LocalDatabaseHandler(c()).onUpgrade(
			new LocalDatabaseHandler(c()).getWritableDatabase() , 1, 1);
	}

	public void testInitialConstructor() {
		User testUser = new User(c(), "Testuser", "a@b.c");

		assertEquals(testUser.id(), 1);
		assertEquals(testUser.name(), "Testuser");
		assertEquals(testUser.email(), "a@b.c");
	}

	public void testIdConstructor() {
		new User(c(), "Testuser", "a@b.c");

		User testUser = new User(c(), 1);

		assertEquals(testUser.id(), 1);
		assertEquals(testUser.name(), "Testuser");
		assertEquals(testUser.email(), "a@b.c");
	}
}
