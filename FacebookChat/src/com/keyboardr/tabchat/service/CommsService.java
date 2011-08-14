package com.keyboardr.tabchat.service;

import static com.keyboardr.tabchat.utils.ObscuredSharedPreferences.KEY_ACCOUNTS;
import static com.keyboardr.tabchat.utils.ObscuredSharedPreferences.SHARED_PREFERENCES;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.keyboardr.tabchat.structures.ChatListener;
import com.keyboardr.tabchat.structures.CombAccount;
import com.keyboardr.tabchat.structures.CombChat;
import com.keyboardr.tabchat.utils.ObscuredSharedPreferences;

public class CommsService extends Service implements ChatListener {
	public static final String EXTRA_ACCOUNT = "com.keyboardr.test.fbchat.service.CommsService.EXTRA_ACCOUNT";
	public static final String ACTION_CONNECT = "com.keyboardr.test.fbchat.service.CommsService.ACTION_CONNECT";
	public static final String ACTION_DISCONNECT = "com.keyboardr.test.fbchat.service.CommsService.ACTION_DISCONNECT";

	public class CommsBinder extends Binder {
		public CommsService getService() {
			return CommsService.this;
		}
	}

	private final IBinder mBinder = new CommsBinder();
	private List<ChatListener> mChatListeners;
	private List<CombAccount> mAccounts;
	private ObscuredSharedPreferences mSettings;

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mAccounts = new ArrayList<CombAccount>();
		mSettings = new ObscuredSharedPreferences(getApplicationContext(),
				getApplicationContext().getSharedPreferences(
						SHARED_PREFERENCES, MODE_PRIVATE));
		try {
			JSONArray jArray = new JSONArray(mSettings.getString(KEY_ACCOUNTS,
					"[]"));
			for (int i = 0; i < jArray.length(); i++) {
				mAccounts.add(CombAccount.fromJson(jArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		new ConnectTask().execute((CombAccount[]) mAccounts.toArray());

	}

	@Override
	public void onDestroy() {
		new DisconnectTask().execute((CombAccount[]) mAccounts.toArray());
	}

	@Override
	public void chatCreated(CombChat chat, boolean createdLocally) {
		for (ChatListener listener : mChatListeners) {
			listener.chatCreated(chat, createdLocally);
		}
	}

	public void addChatListener(ChatListener listener) {
		mChatListeners.add(new InnerChatListener(listener));
	}

	public void removeChatListener(ChatListener listener) {
		mChatListeners.remove(new InnerChatListener(listener));
	}

	public void addAccount(CombAccount account) {
		mAccounts.add(account);
	}

	public void removeAccount(int index) {
		mAccounts.remove(index);
	}

	public CombAccount getAccount(int index) {
		return mAccounts.get(index);
	}

	public List<CombAccount> getAccounts() {
		return mAccounts;
	}

	public void enableAccount(CombAccount account) {
		new ConnectTask().execute(account);
	}

	public void disableAccount(CombAccount account) {
		new DisconnectTask().execute(account);
	}

	private class InnerChatListener implements ChatListener {
		private final WeakReference<ChatListener> mListener;

		public InnerChatListener(ChatListener listener) {
			mListener = new WeakReference<ChatListener>(listener);
		}

		@Override
		public void chatCreated(CombChat chat, boolean createdLocally) {
			ChatListener listener = mListener.get();
			if (listener != null) {
				listener.chatCreated(chat, createdLocally);
			}
		}

		@Override
		public boolean equals(Object another) {
			ChatListener listener = mListener.get();
			if (!(another instanceof InnerChatListener)) {
				if (another.equals(listener)) {
					return true;
				} else {
					return false;
				}
			}
			return listener.equals(((InnerChatListener) another).mListener
					.get());
		}

	}

	private class ConnectTask extends AsyncTask<CombAccount, Void, Void> {

		@Override
		protected Void doInBackground(CombAccount... params) {
			for (CombAccount account : params) {
				if (account.isEnabled) {
					account.connect();
					account.addChatListener(CommsService.this);
				}
			}
			return null;
		}
	}

	private class DisconnectTask extends AsyncTask<CombAccount, Void, Void> {

		@Override
		protected Void doInBackground(CombAccount... params) {
			for (CombAccount account : params) {
				if (account.isConnected()) {
					account.disconnect();
					account.removeChatListener(CommsService.this);
				}
			}
			return null;
		}
	}

	public int getNumAccounts() {
		return mAccounts.size();
	}
}
