package com.keyboardr.tabchat;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle bundle) {
		View v = inflater.inflate(R.layout.chat_tabs_layout, parent, false);
		return v;
	}
}
