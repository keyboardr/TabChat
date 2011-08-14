package com.keyboardr.test.fbchat.structures;

import com.keyboardr.test.fbchat.R;
import android.graphics.Bitmap;

public abstract class CombBuddy {
	public static final int STATUS_OFFLINE = R.drawable.offline;
	public static final int STATUS_AWAY = R.drawable.away;
	public static final int STATUS_IDLE = R.drawable.idle;
	public static final int STATUS_AWAY_IDLE = R.drawable.extended_away;
	public static final int STATUS_AVAILABLE = R.drawable.available;
	
	protected String mName;
	protected String mGroup, mUserID, mMessage;
	protected CombAccount mAccount;
	protected int mStatus;
	protected Bitmap mIcon;
	
	public abstract String getName();
	public abstract String getGroup();
	public abstract String getUserID();
	public abstract String getMessage();
	public abstract CombAccount getAccount();
	public abstract int getStatus();
	public abstract Bitmap getIcon();
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmName() {
		return mName;
	}
	public void setmIcon(Bitmap mIcon) {
		this.mIcon = mIcon;
	}
	public Bitmap getmIcon() {
		return mIcon;
	}
}
