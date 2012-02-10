package org.hackergarten.gwt.hackergarten.client.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hackergarten.gwt.hackergarten.client.service.domain.Event;

//TODO: Mock!
public class CheckInService {

	public void checkIn(String email, Long eventId) {
	}

	public List<Event> listUpcomingEvents() {
		Date date = new Date();
		date.setHours(0);
		return Arrays.asList(new Event(new Date(), "Somewhere", 50.008255,
				8.063672, "Haskel for dummies", "Only for Sylvain", null),
				new Event(new Date(), "Lugano", 46.008255, 8.963672,
						"Extreme PowerPoint session", "Only for Fede", null),
				new Event(date, "Lugano", 46.008255, 8.963672,
						"Convince with PowerPoint", "Only for Fede", null),
				new Event(date, "Somewhere", 50.008255, 8.063672,
						"Haskel for dummies", "Only for Sylvain", null));
	}
}
