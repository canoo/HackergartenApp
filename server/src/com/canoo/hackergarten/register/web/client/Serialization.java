package com.canoo.hackergarten.register.web.client;

import com.canoo.hackergarten.register.web.Address;
import com.canoo.hackergarten.register.web.Contact;
import org.restlet.client.resource.Result;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Serialization implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side Contact
     * resource.
     */
    private final ContactResourceProxy contactResource = GWT
            .create(ContactResourceProxy.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button getButton = new Button("get");
        final Button updateButton = new Button("update");

        // Contact fields
        final TextBox cTbFirstName = new TextBox();
        final TextBox cTbLastName = new TextBox();
        final TextBox cTbAge = new TextBox();
        // Home address fields
        final TextBox haTbLine1 = new TextBox();
        final TextBox haTbLine2 = new TextBox();
        final TextBox haTbZipCode = new TextBox();
        final TextBox haTbCity = new TextBox();
        final TextBox haTbCountry = new TextBox();

        VerticalPanel contactPanel = new VerticalPanel();
        contactPanel.add(cTbFirstName);
        contactPanel.add(cTbLastName);
        contactPanel.add(cTbAge);
        RootPanel.get("contactContainer").add(contactPanel);

        VerticalPanel homeAddressPanel = new VerticalPanel();
        homeAddressPanel.add(haTbLine1);
        homeAddressPanel.add(haTbLine2);
        homeAddressPanel.add(haTbZipCode);
        homeAddressPanel.add(haTbCity);
        homeAddressPanel.add(haTbCountry);
        RootPanel.get("homeAddressContainer").add(homeAddressPanel);

        RootPanel.get("buttonContainer").add(getButton);
        RootPanel.get("buttonContainer").add(updateButton);

        cTbAge.addFocusHandler(new FocusHandler() {
            public void onFocus(FocusEvent event) {
                cTbAge.removeStyleName("error");
            }
        });
        // Focus the cursor on the first name field when the app loads
        cTbFirstName.setFocus(true);
        updateButton.setVisible(false);

        // Define a dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        closeButton.getElement().setId("closeButton");
        final Label textToServerLabel = new Label();
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(textToServerLabel);
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        // Get the contact
        getButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                // Set up the contact resource
                contactResource.getClientResource().setReference(
                        "/contacts/123");

                // Retrieve the contact
                contactResource.retrieve(new Result<Contact>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Get contact");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(Contact contact) {
                        // Contact fields
                        cTbFirstName.setText(contact.getFirstName());
                        cTbLastName.setText(contact.getLastName());
                        cTbAge.setText(Integer.toString(contact.getAge()));

                        if (contact.getHomeAddress() != null) {
                            Address home = contact.getHomeAddress();
                            // Home address fields
                            haTbLine1.setText(home.getLine1());
                            haTbLine2.setText(home.getLine2());
                            haTbZipCode.setText(home.getZipCode());
                            haTbCity.setText(home.getCity());
                            haTbCountry.setText(home.getCountry());
                        }
                        updateButton.setVisible(true);
                    }
                });
            }
        });

        updateButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                Contact contact = new Contact();
                contact.setFirstName(cTbFirstName.getValue());
                contact.setLastName(cTbLastName.getValue());
                try {
                    contact.setAge(Integer.parseInt(cTbAge.getValue()));
                } catch (Exception e) {
                    cTbAge.addStyleName("error");
                    dialogBox.setText("Update contact");
                    textToServerLabel.setText("Error: Invalid age specified.");
                    dialogBox.center();
                    closeButton.setFocus(true);
                    return;
                }

                Address homeAddress = null;
                if (haTbLine1.getValue() != null
                        || haTbLine2.getValue() != null
                        || haTbZipCode.getValue() != null
                        || haTbCity.getValue() != null
                        || haTbCountry.getValue() != null) {
                    homeAddress = new Address();
                    homeAddress.setLine1(haTbLine1.getValue());
                    homeAddress.setLine2(haTbLine2.getValue());
                    homeAddress.setZipCode(haTbZipCode.getValue());
                    homeAddress.setCity(haTbCity.getValue());
                    homeAddress.setCountry(haTbCountry.getValue());
                }
                contact.setHomeAddress(homeAddress);

                // Update the contact
                contactResource.store(contact, new Result<Void>() {
                    public void onFailure(Throwable caught) {
                        dialogBox.setText("Update contact");
                        textToServerLabel.setText("Error: "
                                + caught.getMessage());
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }

                    public void onSuccess(Void v) {
                        dialogBox.setText("Update contact");
                        textToServerLabel
                                .setText("Contact successfully updated");
                        dialogBox.center();
                        closeButton.setFocus(true);
                    }
                });
            }
        });
    }
}
