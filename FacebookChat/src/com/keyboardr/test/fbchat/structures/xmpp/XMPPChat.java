package com.keyboardr.test.fbchat.structures.xmpp;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keyboardr.test.fbchat.R;
import com.keyboardr.test.fbchat.structures.CombChat;
import com.keyboardr.test.fbchat.structures.CombMessage;
import com.keyboardr.test.fbchat.structures.CombMessageListener;

public class XMPPChat extends CombChat implements OnClickListener,
		MessageListener {
	protected Chat mChat;
	private final Set<CombMessageListener> mMessageListeners = new LinkedHashSet<CombMessageListener>();

	public XMPPChat(XMPPBuddy buddy, Chat chat) {
		super(buddy);
		mChat = chat;
	}

	public View createTabContent(String tag, ViewGroup parent) {
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.chat, parent, false);

		((ImageView) v.findViewById(R.id.buddyBadge)).setImageBitmap(mBuddy
				.getIcon());
		((ImageView) v.findViewById(R.id.buddyStatus)).setImageResource(mBuddy
				.getStatus());
		((TextView) v.findViewById(R.id.buddyName)).setText(mBuddy.getName());
		((TextView) v.findViewById(R.id.buddyMessage)).setText(mBuddy
				.getMessage());

		v.findViewById(R.id.chatCloseButton).setOnClickListener(this);
		v.findViewById(R.id.chatSendButton).setOnClickListener(this);

		// TODO: add message adapter
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMessageListener(CombMessageListener listener) {
		mMessageListeners.add(new InnerMessageListener(listener));
	}

	@Override
	public void removeMessageListener(CombMessageListener listener) {
		mMessageListeners.remove(new InnerMessageListener(listener));
	}

	private class InnerMessageListener implements CombMessageListener {
		private final WeakReference<CombMessageListener> mListener;

		public InnerMessageListener(CombMessageListener listener) {
			mListener = new WeakReference<CombMessageListener>(listener);
		}

		@Override
		public void processMessage(CombChat chat, CombMessage message) {
			CombMessageListener listener = mListener.get();
			if (listener != null) {
				listener.processMessage(chat, message);
			}
		}

		@Override
		public boolean equals(Object another) {
			CombMessageListener listener = mListener.get();
			if (!(another instanceof InnerMessageListener)) {
				if (another.equals(listener)) {
					return true;
				} else {
					return false;
				}
			}
			return listener.equals(((InnerMessageListener) another).mListener
					.get());
		}

	}

	@Override
	public void processMessage(Chat chat, Message message) {
		for (CombMessageListener listener : mMessageListeners) {
			listener.processMessage(this, new XMPPMessage(message.getBody(),
					(XMPPBuddy) mBuddy));
		}

	}

}
