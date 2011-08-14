package com.keyboardr.tabchat;

import java.util.ArrayList;
import java.util.List;

import com.keyboardr.tabchat.structures.CombGroup;

public class BuddyList {
	private static BuddyList singleton = new BuddyList();
	private final List<CombGroup> groups;

	private BuddyList() {
		groups = new ArrayList<CombGroup>();
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
}
