package net.hackergarten.android.app.client;

import java.util.Calendar;

class DateUtils {

	/**
	 * @return the timezone offset of the current device relative to UTC
	 */
	static long getTimeZoneOffsetInMillis() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
	}

	/**
	 * @return current UTC time in milliseconds taking into account timezone and
	 *         dst offset
	 */
	static long getUTCTimeMillis() {
		return getUTCTimeFromLocalMillis(System.currentTimeMillis());
	}

	/**
	 * Convert a UTC timestamp into device local time
	 * 
	 * @param utcMillis
	 * @return
	 */
	static long getLocalTimeFromUTCMillis(long utcMillis) {
		return utcMillis - getTimeZoneOffsetInMillis();
	}

	/**
	 * Convert a local timestamp into UTC time
	 * 
	 * @param localTimeMillis
	 * @return
	 */
	static long getUTCTimeFromLocalMillis(long localMillis) {
		return localMillis + getTimeZoneOffsetInMillis();
	}

}
