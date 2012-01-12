package net.hackergarten.android.app;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckinActivity extends Activity {
	
	private static final String TAG = "CheckinActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
        
        final Event event = (Event) getIntent().getSerializableExtra("event");
        
        TextView subject = (TextView)findViewById(R.id.CheckinEventSubject);
        TextView location = (TextView)findViewById(R.id.CheckinEventLocation);
        TextView description = (TextView)findViewById(R.id.CheckinEventDescription);
        TextView time = (TextView)findViewById(R.id.CheckinEventTime);
        
        subject.setText(event.getSubject());
        location.setText(event.getLocation());
        description.setText(event.getDescription());
        time.setText(event.getTimeUST().toGMTString());
        
        Button checkinButton = (Button) findViewById(R.id.CheckinButton);
        checkinButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HackergartenClient client = new HackergartenClient();
				ApplicationSettings settings = new ApplicationSettings(CheckinActivity.this);
				client.checkInUser(settings.getRegisteredUser(), event.getId(), new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						runOnUiThread(new Runnable() {
							public void run() {
								Log.d(TAG, "checkin success");
								Toast.makeText(CheckinActivity.this, "Checkin OK", 500).show();
								finish();
							}
						});

					}

					public void onFailure(Throwable t) {
						runOnUiThread(new Runnable() {
							public void run() {
								Log.d(TAG, "checkin failed");
								Toast.makeText(CheckinActivity.this, "Checkin Failed", 500).show();
							}
						});
					}
				});
			}
		});
    }
}