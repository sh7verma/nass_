package app.com.esenatenigeria.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.multidex.MultiDex;

/**
 * Created by dev on 17/11/17.
 */

public class eSenateApplication extends android.app.Application {

    private static eSenateApplication instance;
    public static final String TAG = eSenateApplication.class
            .getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MultiDex.install(this);

    }

    public static eSenateApplication getInstance() {
        return instance;
    }

    public static boolean hasNetwork() {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
