package net.hackergarten.android.app.client;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final int TEN_MINUTES = (1000 * 60 * 10);
	public static final int FIVE_HOURS = 1000 * 60 * 60 * 5;

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

	public static Date currentDateInUTC() {
		return new Date(getUTCTimeMillis());
	}

	public static boolean isTimeGreaterThan(Date eventTime, int threshold) {
		return currentDateInUTC().getTime()-eventTime.getTime() >= threshold;
	}

	public static boolean isTimeSoonerThan(Date eventTime, int threshold) {
		return currentDateInUTC().getTime()-eventTime.getTime() <= -threshold;
	}
	
	public static boolean isTimeBetween(Date eventTime, int soonerThreshold, int laterThreshold) {
		if (!DateUtils.isTimeSoonerThan(eventTime, soonerThreshold)) {
			if (!DateUtils.isTimeGreaterThan(eventTime, laterThreshold)) {
				return true;
			} 
		} 
		return false;
	}
}
