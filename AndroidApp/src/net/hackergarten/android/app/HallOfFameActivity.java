package net.hackergarten.android.app;

import java.util.List;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.User;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HallOfFameActivity extends Activity {
	private ArrayAdapter<User> fEventAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.halloffame);
		queryHallOfFameUsers();

		ListView listView = (ListView) findViewById(R.id.hallOfFameList);
		fEventAdapter = new ArrayAdapter<User>(this, R.id.hallOfFameList) {
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder;
				if (convertView == null) { 
					convertView = getLayoutInflater().inflate(R.layout.halloffame_item, parent, false);
					holder = new ViewHolder();
					holder.name = (TextView) convertView.findViewById(R.id.hallOfFameName);
					holder.company = (TextView) convertView.findViewById(R.id.hallOfFameCompany);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				User event = getItem(position);
				holder.name.setText(event.getName());
				holder.company.setText(event.getCompany());
				return convertView;
			}
		};
		listView.setAdapter(fEventAdapter);
	}
	
	private void queryHallOfFameUsers() {
		new HackergartenClient().listHallOfFame(new AsyncCallback<List<User>>() {
			
			public void onSuccess(final List<User> result) {
				runOnUiThread(new Runnable() {
					
					public void run() {
						fEventAdapter.clear();
						for (User user : result) {
							fEventAdapter.add(user);
						}
					}
				});
			}
			
			public void onFailure(final Throwable t) {
				runOnUiThread(new Runnable() {
					
					public void run() {
						Toast.makeText(HallOfFameActivity.this, "Failed to query server.", Toast.LENGTH_LONG).show();
						Log.e(MainActivity.class.getName(), "Failed to contact server.", t);
					}
				});
				
			}
			
		});
	}
	
	static class ViewHolder {
		TextView name;
		TextView company;
	}
}