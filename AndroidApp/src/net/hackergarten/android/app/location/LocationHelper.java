package net.hackergarten.android.app.location;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import net.hackergarten.android.app.model.Event;

public class LocationHelper {

	private static final int RANGE_DISTANCE = 500;

	public static boolean eventIsInRange(Activity activity, Event event) {
		Location eventLocation = new Location("eventLocation");
		eventLocation.setLatitude(event.getLatitude());
		eventLocation.setLongitude(event.getLongitude());
		Location lastKnownLocation = getLastKnownLocation(activity);
		if (lastKnownLocation != null && lastKnownLocation.distanceTo(eventLocation) < RANGE_DISTANCE) {
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
