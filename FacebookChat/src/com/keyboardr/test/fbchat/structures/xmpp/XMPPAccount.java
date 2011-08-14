package com.keyboardr.test.fbchat.structures.xmpp;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.os.AsyncTask;

import com.keyboardr.test.fbchat.R;
import com.keyboardr.test.fbchat.structures.ChatListener;
import com.keyboardr.test.fbchat.structures.CombAccount;
import com.keyboardr.test.fbchat.structures.CombBuddy;
import com.keyboardr.test.fbchat.structures.CombChat;

public class XMPPAccount extends CombAccount implements ChatManagerListener {
	protected String mUsername, mPassword, mServer;
	protected int mPort;
	protected XMPPConnection mConnection;
	protected ChatManager mChatManager;
	protected Set<ChatListener> mChatListeners = new LinkedHashSet<ChatListener>();

	public XMPPAccount(String username, String password, String server, int port) {
		mUsername = username;
		mPassword = password;
		mServer = server;
		mPort = port;
	}

	/**
	 * @return the mUsername
	 */
	public String getUsername() {
		return mUsername;
	}

	/**
	 * @param mUsername the mUsername to set
	 */
	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	/**
	 * @return the mPassword
	 */
	public String getPassword() {
		return mPassword;
	}

	/**
	 * @param mPassword the mPassword to set
	 */
	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	/**
	 * @return the mServer
	 */
	public String getServer() {
		return mServer;
	}

	/**
	 * @param mServer the mServer to set
	 */
	public void setServer(String mServer) {
		this.mServer = mServer;
	}

	/**
	 * @return the mPort
	 */
	public int getPort() {
		return mPort;
	}

	/**
	 * @param mPort the mPort to set
	 */
	public void setPort(int mPort) {
		this.mPort = mPort;
	}

	@Override
	public void connect() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				ConnectionConfiguration config = new ConnectionConfiguration(
						mServer, mPort);
				mConnection = new XMPPConnection(config);
				try {
					mConnection.connect();
					mConnection.login(mUsername, mPassword);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mChatManager = mConnection.getChatManager();
				mChatManager.addChatListener(XMPPAccount.this);
			}

		}.execute();
		new Thread(new Runnable() {
			public void run() {

			}
		}).start();
	}

	@Override
	public void disconnect() {
		if (mConnection != null) {
			mConnection.disconnect();
		}
	}

	@Override
	public Collection<CombBuddy> getRosterList() {
		Roster roster = mConnection.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		ArrayList<CombBuddy> buddies = new ArrayList<CombBuddy>();
		for (RosterEntry entry : entries) {
			// Get first group
			String group;
			Collection<RosterGroup> groups = entry.getGroups();
			if (groups.size() == 0) {
				group = null;
			} else {
				group = ((RosterGroup) groups.toArray()[0]).getName();
			}

			// Get status

			int status = CombBuddy.STATUS_OFFLINE;
			Presence presence = roster.getPresence(entry.getName());
			if (presence.getType().equals(Presence.Type.available)) {
				Presence.Mode presenceMode = presence.getMode();
				if (presenceMode == null
						|| presenceMode.equals(Presence.Mode.available)) {
					status = CombBuddy.STATUS_AVAILABLE;
				} else if (presenceMode.equals(Presence.Mode.away)) {
					status = CombBuddy.STATUS_AWAY;
				}
			}

			buddies.add(new XMPPBuddy(entry.getUser(), entry.getName(), group,
					this, status, null));
		}
		return buddies;
	}

	@Override
	public String getName() {
		return mUsername;
	}

	@Override
	public int getIconResource() {
		return R.drawable.xmpp_logo;
	}

	@Override
	public boolean isConnected() {
		return mConnection != null && mConnection.isConnected();
	}

	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		Collection<CombBuddy> buddies = getRosterList();
		XMPPBuddy participant = null;
		for (CombBuddy buddy : buddies) {
			if (((XMPPBuddy) buddy).getName().equalsIgnoreCase(
					chat.getParticipant())) {
				participant = (XMPPBuddy) buddy;
				break;
			}
		}

		for (ChatListener listener : mChatListeners) {
			listener.chatCreated(new XMPPChat(participant, chat),
					createdLocally);
		}
	}

	@Override
	public void addChatListener(ChatListener listener) {
		mChatListeners.add(new InnerChatListener(listener));
	}

	@Override
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
