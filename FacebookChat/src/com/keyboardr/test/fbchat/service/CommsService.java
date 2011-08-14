package com.keyboardr.test.fbchat.service;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.keyboardr.test.fbchat.structures.ChatListener;
import com.keyboardr.test.fbchat.structures.CombAccount;
import com.keyboardr.test.fbchat.structures.CombChat;

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

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {

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

}
