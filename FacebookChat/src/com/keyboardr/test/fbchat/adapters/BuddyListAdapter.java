package com.keyboardr.test.fbchat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyboardr.test.fbchat.BuddyList;
import com.keyboardr.test.fbchat.R;
import com.keyboardr.test.fbchat.structures.CombBuddy;
import com.keyboardr.test.fbchat.structures.CombGroup;

public class BuddyListAdapter<A extends CombGroup, B extends CombBuddy> extends
		BaseExpandableListAdapter {

	@Override
	public CombBuddy getChild(int groupPosition, int childPosition) {
		return getGroup(groupPosition).getList().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CombBuddy child = getChild(groupPosition, childPosition);
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View v = inflater.inflate(R.layout.buddy_list_item, parent);

		((ImageView) v.findViewById(R.id.buddyBadge)).setImageBitmap(child
				.getIcon());
		((ImageView) v.findViewById(R.id.buddyStatus)).setImageResource(child
				.getStatus());
		((TextView) v.findViewById(R.id.buddyName)).setText(child.getName());
		((TextView) v.findViewById(R.id.buddyMessage)).setText(child
				.getMessage());

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return getGroup(groupPosition).getList().size();
	}

	@Override
	public CombGroup getGroup(int groupPosition) {
		return BuddyList.getGroups().get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return BuddyList.getGroups().size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		CombGroup group = getGroup(groupPosition);
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View v = inflater.inflate(R.layout.buddy_list_group, parent);

		((TextView) v.findViewById(R.id.groupName)).setText(group.getName());

		return v;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return (groupPosition < getGroupCount() && childPosition < getChildrenCount(groupPosition));
	}

}
