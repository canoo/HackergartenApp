package org.hackergarten.gwt.hackergarten.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Hackergarten implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
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
            }
        });

        buttonRegister.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                hidePanels();
                panel3.setVisible(true);
            }
        });

    }
    public void hidePanels() {
        RootPanel.get("slot1").setVisible(false);
        RootPanel.get("slot2").setVisible(false);
        RootPanel.get("slot3").setVisible(false);
    }
    
}
