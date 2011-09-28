package net.hackergarten.android.app.client;

public interface AsyncCallback<T> {

	void onSuccess(T result);

	void onFailure(Throwable t);

}
