package com.example.tacho;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;

public class MainActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		com.google.android.gms.location.LocationListener {

	public TextView distanceView, speedView;
	public float distance;
	public float d;	

	public float speed;
	public Location aktuelleLocation, alteLocation;
	public LocationClient mLocationClient;
	public LocationRequest mLocationRequest;

	Button startButton, stopButton, resetButton;
	public boolean messen;

	private TextView textTimer;
	private long startTime = 0L;
	private Handler myHandler = new Handler();
	long timeInMillies = 0L;
	long timeSwap = 0L;
	long finalTime = 0L;
	int seconds;
	int minutes;
	int milliseconds;

	private TachoView myView;

	public float getDistance(Location loc1, Location loc2) {
		float results[] = new float[1];
		Location.distanceBetween(loc1.getLatitude(), loc1.getLongitude(),
				loc2.getLatitude(), loc2.getLongitude(), results);
		return results[0];
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		//LocationClient der googleplayservices-library
		//gibt exaktere Werte als die Methoden, die von Android zur Verfügung gestellt werden
		mLocationClient = new LocationClient(this, this, this);

		mLocationRequest = LocationRequest.create();
		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		// Set the update interval to 1 second
		mLocationRequest.setInterval(1000);
		// Set the fastest update interval to 500 milliseconds
		mLocationRequest.setFastestInterval(500);

		textTimer = (TextView) findViewById(R.id.textView1);
		distanceView = (TextView) findViewById(R.id.textView8);
		speedView = (TextView) findViewById(R.id.textView3);
		myView = (TachoView) findViewById(R.id.tachoView1);
		startButton = (Button) findViewById(R.id.button2);
		stopButton = (Button) findViewById(R.id.button1);
		resetButton = (Button) findViewById(R.id.button3);

		startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				messen = true;
				startTime = SystemClock.uptimeMillis();
				myHandler.postDelayed(updateTimerMethod, 0);
			}
		});
		stopButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				messen = false;
				speed = 0;
				timeSwap += timeInMillies;
				myHandler.removeCallbacks(updateTimerMethod);
				
				speedView.setText("" + speed); 
				//roter Zeiger auf Tacho auf 0-Position
				myView.setX1(90);
				myView.setY1(310);
			}
		});

		resetButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				messen = false;
				textTimer.setText("00:00:00");
				distance = 0;
				speed = 0;

				myHandler.removeCallbacks(updateTimerMethod);
				timeSwap = 0;

				speedView.setText("" + speed);
				distanceView.setText("" + distance);
				myView.setX1(90);
				myView.setY1(310);
			}
		});
	}

	private Runnable updateTimerMethod = new Runnable() {
		public void run() {
			timeInMillies = SystemClock.uptimeMillis() - startTime;
			finalTime = timeSwap + timeInMillies;

			seconds = (int) (finalTime / 1000);
			minutes = seconds / 60;
			seconds = seconds % 60;
			milliseconds = (int) (finalTime % 1000);

			textTimer.setText("" + minutes + ":"
					+ String.format("%02d", seconds) + ":"
					+ String.format("%03d", milliseconds));
			myHandler.postDelayed(this, 0);
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	public void onLocationChanged(Location location) {
		if ((location != null) && (messen == true)) {

			aktuelleLocation = mLocationClient.getLastLocation();
			speed = location.getSpeed();
			
			// je nach speed-Wert wird die Nadel auf dem Tacho neu mit anderen x,y Koordinaten gezeichnet
			if (speed >= 0 && speed <= 5) {
				myView.setX1(100);
				myView.setY1(300);
			} else if (speed >= 5 && speed <= 10) {
				myView.setX1(130);
				myView.setY1(240);
			} else if (speed >= 10 && speed <= 15) {
				myView.setX1(150);
				myView.setY1(200);
			} else if (speed >= 15 && speed <= 20) {
				myView.setX1(165);
				myView.setY1(175);
			} else if (speed >= 20 && speed <= 25) {
				myView.setX1(200);
				myView.setY1(175);
			} else if (speed >= 25 && speed <= 30) {
				myView.setX1(230);
				myView.setY1(150);
			} else if (speed >= 30 && speed <= 35) {
				myView.setX1(260);
				myView.setY1(125);
			} else if (speed >= 35 && speed <= 40) {
				myView.setX1(290);
				myView.setY1(100);
			} else if (speed >= 40 && speed <= 45) {
				myView.setX1(320);
				myView.setY1(80);
			} else if (speed >= 45 && speed <= 50) {
				myView.setX1(350);
				myView.setY1(85);
			} else if (speed >= 50 && speed <= 55) {
				myView.setX1(380);
				myView.setY1(80);
			} else if (speed >= 55 && speed <= 60) {
				myView.setX1(420);
				myView.setY1(90);
			} else if (speed >= 60 && speed <= 65) {
				myView.setX1(450);
				myView.setY1(100);
			} else if (speed >= 65 && speed <= 70) {
				myView.setX1(480);
				myView.setY1(120);
			} else if (speed >= 70 && speed <= 75) {
				myView.setX1(520);
				myView.setY1(150);
			} else if (speed >= 75 && speed <= 80) {
				myView.setX1(540);
				myView.setY1(160);
			} else if (speed >= 80 && speed <= 85) {
				myView.setX1(570);
				myView.setY1(190);
			} else if (speed >= 85 && speed <= 90) {
				myView.setX1(590);
				myView.setY1(210);
			} else if (speed >= 90 && speed <= 95) {
				myView.setX1(610);
				myView.setY1(240);
			} else if (speed >= 95 && speed <= 100) {
				myView.setX1(630);
				myView.setY1(285);
			} else if (speed >= 100 && speed <= 105) {
				myView.setX1(650);
				myView.setY1(325);
			} else if (speed >= 106) {
				myView.setX1(660);
				myView.setX2(375);
			}

			// Damit nur berechnet wird, sobald 2 Punkte vorhanden sind
			// Berechnung der Distanz in m
			if (alteLocation != null) {
				d = (int) Math
						.round(getDistance(aktuelleLocation, alteLocation));
				distance += d;
			}	

			speedView.setText("" + speed);
			distanceView.setText("" + distance);
		}
		alteLocation = aktuelleLocation;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	public void onClick(DialogInterface dialog, int which) {
		if (which == DialogInterface.BUTTON_NEUTRAL) {
			textTimer
					.setText("Sorry, location is not determined. To fix this please enable location providers");
		} else if (which == DialogInterface.BUTTON_POSITIVE) {
			startActivity(new Intent(
					android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
	}

	@Override
	public void onDisconnected() {
	}
}
