/**
 * 
 */
package com.cps.gps;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Bhumil Haria
 * 
 */
public class GPSService extends Service {

	private static final String TAG = "GPSService";
	private int sensUpdateDelay = 1000 * 60 * 10;

	GPSDelayHandler gpsDelayHandler = new GPSDelayHandler();
	GPSDelayThread gpsDelayThread = new GPSDelayThread(gpsDelayHandler,
			sensUpdateDelay);

	LocationManager lm;
	LocationListener ll;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, TAG + " Created", Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onCreate");

		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		/*Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_HIGH);  
		lm.getBestProvider(criteria, true);
		*/
		ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
		
		createFile();

	}

	public void gpsDoSomething() {
		String s = TAG + ": \n";
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, TAG + " Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, TAG + " Started", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onStart");
	}
	
	
	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;
	
	public void createFile() {
		checkExternalStorage();
		
		int i = 0;
		if (mExternalStorageAvailable == false
				|| mExternalStorageWriteable == false)
			i = 2/(2 - 2);

		File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) ;
		
		try {
				File file = new File(root, "gpslog.txt");
				FileWriter filewriter = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(filewriter);
				out.write("New File:\n");
				out.close();			
		} catch (IOException e) {
			Log.e("TAG", "Could not write file " + e.getMessage());
			i = 2/(2 - 2);
		}

		mExternalStorageAvailable = mExternalStorageWriteable = false;
	}

	private void checkExternalStorage() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states,
			// but all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	
	private class MyLocationListener implements LocationListener {

		public double latitude;
		public double longitude;
		public float accuracy;

		private boolean mExternalStorageAvailable = false;
		private boolean mExternalStorageWriteable = false;
		private String s = "";

		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				accuracy = location.getAccuracy();

				Log.d("LOCATION CHANGED", latitude + "");
				Log.d("LOCATION CHANGED", longitude + "");

				Toast.makeText(GPSService.this, latitude + " : " + longitude,
						Toast.LENGTH_LONG).show();

				s = "Lat: "  + latitude + "   ||   Lon: " + longitude  + "\n";
				s = s + "Accuracy: " + accuracy + "\n";
				
				this.write();
			}
		}

		public void write() {
			checkExternalStorage();
			
			int i = 0;
			if (mExternalStorageAvailable == false
					|| mExternalStorageWriteable == false)
				i = 2/(2 - 2);

			File root = Environment.getExternalStorageDirectory();
			File file = new File(root, "gpslog.txt");
			
			try {
					FileWriter filewriter = new FileWriter(file, true);
					BufferedWriter out = new BufferedWriter(filewriter);
					out.write(s + "\n");
					out.close();
				
			} catch (IOException e) {
				Log.e("TAG", "Could not write file " + e.getMessage());
				i = 2/(2 - 2);
			}

			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}

		private void checkExternalStorage() {
			String state = Environment.getExternalStorageState();

			if (Environment.MEDIA_MOUNTED.equals(state)) {
				// We can read and write the media
				mExternalStorageAvailable = mExternalStorageWriteable = true;
			} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
				// We can only read the media
				mExternalStorageAvailable = true;
				mExternalStorageWriteable = false;
			} else {
				// Something else is wrong. It may be one of many other states,
				// but all we need
				// to know is we can neither read nor write
				mExternalStorageAvailable = mExternalStorageWriteable = false;
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}
}
