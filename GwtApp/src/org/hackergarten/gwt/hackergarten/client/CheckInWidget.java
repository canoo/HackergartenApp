package org.hackergarten.gwt.hackergarten.client;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.hackergarten.gwt.hackergarten.client.service.CheckInService;
import org.hackergarten.gwt.hackergarten.client.service.domain.Event;

public class CheckInWidget extends Composite {

	private CheckInService service;
	private double currentLongitude;
	private double currentLatitude;
	private static CheckInWidgetUiBinder uiBinder = GWT
			.create(CheckInWidgetUiBinder.class);

	private final double d2r = (Math.PI / 180.0);

	interface CheckInWidgetUiBinder extends UiBinder<Widget, CheckInWidget> {
	}

	public CheckInWidget(CheckInService service) {
		initWidget(uiBinder.createAndBindUi(this));
		this.service = service;
		this.checkIn.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				checkIn();
			}

		});
		displayLoadingInfo();
	}

	@UiField()
	// More generic since the generic one is enought for our goal
	// TableCellElement center;
	ListBox eventList;

	@UiField()
	Button checkIn;

	private List<Event> events;

	public void updatePosition(double longitude, double latitude) {
		this.currentLatitude = latitude;
		this.currentLongitude = longitude;
		removeLoadingInfo();
		displayEvents(longitude, latitude);
	}

	private void displayEvents(double longitude, double latitude) {
		// TODO: call the listUpcomingEvents service here!
		events = service.listUpcomingEvents();
		Collections.sort(events, new Comparator<Event>() {
			public int compare(Event e1, Event e2) {
				return distanceFrom(e1).compareTo(distanceFrom(e2));
			}
		});
		for (Event event : events) {
			eventList.addItem(event.getSubject());
		}
	}

	private void displayLoadingInfo() {
		eventList.addItem("Loading events...");
	}

	private void removeLoadingInfo() {
		eventList.clear();
	}

	private Double distanceFrom(Event e) {
		double lat1 = e.getLatitude();
		double lat2 = currentLatitude;
		double long1 = e.getLongitude();
		double long2 = currentLongitude;

		double dlong = (long2 - long1) * d2r;
		double dlat = (lat2 - lat1) * d2r;
		double a = pow(sin(dlat / 2.0), 2) + cos(lat1 * d2r) * cos(lat2 * d2r)
				* pow(sin(dlong / 2.0), 2);
		double c = 2 * atan2(sqrt(a), sqrt(1 - a));
		double d = 6367 * c;

		return d;
	}

	private void checkIn() {
		int selectedElement = eventList.getSelectedIndex();
		if (selectedElement >= 0) {
			Event selectedEvent = events.get(selectedElement);
			double distance = distanceFrom(selectedEvent);

			if (!checkInTime(selectedEvent)) {
				Window.alert(events.get(selectedElement).getDescription()
						+ " is not inside your time frame of 1h ("
						+ selectedEvent.getStart() + ")");
				return;
			}

			if (distance >= 0.5) {
				Window.alert(events.get(selectedElement).getDescription()
						+ " is "
						+ distance
						+ " km too far. If you run very fast, you can reach the goal in X min");
				return;
			}

			Window.alert("You have registered for "
					+ selectedEvent.getDescription() + "!! Thank you");
			// TODO: call the checkIn API here!!
			service.checkIn("me@example.org", events.get(selectedElement)
					.getId());
		} else {
			Window.alert("Please select an event");
		}
	}

	private boolean checkInTime(Event selectedEvent) {
		long current = new Date().getTime();
		long start = selectedEvent.getStart().getTime();

		return (abs(current - start) < 1000 * 60 * 60 /* ms */);
	}
}
