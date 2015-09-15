package org.ry8.CeaFrame.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ceare on 2015/2/27.
 */
public class NetHelper {    //是否联网网络

    public static boolean IsHaveInternet(final Context context) {
        try {
            ConnectivityManager manger = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manger.getActiveNetworkInfo();
            return (info != null && info.isAvailable());
        } catch (Exception e) {
            return false;
        }
    }
}