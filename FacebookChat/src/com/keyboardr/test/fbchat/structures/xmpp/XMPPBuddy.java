package com.keyboardr.test.fbchat.structures.xmpp;

import com.keyboardr.test.fbchat.structures.CombAccount;
import com.keyboardr.test.fbchat.structures.CombBuddy;

import android.graphics.Bitmap;

public class XMPPBuddy extends CombBuddy {
	public XMPPBuddy(String userID, String name, String group, CombAccount account, int status, Bitmap icon){
		mName = name;
		mGroup = group;
		mAccount = account;
		mStatus = status;
		mUserID = userID;
		mIcon = icon;
		mMessage = "";
	}

	@Override
	public String getName() {
		return new String(mName);
	}

	@Override
	public String getGroup() {
		return new String(mGroup);
	}
	
	@Override
	public String getMessage(){
		return mMessage;
	}

	@Override
	public CombAccount getAccount() {
		return mAccount;
	}

	@Override
	public int getStatus() {
		return mStatus;
	}

	@Override
	public String getUserID() {
		return new String(mUserID);
	}
	
	public Bitmap getIcon(){
		return mIcon;
	}

}
