package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.*;

public class UserHandlerTest extends AndroidTestCase {
	private UserHandler uHandler(){
		return new UserHandler(getContext());
	}
	
	protected void setUp(){
		new LocalDatabaseHandler(getContext()).onUpgrade(
				new LocalDatabaseHandler(getContext()).getWritableDatabase() , 1, 1);
	}
	
	public void testConstructor(){
		uHandler();
	}
	
	public void testAddMethod(){
		uHandler().add(new User("", ""));
	}
	
	public void testExsistsUserMethod(){
		assertFalse(uHandler().existsUser());
		
		uHandler().add(new User("", ""));
		
		assertTrue(uHandler().existsUser());
	}
}
