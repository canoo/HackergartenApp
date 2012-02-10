package org.hackergarten.gwt.hackergarten.client.service.domain;

import java.io.Serializable;
import java.util.Date;

//TODO: Mock!
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date start;
	private String location;
	private double latitude;
	private double longitude;
	private String subject;
	private String description;

	private User initiator;

	public Date getStart() {
		return start;
	}

	public Event(Date start, String location, double latitude,
			double longitude, String subject, String description, User initiator) {
		this.start = start;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.subject = subject;
		this.description = description;
		this.initiator = initiator;
	}

	public void setStart(Date start) {
		this.start = start;
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

	public void setLatitude(double inLatitude) {
		latitude = inLatitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double inLongitude) {
		longitude = inLongitude;
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

	public User getInitiator() {
		return initiator;
	}

	public void setInitiator(User inInitiator) {
		initiator = inInitiator;
	}

	public Long getId() {
		return 1L;
	}
}
