package net.hackergarten.android.app.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.hackergarten.android.app.model.Event;
import net.hackergarten.android.app.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Handles the mapping of the REST parameters to http parameters and the
 * deserialization of JSON objects
 * 
 * Note that all Time parameters are expressed as milliseconds UTC time. The
 * conversion to and from local time must be done in the calling code
 * 
 * @author asocaciu
 * 
 */
class HttpMapper {

	static final String ID = "id";
	static final String INITIATOR = "initiator";
	static final String TIME_UTC = "start";
	static final String DESCRIPTION = "description";
	static final String SUBJECT = "subject";
	static final String LONGITUDE = "longitude";
	static final String LATITUDE = "latitude";
	static final String LOCATION = "location";
	static final String TWITTER_URL = "twitterUrl";
	static final String BLOG_URL = "blogUrl";
	static final String COMPANY = "company";
	static final String NAME = "name";
	static final String EMAIL = "email";
	static final String TIME = "start";
	static final String EVENT_ID = "eventId";

	static void map(User user, HttpPost request) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair(EMAIL, user.getEmail()));
		nameValuePairs.add(new BasicNameValuePair(NAME, user.getName()));
		nameValuePairs.add(new BasicNameValuePair(COMPANY, user.getCompany()));
		nameValuePairs.add(new BasicNameValuePair(BLOG_URL, user.getBlogUrl()));
		nameValuePairs.add(new BasicNameValuePair(TWITTER_URL, user
				.getTwitterUrl()));
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	static void map(Event event, HttpPost request) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs
				.add(new BasicNameValuePair(LOCATION, event.getLocation()));
		nameValuePairs.add(new BasicNameValuePair(LATITUDE, Double
				.toString(event.getLatitude())));
		nameValuePairs.add(new BasicNameValuePair(LONGITUDE, Double
				.toString(event.getLongitude())));
		nameValuePairs.add(new BasicNameValuePair(SUBJECT, event.getSubject()));
		nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, event
				.getDescription()));
		nameValuePairs.add(new BasicNameValuePair(TIME_UTC, Long
				.toString(DateUtils.getUTCTimeFromLocalMillis(event
						.getTimeUST().getTime()))));
		nameValuePairs.add(new BasicNameValuePair(INITIATOR, event
				.getInitiator()));
		try {
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

	}

	public static List<Event> mapToEventList(HttpResponse response)
			throws IllegalStateException, IOException, JSONException {
		String content = getResponseContent(response);
		JSONTokener tokenizer = new JSONTokener(content);
		JSONArray eventArray = (JSONArray) tokenizer.nextValue();
		List<Event> result = new ArrayList<Event>();
		for (int i = 0; i < eventArray.length(); i++) {
			JSONObject eventObject = (JSONObject) eventArray.get(i);
			Event event = new Event();
			event.setId(eventObject.getString(ID));
			event.setLocation(eventObject.getString(LOCATION));
			event.setLatitude(eventObject.getDouble(LATITUDE));
			event.setLongitude(eventObject.getDouble(LONGITUDE));
			event.setSubject(eventObject.getString(SUBJECT));
			event.setDescription(eventObject.getString(DESCRIPTION));
			event.setTimeUST(new Date(DateUtils
					.getLocalTimeFromUTCMillis(eventObject.getLong(TIME_UTC))));
			event.setInitiator(eventObject.getString(INITIATOR));
			result.add(event);
		}
		return result;
	}

	public static List<User> mapToUserList(HttpResponse response)
			throws IllegalStateException, IOException, JSONException {
		String content = getResponseContent(response);
		JSONTokener tokenizer = new JSONTokener(content);
		JSONArray userArray = (JSONArray) tokenizer.nextValue();
		List<User> result = new ArrayList<User>();
		for (int i = 0; i < userArray.length(); i++) {
			JSONObject eventObject = (JSONObject) userArray.get(i);
			User user = new User();
			user.setId(eventObject.getString(ID));
			user.setName(eventObject.getString(NAME));
			user.setCompany(eventObject.getString(COMPANY));
			user.setBlogUrl(eventObject.getString(BLOG_URL));
			user.setTwitterUrl(eventObject.getString(TWITTER_URL));
			result.add(user);
		}
		return result;
	}

	private static String getResponseContent(HttpResponse response)
			throws IllegalStateException, IOException {
		StringBuilder content = new StringBuilder();
		HttpEntity entity = response.getEntity();
		InputStream inputStream = entity.getContent();
		String encoding = "ISO-8859-1";
		if (entity.getContentEncoding() != null) {
			encoding = entity.getContentEncoding().getValue();
		}
		byte[] buf = new byte[1024];
		int read = 0;
		while ((read = inputStream.read(buf)) >= 0) {
			content.append(new String(buf, 0, read, encoding));
		}
		inputStream.close();
		entity.consumeContent();
		return content.toString();
	}

}
