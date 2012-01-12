package net.hackergarten.android.app;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Event event = (Event) getIntent().getExtras().get("event");

        setContentView(R.layout.event_detail);
        
        TextView subjectView = (TextView) findViewById(R.id.eventDetailSubject);
        subjectView.setText(event.getSubject());
        TextView locationView = (TextView) findViewById(R.id.eventDetailLocation);
        locationView.setText(event.getLocation());
        TextView descriptionView = (TextView) findViewById(R.id.eventDetailDescription);
        descriptionView.setText(event.getDescription());
        TextView timeView = (TextView) findViewById(R.id.eventDetailTime);
        timeView.setText(event.getTimeUST().toLocaleString());
        TextView initiatorView = (TextView) findViewById(R.id.eventDetailInitiator);
        initiatorView.setText(event.getInitiator());
        
        Button showOnMap = (Button) findViewById(R.id.showOnMapButton);
        showOnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View inView) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + event.getLatitude() + "," + event.getLongitude()));
                startActivity(mapIntent);
            }
        });
        
    }
	
}