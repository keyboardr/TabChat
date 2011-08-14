package com.keyboardr.tabchat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.keyboardr.tabchat.adapters.BuddyListAdapter;
import com.keyboardr.tabchat.structures.CombBuddy;
import com.keyboardr.tabchat.structures.CombGroup;

public class BuddyListFragment extends Fragment {
	BuddyListAdapter<CombGroup, CombBuddy> mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle bundle) {
		View v = inflater.inflate(R.layout.buddy_list, parent, false);
		mAdapter = new BuddyListAdapter<CombGroup, CombBuddy>();
		((ExpandableListView) v.findViewById(R.id.expandableListView1))
				.setAdapter(mAdapter);
		// TODO add onClickListeners and Adapters
		return v;
	}

}
