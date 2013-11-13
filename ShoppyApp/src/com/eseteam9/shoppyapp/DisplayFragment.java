package com.eseteam9.shoppyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
/**
 * This class doesn't do anything but define a fragment. It provides the structure used in 
 * any other fragments in our app.
 * 
 * @author SŽbastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
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
