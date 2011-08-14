package com.keyboardr.tabchat.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CombGroup {
	private List<CombBuddy> mBuddies;
	private String mName;
	private Comparator<CombBuddy> mSorter;
	
	public CombGroup(List<CombBuddy> buddies, String name, Comparator<CombBuddy> sorter){
		mBuddies = new ArrayList<CombBuddy>(buddies.size());
		mName = name;
		mSorter = sorter;
		for(CombBuddy buddy : buddies){
			int index = Collections.binarySearch(mBuddies, buddy, sorter);
			index = (index < 0)? -index - 1: index;
			mBuddies.add(index, buddy);
		}
	}
	
	public int addBuddy(CombBuddy b){
		int index = Collections.binarySearch(mBuddies, b, mSorter);
		index = (index < 0)? -index - 1: index;
		mBuddies.add(index, b);
		return index;
	}
	
	public void resort(){
		Collections.sort(mBuddies, mSorter);
	}
	
	public String getName(){
		return new String(mName);
	}
	
	public List<CombBuddy> getList(){
		return mBuddies;
	}
}
