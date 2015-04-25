package raulvilar.gdgaracaju;

import android.app.Application;

import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;

/**
 * Created by raul on 19/10/14.
 */
public class CastApplication extends Application {

    private static String APPLICATION_ID;
    public static final double VOLUME_INCREMENT = 0.05;

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION_ID = getString(R.string.app_id);

        VideoCastManager.
                initialize(this, APPLICATION_ID, null, null).
                setVolumeStep(VOLUME_INCREMENT).
                enableFeatures(VideoCastManager.FEATURE_NOTIFICATION |
                        VideoCastManager.FEATURE_LOCKSCREEN |
                        VideoCastManager.FEATURE_WIFI_RECONNECT |
                        VideoCastManager.FEATURE_CAPTIONS_PREFERENCE |
                        VideoCastManager.FEATURE_DEBUGGING);
    }
}