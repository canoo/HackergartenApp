package net.hackergarten.android.app;


import net.hackergarten.android.app.client.AsyncCallback;
import net.hackergarten.android.app.client.HackergartenClient;
import net.hackergarten.android.app.model.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static android.view.View.*;

public class RegisterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        
        Button registerBtn = (Button)this.findViewById(R.id.button_register);
        registerBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				User user = new User();
				String emailValue = getFieldValue(R.id.editTextEmail);
				if(emailValue.trim().length() == 0){
					Toast.makeText(RegisterActivity.this, "@string/register_failure_email", Toast.LENGTH_SHORT);	
					return;
				}
				user.setEmail(emailValue); 
				user.setName(getFieldValue(R.id.editTextPersonName));
				user.setCompany(getFieldValue(R.id.editTextCompany));
				user.setTwitterUrl(getFieldValue(R.id.editTextTwitter));
				user.setBlogUrl(getFieldValue(R.id.editTextBlog ));
				
				new HackergartenClient().registerUser(user, new AsyncCallback<Void>() {
					
					public void onSuccess(Void result) {
						Toast.makeText(RegisterActivity.this, "@string/register_sucess", Toast.LENGTH_LONG);	
						//Intent registerIntent = new Intent(Intent.ACTION_VIEW,)
						
						//startActivity(registerIntent);
					
					}
					
					public void onFailure(Throwable t) {
						Toast.makeText(RegisterActivity.this, "@string/register_failure", Toast.LENGTH_LONG);	
						
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