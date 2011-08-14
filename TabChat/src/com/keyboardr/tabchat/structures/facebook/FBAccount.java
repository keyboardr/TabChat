package com.keyboardr.tabchat.structures.facebook;

import java.util.Collection;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.packet.VCard;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.BitmapFactory;

import com.keyboardr.tabchat.R;
import com.keyboardr.tabchat.structures.CombBuddy;
import com.keyboardr.tabchat.structures.xmpp.XMPPAccount;

public class FBAccount extends XMPPAccount {

	public FBAccount(String username, String password) {
		super(username, password, "chat.facebook.com", 5222);
	}

	public FBAccount(JSONObject jObject) throws JSONException {
		this(jObject.getString("username"), jObject.getString("password"));

	}

	@Override
	public Collection<CombBuddy> getRosterList() {
		Collection<CombBuddy> buddies = super.getRosterList();
		VCard vcard = new VCard();
		for (CombBuddy buddy : buddies) {
			try {
				vcard.load(this.mConnection, buddy.getUserID());
				buddy.setmName(vcard.getFirstName() + " " + vcard.getLastName());
				buddy.setmIcon(BitmapFactory.decodeByteArray(vcard.getAvatar(),
						0, vcard.getAvatar().length));
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		return buddies;
	}

	@Override
	public int getIconResource() {
		return R.drawable.facebook_logo;
	}

	@Override
	protected int getType() {
		return TYPE_FACEBOOK;
	}

}
