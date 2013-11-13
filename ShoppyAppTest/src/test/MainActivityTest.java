package test;

import com.eseteam9.shoppyapp.*;

import android.test.AndroidTestCase;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import junit.framework.TestCase;

public class MainActivityTest extends AndroidTestCase {
	private static final int ADAPTER_COUNT = 9;
	private MainActivity mActivity;
	private Spinner mSpinner;
	private SpinnerAdapter mPlanetData;

	public MainActivityTest(){
		super();
	}
protected void setUp() throws Exception{
	super.setUp();
	mSpinner = (Spinner) mActivity.findViewById(com.eseteam9.shoppyapp.R.id.listoverview);
	mPlanetData = mSpinner.getAdapter();
	
	
}
public void testPreConditions() {
    assertTrue(mSpinner.getOnItemSelectedListener() != null);
    assertTrue(mPlanetData != null);
    assertEquals(mPlanetData.getCount(),ADAPTER_COUNT);
  } 

}
