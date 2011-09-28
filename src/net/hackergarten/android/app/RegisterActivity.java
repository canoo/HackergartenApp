package net.hackergarten.android.app;

import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

		LinearLayout listLayout = (LinearLayout) getLayoutInflater().inflate(
				R.layout.register, null);
		TableLayout tableView = (TableLayout) listLayout
				.findViewById(R.id.tableLayout1);

		setContentView(R.layout.register);

		Button registerBtn = (Button) findViewById(R.id.button_register);
		registerBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				User user = new User();
				String emailValue = getFieldValue(R.id.editTextEmail);
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
								runOnUiThread(new Runnable() {

									public void run() {
										Toast.makeText(RegisterActivity.this,
												getString(R.string.register_success),
												Toast.LENGTH_LONG).show();
										Intent intent = new Intent(
												RegisterActivity.this,
												MainActivity.class);
										startActivity(intent);
									}
								});
							}

							public void onFailure(final Throwable t) {
								runOnUiThread(new Runnable() {

									public void run() {

										Toast.makeText(RegisterActivity.this,
												getString(R.string.register_failure) + "\n" + t.getMessage(),
												Toast.LENGTH_LONG).show();
									}
								});
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
}