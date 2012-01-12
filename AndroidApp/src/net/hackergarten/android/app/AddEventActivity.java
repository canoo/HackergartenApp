package net.hackergarten.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.Event;

import java.util.Date;

public class AddEventActivity extends Activity {

    private ViewSwitcher switcher;
    private TextView fSubject;
    private TextView fDescription;
    private TextView fInitiator;
    private DatePicker fDate;
    private TimePicker fTime;
    private TextView fLocation;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View addEventLayout = getLayoutInflater().inflate(R.layout.addevent, null);
        final View addEventLayout2 = getLayoutInflater().inflate(R.layout.addevent2, null);
        switcher = new ViewSwitcher(this);
        switcher.addView(addEventLayout);
        switcher.addView(addEventLayout2);
        switcher.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        setContentView(switcher);

        Button next = (Button) addEventLayout.findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View inView) {
                switcher.setDisplayedChild(1);
            }
        });
        Button previous = (Button) addEventLayout2.findViewById(R.id.buttonPrevious);
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View inView) {
                switcher.setDisplayedChild(0);
            }
        });

        Button save = (Button) addEventLayout2.findViewById(R.id.buttonSave);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View inView) {
                addEvent();
            }
        });

        fSubject = (TextView) addEventLayout.findViewById(R.id.eventDetailSubject);
        fDescription = (TextView) addEventLayout.findViewById(R.id.eventDetailDescription);
        fInitiator = (TextView) addEventLayout.findViewById(R.id.eventDetailInitiator);

        fDate = (DatePicker) addEventLayout2.findViewById(R.id.eventDetailDate);
        fTime = (TimePicker) addEventLayout2.findViewById(R.id.eventDetailTime);
        fLocation = (TextView) addEventLayout2.findViewById(R.id.eventDetailLocation);
        fLocation = (TextView) addEventLayout2.findViewById(R.id.eventDetailLocation);

        //Fill initiator
        ApplicationSettings appSettings= new ApplicationSettings(this);
        fInitiator.setText(appSettings.getRegisteredUser());
    }

    private void addEvent() {
        HackergartenClient client = new HackergartenClient();
        final Event event = new Event();


        event.setInitiator(fInitiator.getText().toString());
        event.setDescription(fDescription.getText().toString());
        event.setSubject(fSubject.getText().toString());
        event.setLocation(fLocation.getText().toString());
        event.setTimeUST(new Date(fDate.getYear(), fDate.getMonth(), fDate.getDayOfMonth(), fTime.getCurrentHour(), fTime.getCurrentMinute()));

        client.addEvent(event, new AsyncCallback<Void>() {
            public void onSuccess(final Void result) {
                HackersHelper.showMessage(AddEventActivity.this, "successfully created new event.");
                Intent intent = new Intent(
                        AddEventActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }

            public void onFailure(final Throwable t) {
                HackersHelper.showMessage(AddEventActivity.this, "error creating event: " + t.getMessage());

            }
        });

    }

}