package com.eseteam9.shoppyapp.adapters;

import java.util.Locale;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.fragments.DisplayFragment;
import com.eseteam9.shoppyapp.fragments.DisplayListsFragment;
import com.eseteam9.shoppyapp.fragments.DisplayNotificationsFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private DisplayFragment listFragment;
	private DisplayFragment notificationsFragment;
	private Context context;

	public SectionsPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0){
			this.listFragment = new DisplayListsFragment();
			return listFragment;
		}
		
		this.notificationsFragment = new DisplayNotificationsFragment();
		return notificationsFragment;
	}

	public void update(){
		listFragment.updateAdapter();
		notificationsFragment.updateAdapter();
	}
	
	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return context.getString(R.string.title_section1).toUpperCase(l);
		case 1:
			return context.getString(R.string.title_section2).toUpperCase(l);
		}
		return null;
	}
}
