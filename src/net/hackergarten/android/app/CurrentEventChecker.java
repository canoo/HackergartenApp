package net.hackergarten.android.app;

import java.util.List;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * -
 * 
 * @author asocaciu
 */
class CurrentEventChecker implements LocationListener {
	
	private static final String TAG = CurrentEventChecker.class.getSimpleName();

	private Activity activity;
	private List<Event> currentEvents;
	
	public CurrentEventChecker(Activity context) {
		this.activity = context;
	}

	public void checkForEvent() {
		Log.d(TAG, "checking for event");
		final HackergartenClient client = new HackergartenClient();
		client.listUpcomingEvents(new AsyncCallback<List<Event>>() {

			public void onSuccess(List<Event> result) {
				Log.d(TAG, "got Events " + result.size());
				currentEvents = result;
				if (!currentEvents.isEmpty()) {
					obtainCurrentLocation();
				}
				client.close();
			}

			public void onFailure(Throwable t) {
				Log.d(TAG, "failed checking for events", t);
				client.close();
			}
		});
	}

	private void obtainCurrentLocation() {
		activity.runOnUiThread(new Runnable() {
			
			public void run() {
				LocationManager locationManager = getLocationManager();
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_COARSE);
				String bestProvider = locationManager.getBestProvider(criteria, true);
				Log.d(TAG, "checking for location from provider " + bestProvider);
				locationManager.requestLocationUpdates(bestProvider, 0, 0, CurrentEventChecker.this);
			}
		});
	}

	private LocationManager getLocationManager() {
		return (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public void onLocationChanged(Location location) {
		getLocationManager().removeUpdates(this);
		Log.d(TAG, "got location " + location);
		Location eventLocation = new Location("me");
		for (Event event : currentEvents) {
			eventLocation.setLatitude(event.getLatitude());
			eventLocation.setLongitude(event.getLongitude());
			if (location.distanceTo(eventLocation) < 500) {
				//we are at this event
				Log.d(TAG, "starting activity for event " + event);
				Intent intent = new Intent(activity, CheckinActivity.class);
				intent.putExtra("eventId", event.getId());
				activity.startActivity(intent);
			}
		}
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
