package net.hackergarten.android.app;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;
import static android.view.View.*;

public class RegisterActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ApplicationSettings settings = new ApplicationSettings(this); 

		LinearLayout listLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.register, null);
		TableLayout tableView = (TableLayout) listLayout
				.findViewById(R.id.tableLayout1);

		setContentView(R.layout.register);

		Button registerBtn = (Button) findViewById(R.id.button_register);
		registerBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				User user = new User();
				final String emailValue = getFieldValue(R.id.editTextEmail);
				if (emailValue.trim().length() == 0) {
					Toast.makeText(RegisterActivity.this,
							getString(R.string.register_failure_email),
							Toast.LENGTH_SHORT).show();
					return;
				}
				user.setEmail(emailValue);
				user.setName(getFieldValue(R.id.editTextPersonName));
				user.setCompany(getFieldValue(R.id.editTextCompany));
				user.setTwitterUrl(getFieldValue(R.id.editTextTwitter));
				user.setBlogUrl(getFieldValue(R.id.editTextBlog));

				new HackergartenClient().registerUser(user,
						new AsyncCallback<Void>() {

							public void onSuccess(Void result) {
								showMessage(getString(R.string.register_success));
								settings.registerUser(emailValue);
								Intent intent = new Intent(
										RegisterActivity.this,
										MainActivity.class);
								startActivity(intent);
							}

							public void onFailure(final Throwable t) {
								showMessage(getString(R.string.register_failure)
										+ "\n" + t.getMessage());
							}

						});
			}

			private String getFieldValue(int widgetId) {
				EditText textField = (EditText) findViewById(widgetId);
				String value = textField.getText().toString();
				return value;
			}
		});
	}

	private void showMessage(final String message) {
		runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(RegisterActivity.this, message,
						Toast.LENGTH_LONG).show();
			}
		});
	}
}