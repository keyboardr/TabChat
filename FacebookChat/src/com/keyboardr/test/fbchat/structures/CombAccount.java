package com.keyboardr.test.fbchat.structures;

import java.util.Collection;

public abstract class CombAccount {
	public abstract void connect();

	public abstract void disconnect();

	public abstract Collection<CombBuddy> getRosterList();

	public abstract String getName();

	public abstract int getIconResource();

	public abstract boolean isConnected();

	public abstract void addChatListener(ChatListener listener);

	public abstract void removeChatListener(ChatListener listener);
}
