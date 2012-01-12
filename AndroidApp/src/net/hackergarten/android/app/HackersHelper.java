package net.hackergarten.android.app;

import android.app.Activity;
import android.widget.Toast;

/**
 * This is the hackergartens little mobile helper class.
 *
 * @author Markus Schlichting <markus.schlichting@canoo.com>
 */
public class HackersHelper {
    private HackersHelper() {
    }

    static void showMessage(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
