package com.keyboardr.test.fbchat;

import java.util.ArrayList;
import java.util.List;

import com.keyboardr.test.fbchat.structures.CombAccount;
import com.keyboardr.test.fbchat.structures.CombGroup;

public class BuddyList {
	private static BuddyList singleton = new BuddyList();
	private final List<CombGroup> groups;
	private final List<CombAccount> accounts;

	private BuddyList() {
		groups = new ArrayList<CombGroup>();
		accounts = new ArrayList<CombAccount>();
	}

	public static BuddyList getInstance() {
		return singleton;
	}

	private void update() {
		// TODO: stub
	}

	public static void requestUpdate() {
		singleton.update();
	}

	public static List<CombGroup> getGroups() {
		return singleton.groups;
	}

	public static List<CombAccount> getAccounts() {
		return singleton.accounts;
	}

	public static void addAccount(CombAccount account) {
		singleton.accounts.add(account);

	}
}
