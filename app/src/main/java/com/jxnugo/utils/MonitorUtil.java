package com.jxnugo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zpauly on 16-5-19.
 */
public class MonitorUtil {
    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            boolean isConnected = activeNetwork.isConnectedOrConnecting();
            return isConnected;
        } else {
            return false;
        }
    }
}
