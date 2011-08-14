package com.keyboardr.tabchat.structures;

public abstract class CombChat {
	protected CombBuddy mBuddy;

	public CombChat(CombBuddy buddy) {
		mBuddy = buddy;
	}

	public abstract void addMessageListener(CombMessageListener listener);

	public abstract void removeMessageListener(CombMessageListener listener);

}
