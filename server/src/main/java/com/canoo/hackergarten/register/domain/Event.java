package com.canoo.hackergarten.register.domain;

import com.google.appengine.api.datastore.Key;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;
	private String location;
    private double latitude;
    private double longitude;
	private String subject;
	private String description;
	
	@ManyToOne
	private User initiator;

	public Key getId() {
		return id;
	}

	public Date getStart() {
		return start;
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
}
