package net.hackergarten.android.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationSettings {

	private static final String SETTINGS_KEY = "net.hackergarten.android.app.preferences";
	final Context context;

	public ApplicationSettings(Context context) {
		this.context = context;
	}

	public void registerUser(String userName) {
		SharedPreferences pref = context.getSharedPreferences(
				ApplicationSettings.SETTINGS_KEY, Context.MODE_PRIVATE);
		Editor prefEditor = pref.edit();
		prefEditor.putString("email_address", userName);
		prefEditor.commit();
	}

	public boolean isUserRegistered() {
		SharedPreferences pref = context.getSharedPreferences(
				ApplicationSettings.SETTINGS_KEY, Context.MODE_PRIVATE);
		String email = pref.getString("email_address", null);
		return email != null;
	}

	public String getRegisteredUser() {
		if (!isUserRegistered()) {
			throw new IllegalStateException("User is not registered");
		}
		SharedPreferences pref = context.getSharedPreferences(
				ApplicationSettings.SETTINGS_KEY, Context.MODE_PRIVATE);
		return pref.getString("email_address", null);
	}

	public void logOut() {
		
		SharedPreferences pref = context.getSharedPreferences(
				ApplicationSettings.SETTINGS_KEY, Context.MODE_PRIVATE);
		Editor prefEditor = pref.edit();
		prefEditor.remove("email_address");
		prefEditor.apply();		
	}
}
