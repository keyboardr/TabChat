package com.keyboardr.tabchat.structures;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.keyboardr.tabchat.structures.facebook.FBAccount;
import com.keyboardr.tabchat.structures.xmpp.XMPPAccount;

public abstract class CombAccount {
	public static final int TYPE_XMPP = 0;
	public static final int TYPE_FACEBOOK = 1;

	public boolean isEnabled = false;

	public void connect() {
		isEnabled = true;
	}

	public void disconnect() {
		isEnabled = false;
	}

	public abstract Collection<CombBuddy> getRosterList();

	public abstract String getName();

	public abstract int getIconResource();

	public abstract boolean isConnected();

	public abstract void addChatListener(ChatListener listener);

	public abstract void removeChatListener(ChatListener listener);

	public static CombAccount fromJson(JSONObject jObject) {
		CombAccount account = null;
		try {
			switch (jObject.getInt("type")) {
			case TYPE_XMPP:
				account = new XMPPAccount(jObject);
				return account;
			case TYPE_FACEBOOK:
				account = new FBAccount(jObject);
				return account;
			}
			account.isEnabled = jObject.getBoolean("enabled");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return account;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject jObject = new JSONObject();
		jObject.put("type", getType());
		jObject.put("enabled", isEnabled);
		return jObject;
	}

	protected abstract int getType();
}
