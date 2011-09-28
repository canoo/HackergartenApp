package net.hackergarten.android.app;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CheckinActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin);
        
        Button checkinButton = (Button) findViewById(R.id.CheckinButton);
        checkinButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HackergartenClient client = new HackergartenClient();
				client.checkInUser("", "", new AsyncCallback<Void>() {
					public void onSuccess(Void result) {
						Toast.makeText(CheckinActivity.this, "Checkin OK", 500);
					}
					
					public void onFailure(Throwable t) {
						Toast.makeText(CheckinActivity.this, "Checkin Failed", 500);
					}
				});
			}
		});
    }
}