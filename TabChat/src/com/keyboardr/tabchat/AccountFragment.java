package com.keyboardr.tabchat;

import java.util.HashMap;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.keyboardr.tabchat.structures.CombAccount;
import com.keyboardr.tabchat.structures.facebook.FBAccount;
import com.keyboardr.tabchat.structures.xmpp.XMPPAccount;

public class AccountFragment extends Fragment implements OnClickListener {
	private static HashMap<Class<? extends CombAccount>, Integer> layouts = new HashMap<Class<? extends CombAccount>, Integer>();
	static {
		layouts.put(XMPPAccount.class, R.layout.xmpp_account_setup);
		layouts.put(FBAccount.class, R.layout.facebook_account_setup);
	}
	CombAccount mAccount;
	private View v;

	public AccountFragment(CombAccount i) {
		mAccount = i;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle bundle) {
		v = inflater.inflate(layouts.get(mAccount.getClass()), parent, false);
		setAccount(mAccount);
		return v;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.accountDone:
			switch (layouts.get(mAccount.getClass())) {
			case R.layout.facebook_account_setup:
				((FBAccount) mAccount).setPassword(((EditText) v
						.findViewById(R.id.accountPassword)).getEditableText()
						.toString());
				((FBAccount) mAccount).setUsername(((EditText) v
						.findViewById(R.id.accountUserName)).getEditableText()
						.toString());
				break;
			}
			mAccount.connect();
		case R.id.accountCancel:
			((AccountActivity) getActivity()).removeCreater();
			break;
		}
	}

	public void setAccount(final CombAccount account) {
		mAccount = account;
		switch (layouts.get(mAccount.getClass())) {
		case R.layout.facebook_account_setup:
			((EditText) v.findViewById(R.id.accountPassword))
					.setText(((FBAccount) mAccount).getPassword());
			((EditText) v.findViewById(R.id.accountUserName))
					.setText(((FBAccount) mAccount).getUsername());
			break;
		}
		CheckBox enabledBox = (CheckBox) v.findViewById(R.id.accountEnabled);
		enabledBox.setChecked(account.isEnabled);
		enabledBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton view, boolean checked) {
				if (checked) {
					((AccountActivity) getActivity()).enableAccount(account);
				} else {
					((AccountActivity) getActivity()).disableAccount(account);
				}
			}

		});
		v.findViewById(R.id.accountDone).setOnClickListener(this);
		v.findViewById(R.id.accountCancel).setOnClickListener(this);
	}

}
