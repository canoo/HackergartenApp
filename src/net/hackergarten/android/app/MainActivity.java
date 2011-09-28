package net.hackergarten.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.Event;

import java.util.List;

public class MainActivity extends Activity {

    private EventArrayListAdapter fEventAdapter;
    private ApplicationSettings fSettings;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fSettings = new ApplicationSettings(this);

        final LinearLayout listLayout = (LinearLayout) getLayoutInflater().inflate(
                R.layout.main, null);
        final ListView listView = (ListView) listLayout
                .findViewById(R.id.eventListView);
        fEventAdapter = new EventArrayListAdapter(this, getLayoutInflater());
        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                final Event event = fEventAdapter.getEntries().get(position);
                final Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }

        });
        listView.setAdapter(fEventAdapter);

        final Button registerButton = (Button) listLayout
                .findViewById(R.id.registerButton);
        final TextView welcomeMessage = (TextView) listLayout
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
            public void onClick(final View v) {
                final Intent intent = new Intent(MainActivity.this,
                        RegisterActivity.class);
                startActivity(intent);
            }
        });

        setContentView(listLayout);

        queryEvents();
        final CurrentEventChecker ch = new CurrentEventChecker(this);
        ch.checkForEvent();
    }

    private void queryEvents() {
        new HackergartenClient().listUpcomingEvents(new AsyncCallback<List<Event>>() {

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
                        Toast.makeText(MainActivity.this, "Failed to contact server.", Toast.LENGTH_LONG).show();
                        Log.e(MainActivity.class.getName(), "Failed to contact server.", t);
                    }

                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (fSettings.isUserRegistered()) {
            menu.add(0, 1, 0, "log out");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 1:
                fSettings.logOut();
                final Button registerButton = (Button) findViewById(R.id.registerButton);
                final TextView welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
                registerButton.setVisibility(View.VISIBLE);
                welcomeMessage.setVisibility(View.INVISIBLE);
        }
        return true;
    }
}