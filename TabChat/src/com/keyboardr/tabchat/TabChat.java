package com.keyboardr.tabchat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.keyboardr.tabchat.service.CommsService;
import com.keyboardr.tabchat.service.CommsService.CommsBinder;

public class TabChat extends Activity {
	private CommsService mService;
	private boolean mBound = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(CommsService.ACTION_CONNECT);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

	}

	@Override
	public void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.accounts:
			Intent intent = new Intent(this, AccountActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}

	private final ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = ((CommsBinder) service).getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName className) {
			mBound = false;
		}

	};
}
