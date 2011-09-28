package net.hackergarten.android.app;

import java.util.List;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.Event;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EventArrayListAdapter fEventAdapter;
	private ApplicationSettings fSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		fSettings = new ApplicationSettings(this);

		LinearLayout listLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.main, null);
		ListView listView = (ListView) listLayout
				.findViewById(R.id.eventListView);
		fEventAdapter = new EventArrayListAdapter(this, getLayoutInflater());
		listView.setAdapter(fEventAdapter);

		Button registerButton = (Button) listLayout
				.findViewById(R.id.registerButton);
		TextView welcomeMessage = (TextView) listLayout
				.findViewById(R.id.welcomeMessage);
		if (fSettings.isUserRegistered()) {
			registerButton.setVisibility(View.INVISIBLE);
			welcomeMessage.setVisibility(View.VISIBLE);
			welcomeMessage.setText("Welcome " + fSettings.getRegisteredUser());
		} else {
			registerButton.setVisibility(View.VISIBLE);
			welcomeMessage.setVisibility(View.INVISIBLE);

		}
		registerButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		setContentView(listLayout);

		queryEvents();
		CurrentEventChecker ch = new CurrentEventChecker(this);
		ch.checkForEvent();
	}

	private void queryEvents() {
		new HackergartenClient()
				.listUpcomingEvents(new AsyncCallback<List<Event>>() {

					public void onSuccess(final List<Event> result) {
						runOnUiThread(new Runnable() {

							public void run() {
								fEventAdapter.setEntries(result);
							}
						});
					}

					public void onFailure(final Throwable t) {
						runOnUiThread(new Runnable() {

							public void run() {
								Toast.makeText(MainActivity.this,
										"Failed to query server.",
										Toast.LENGTH_LONG);
								Log.e(MainActivity.class.getName(),
										"Failed to contact server.", t);
							}
						});

					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		if (fSettings.isUserRegistered()) {
			menu.add(0, 1, 0, "log out");
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case 1:
			fSettings.logOut();
			Button registerButton = (Button) findViewById(R.id.registerButton);
			TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
			registerButton.setVisibility(View.VISIBLE);
			welcomeMessage.setVisibility(View.INVISIBLE);
		}
		return true;
	}
}