package net.hackergarten.android.app.location;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import net.hackergarten.android.app.model.Event;

public class LocationHelper {

	private static final int ONE_KILOMETER = 1000;

	public static boolean eventIsInRange(Activity activity, Event event) {
		return eventIsInRange(event, getLastKnownLocation(activity));
	}

	public static boolean eventIsInRange(Event event, Location lastKnownLocation) {
		Location eventLocation = new Location("eventLocation");
		eventLocation.setLatitude(event.getLatitude());
		eventLocation.setLongitude(event.getLongitude());
		if (lastKnownLocation != null && lastKnownLocation.distanceTo(eventLocation) < ONE_KILOMETER) {
			return true;
		}
		return false;
	}

	public static LocationManager getLocationManager(Activity activity) {
		return (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);

	}

	public static Location getLastKnownLocation(Activity activity) {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		String bestProvider = getLocationManager(activity).getBestProvider(criteria, true);
		return getLocationManager(activity).getLastKnownLocation(bestProvider);
	}

}
