package net.hackergarten.android.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.DateUtils;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.location.LocationHelper;
import net.hackergarten.android.app.model.Event;

import java.util.Date;

public class EventDetailActivity extends Activity {

	private static final String TAG = "EventDetailActivity";

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

		Button checkinButton = (Button) findViewById(R.id.CheckinButton);

		boolean eventIsInRange = LocationHelper.eventIsInRange(this, event);
		if (!eventIsInRange) {
			Toast.makeText(EventDetailActivity.this, "You are not within range", 1000).show();
			checkinButton.setEnabled(false);
			return;
		}

		Date eventTime = event.getTimeUST();

		// between 10 min before
		if (DateUtils.isTimeSoonerThan(eventTime, DateUtils.TEN_MINUTES)) {
			Toast.makeText(EventDetailActivity.this, "It's too early to check-in. Please come back later!", 1000).show();
			checkinButton.setEnabled(false);
			return;
		}

		// 5 hours after start
		if (DateUtils.isTimeGreaterThan(eventTime, DateUtils.FIVE_HOURS)) {
			Toast.makeText(EventDetailActivity.this, "Sorry it is too late to check-in.", 1000).show();
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
						}
						);
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