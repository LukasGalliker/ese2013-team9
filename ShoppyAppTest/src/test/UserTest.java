package test;

import junit.framework.TestCase;

import com.eseteam9.shoppyapp.shoppingclasses.User;

public class UserTest extends TestCase {
	
	public void testThinConstructor(){
		new User(null, null);
		
		User testUser = new User("Testuser", "a@b.c");
		
		assertEquals(testUser.id, 0);
		assertEquals(testUser.name, "Testuser");
		assertEquals(testUser.email, "a@b.c");
	}
	
	public void testThickConstructor(){
		new User(0, null, null);
		
		User testUser = new User(10, "Testuser", "a@b.c");
		
		assertEquals(testUser.id, 10);
		assertEquals(testUser.name, "Testuser");
		assertEquals(testUser.email, "a@b.c");
	}
	
	public void testEqualsMethod(){
		User[] user = {new User(0, "name1", "email1"),
				new User(1, "name1", "email1"),
				new User(0, "name1", "email2"),
				new User(0, "name2", "email2")};
		
		assertFalse(user[0].equals(null));
		assertFalse(user[0].equals(true));
		assertEquals(user[0], user[0]);
		assertEquals(user[0], user[1]);
		assertFalse(user[1].equals(user[2]));
		assertFalse(user[1].equals(user[3]));
		assertFalse(user[2].equals(user[3]));
	}
}
