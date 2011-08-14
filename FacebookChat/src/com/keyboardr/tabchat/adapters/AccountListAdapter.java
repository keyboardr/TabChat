package com.keyboardr.tabchat.adapters;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyboardr.tabchat.R;
import com.keyboardr.tabchat.structures.CombAccount;

public class AccountListAdapter<T extends CombAccount> extends BaseAdapter {
	List<T> mAccounts;

	public AccountListAdapter(List<T> accounts) {
		mAccounts = accounts;
	}

	@Override
	public int getCount() {
		return mAccounts.size();
	}

	@Override
	public CombAccount getItem(int position) {
		return mAccounts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		CombAccount account = getItem(position);
		if (view == null) {
			view = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.account_list_item, parent, false);
		}
		((ImageView) view.findViewById(R.id.accountIcon))
				.setImageResource(account.getIconResource());
		((TextView) view.findViewById(R.id.accountName)).setText(account
				.getName());
		return view;
	}
}
