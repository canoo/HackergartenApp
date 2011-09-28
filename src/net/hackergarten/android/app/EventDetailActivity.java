package net.hackergarten.android.app;

import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Event event = (Event) getIntent().getExtras().get("event");
        
        View detailView = getLayoutInflater().inflate(R.layout.event_detail, null);
        TextView subjectView = (TextView) detailView.findViewById(R.id.eventDetailSubject);
        subjectView.setText(event.getSubject());
        TextView locationView = (TextView) detailView.findViewById(R.id.eventDetailLocation);
        locationView.setText(event.getLocation());
        TextView descriptionView = (TextView) detailView.findViewById(R.id.eventDetailDescription);
        descriptionView.setText(event.getDescription());
        TextView timeView = (TextView) detailView.findViewById(R.id.eventDetailTime);
        timeView.setText(event.getTimeUST().toLocaleString());
        TextView initiatorView = (TextView) detailView.findViewById(R.id.eventDetailInitiator);
        initiatorView.setText(event.getInitiator());
        
        setContentView(detailView);
    }
	
}