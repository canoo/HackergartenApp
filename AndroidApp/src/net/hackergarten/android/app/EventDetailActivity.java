package net.hackergarten.android.app;

import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.location.LocationHelper;
import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EventDetailActivity extends Activity {

	private static final String TAG = "EventDetailActivity";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Event event = (Event) getIntent().getExtras().get("event");
        
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

		Button checkinButton = (Button) findViewById(R.id.CheckinButton);
		boolean eventIsInRange = LocationHelper.eventIsInRange(this, event);
		if (!eventIsInRange) {
			Toast.makeText(EventDetailActivity.this, "You are not within range", 1000).show();
			checkinButton.setEnabled(false);
			return;
		}

		checkinButton.setEnabled(true);
		checkinButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						HackergartenClient client = new HackergartenClient();
						ApplicationSettings settings = new ApplicationSettings(EventDetailActivity.this);
						client.checkInUser(settings.getRegisteredUser(), event.getId(), new AsyncCallback<Void>() {
							public void onSuccess(Void result) {
								runOnUiThread(new Runnable() {
									public void run() {
										Log.d(TAG, "checkin success");
										Toast.makeText(EventDetailActivity.this, "Checkin OK", 500).show();
										finish();
									}
								});

							}

							public void onFailure(Throwable t) {
								runOnUiThread(new Runnable() {
									public void run() {
										Log.d(TAG, "checkin failed");
										Toast.makeText(EventDetailActivity.this, "Checkin Failed", 500).show();
									}
								});
							}
						});
					}
				});
    }
	
}