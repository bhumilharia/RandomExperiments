package com.cps.gps;

import android.app.Activity;
import android.content.Context;	
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class GPSTest1Activity extends Activity {

	private static String TAG = "GPSAct";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.startserv:
				Log.d(TAG, "onClick: Starting service");
				startService(new Intent(this, GPSService.class));
				break;
			case R.id.stopserv:
				Log.d(TAG, "onClick: Stopping service");
				stopService(new Intent(this, GPSService.class));
				break;
		}
		return true;
	}


}