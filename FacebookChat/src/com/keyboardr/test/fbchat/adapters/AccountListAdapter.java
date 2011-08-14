package com.keyboardr.test.fbchat.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyboardr.test.fbchat.AccountActivity;
import com.keyboardr.test.fbchat.BuddyList;
import com.keyboardr.test.fbchat.R;
import com.keyboardr.test.fbchat.structures.CombAccount;

public class AccountListAdapter<T extends CombAccount> extends BaseAdapter {
	AccountActivity mOwner;

	public AccountListAdapter(AccountActivity owner) {
		mOwner = owner;
	}

	@Override
	public int getCount() {
		return BuddyList.getAccounts().size();
	}

	@Override
	public CombAccount getItem(int position) {
		return BuddyList.getAccounts().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		CombAccount account = getItem(position);
		if (view == null) {
			view = mOwner.getLayoutInflater().inflate(
					R.layout.account_list_item, parent, false);
		}
		((ImageView) view.findViewById(R.id.accountIcon))
				.setImageResource(account.getIconResource());
		((TextView) view.findViewById(R.id.accountName)).setText(account
				.getName());
		return view;
	}
}
