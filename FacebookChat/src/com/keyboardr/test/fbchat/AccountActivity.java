package com.keyboardr.test.fbchat;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.keyboardr.test.fbchat.adapters.AccountListAdapter;
import com.keyboardr.test.fbchat.structures.CombAccount;
import com.keyboardr.test.fbchat.structures.facebook.FBAccount;

public class AccountActivity extends Activity implements OnItemClickListener {
	private AccountFragment fragment;
	// private AccountCreateFragment creater;
	private BaseAdapter mAdapter;
	private int selected;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.management);

		mAdapter = new AccountListAdapter<CombAccount>(this);
		ListView accountList = (ListView) findViewById(R.id.accountList);
		accountList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		accountList.setAdapter(mAdapter);
		accountList.setOnItemClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.accounts, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.addAccount:
			final Dialog dialog = new Dialog(this);
			dialog.setContentView(R.layout.new_account);
			dialog.setTitle(R.string.newAccountType);

			Button okButton = (Button) dialog
					.findViewById(R.id.accountOkButton);
			okButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					CombAccount account = null;
					switch (((Spinner) dialog
							.findViewById(R.id.accountTypeSpinner))
							.getSelectedItemPosition()) {
					case 0:
						account = new FBAccount("", "");
						break;
					}
					if (null == fragment) {
						fragment = new AccountFragment(account);

					} else {
						fragment.setAccount(account);
					}
					if (!fragment.isVisible()) {
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();
						transaction.replace(R.id.accountLayout, fragment);
						transaction.addToBackStack(null);
						transaction.commit();
					}
					BuddyList.addAccount(account);
					mAdapter.notifyDataSetChanged();
					((ListView) findViewById(R.id.accountList)).setItemChecked(
							BuddyList.getAccounts().size() - 1, true);
					dialog.dismiss();
				}

			});
			Button cancelButton = (Button) dialog
					.findViewById(R.id.accountCancelButton);
			cancelButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});
			dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			dialog.show();
			return true;
		case android.R.id.home:
			Intent intent = new Intent(this, FBChat.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.findItem(R.id.deleteAccount).setVisible(
				fragment != null && fragment.isVisible());
		return true;

	}

	public void removeCreater() {
		ListView accountList = ((ListView) findViewById(R.id.accountList));
		accountList.setItemChecked(-1, true);
		mAdapter.notifyDataSetChanged();
		getFragmentManager().popBackStack();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		selected = position;
		if (null == fragment) {
			fragment = new AccountFragment(
					(CombAccount) parent.getItemAtPosition(position));

		} else {
			fragment.setAccount((CombAccount) parent
					.getItemAtPosition(position));
		}
		if (!fragment.isVisible()) {
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.accountLayout, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
		mAdapter.notifyDataSetChanged();
	}

	public int getSelected() {
		return selected;
	}
}
