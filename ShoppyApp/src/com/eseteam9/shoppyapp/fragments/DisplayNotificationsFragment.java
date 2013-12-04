package com.eseteam9.shoppyapp.fragments;

import com.eseteam9.shoppyapp.R;
import com.eseteam9.shoppyapp.adapters.NotificationAdapter;
import com.eseteam9.shoppyapp.handlers.OnlineDatabaseHandler;
import com.eseteam9.shoppyapp.shoppingclasses.*;

import android.content.Context;
import android.os.Bundle;
import android.view.*;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
/**
 * This class is responsible of handling the notifications.
 * 
 * @author Sebastien Broggi, Sammer Puran, Marc Schneiter, Lukas Galliker
 * @extends DisplayFragment
 */
public class DisplayNotificationsFragment extends DisplayFragment{
	private NotificationAdapter adapter;
	private Notification[] notifications; 
	private ListView lv;

	public DisplayNotificationsFragment() {
	}

	//Displays Lists
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		User me = Users.getOwner(getActivity());
        View view =  inflater.inflate(R.layout.fragment_display_notifications,container, false);
        lv = (ListView)view.findViewById(R.id.notifications);
		registerForContextMenu(lv);
		lv.setClickable(true);
		if (notifications == null){
			notifications = new Notification[1];
			notifications[0] = new Notification(me.email(), "", "No new Notifications!");
		}

		this.adapter = new NotificationAdapter(getActivity(), R.id.notifications, notifications);
		lv.setAdapter(adapter);
		updateAdapter();

		return view;
	}

	//Creates ContextMenu (from arrays.xml) when pressing&holding an item
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	  if (v.getId()==R.id.listoverview) {
	    menu.setHeaderTitle("Options");
	    String[] menuItems = getResources().getStringArray(R.array.item_context_menu);
	    for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
	  }
	}

	//Reads what is selected from ContextMenu
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo(); 
	    Notification notification = notifications[menuInfo.position];
	    
	    switch (item.getItemId()) {
		  case 0:
		    return true;
		  case 1:
			  return true;
		  default:
		    return super.onContextItemSelected(item);
		}
	}

	@Override
	public void updateAdapter() {
		Context context = getActivity();
		User me = Users.getOwner(context);
		new OnlineDatabaseHandler(context).getNotifications(me.email(), this.adapter);
	}
}
