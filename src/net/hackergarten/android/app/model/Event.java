package net.hackergarten.android.app.model;

import java.util.Date;

public class Event {

	private String id;
	private String location;
	private double latitude;
	private double longitude;
	private Date timeUST;
	private String subject;
	private String description;
	private String initiator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public long getIdAsLong() {
		try {
			return Long.parseLong(id);
		} catch (NumberFormatException nfe) {
			return id != null ? id.hashCode() : -1;
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getTimeUST() {
		return timeUST;
	}

	public void setTimeUST(Date timeUST) {
		this.timeUST = timeUST;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", location=" + location + ", latitude="
				+ latitude + ", longitude=" + longitude + ", timeUST="
				+ timeUST + ", subject=" + subject + ", description="
				+ description + ", initiator=" + initiator + "]";
	}

}
