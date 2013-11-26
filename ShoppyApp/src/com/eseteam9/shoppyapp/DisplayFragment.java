package com.eseteam9.shoppyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
/**
 * This is an Interface to the DisplayFragments in the App.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends Fragment
 */
public abstract class DisplayFragment extends Fragment {
	
	public DisplayFragment() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
	}
	
	public abstract void updateAdapter();
}
