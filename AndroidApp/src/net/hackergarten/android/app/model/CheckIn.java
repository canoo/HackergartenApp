package net.hackergarten.android.app.model;

import java.io.Serializable;
import java.util.Date;

public class CheckIn implements Serializable {

	private String userId;
	private String eventId;
	private Date checkInTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

}
