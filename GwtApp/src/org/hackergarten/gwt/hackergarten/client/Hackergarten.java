package org.hackergarten.gwt.hackergarten.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.googlecode.gwtphonegap.client.*;
import com.googlecode.gwtphonegap.client.geolocation.GeolocationCallback;
import com.googlecode.gwtphonegap.client.geolocation.Position;
import com.googlecode.gwtphonegap.client.geolocation.PositionError;
import org.hackergarten.gwt.hackergarten.client.service.CheckInService;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Hackergarten implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				Window.alert("General error: " + e.getMessage());
			}
		});

		final PhoneGap phoneGap = GWT.create(PhoneGap.class);

		phoneGap.addHandler(new PhoneGapAvailableHandler() {
			public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
                final Button buttonHome = new Button("Home");
                final Button buttonCheckin = new Button("Checkin");
                final Button buttonRegister = new Button("Register");
                RootPanel buttonHolder = RootPanel.get("buttonholder");
                final RootPanel panel1 = RootPanel.get("slot1");
                final RootPanel panel2 = RootPanel.get("slot2");
                final RootPanel panel3 = RootPanel.get("slot3");

        //        hidePanels();
        //        panel1.setVisible(true);

                buttonHolder.add(buttonHome);
                buttonHolder.add(buttonCheckin);
                buttonHolder.add(buttonRegister);

                buttonHome.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        hidePanels();
                        panel1.setVisible(true);
                    }
                });

                buttonCheckin.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        hidePanels();
                        panel2.setVisible(true);
        				startCheckin(phoneGap, new CheckInService());
                    }
                });

                buttonRegister.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        hidePanels();
                        panel3.setVisible(true);
                    }
                });

			}
		});

		phoneGap.addHandler(new PhoneGapTimeoutHandler() {
			public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
				Window.alert("PhoneGap timeout event received");
			}
		});

		phoneGap.initializePhoneGap();

   }
    public void hidePanels() {
        RootPanel.get("slot1").setVisible(false);
        RootPanel.get("slot2").setVisible(false);
        RootPanel.get("slot3").setVisible(false);
    }
    
	private void startCheckin(PhoneGap phoneGap, CheckInService service) {
		// String uuid = "unknown";
		final CheckInWidget widget = new CheckInWidget(service);

		if (phoneGap != null) {
			// if (phoneGap.getDevice() != null) {
			// uuid = phoneGap.getDevice().getUuid();
			phoneGap.getGeolocation().getCurrentPosition(
					new GeolocationCallback() {
						public void onSuccess(Position position) {
							widget.updatePosition(position.getCoordinates()
									.getLongitude(), position.getCoordinates()
									.getLatitude());
						}

						public void onFailure(PositionError error) {
							Window.alert("Not able to get current position");
						}
					});
			// }
		}
		RootPanel.get("slot2").add(widget);
	}

}
