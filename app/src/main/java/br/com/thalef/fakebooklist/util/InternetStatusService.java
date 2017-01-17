package br.com.thalef.fakebooklist.util;

/**
 * Created by thiagocarvalho on 15/01/17.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetStatusService {


    public boolean isOnline(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        context = null;

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isWifiOn(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        context = null;

        return activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    }


}
