package test;

import android.test.AndroidTestCase;
import com.eseteam9.shoppyapp.handlers.LocalDatabaseHandler;
import com.eseteam9.shoppyapp.handlers.UserHandler;
import com.eseteam9.shoppyapp.valuesets.UserValueSet;

public class UserHandlerTest extends AndroidTestCase {
	private UserHandler uHandler() {
		return new UserHandler(getContext());
	}

	protected void setUp() {
		new LocalDatabaseHandler(getContext()).onUpgrade(
			new LocalDatabaseHandler(getContext()).getWritableDatabase() , 1, 1);
	}

	public void testConstructor() {
		uHandler();
	}

	public void testAddMethod() {
		uHandler().add(new UserValueSet("Testname", "a@b.c"));
	}

	public void testExsistsUserMethod() {
	}
}
