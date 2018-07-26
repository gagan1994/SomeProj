package com.example.gagan.proj1.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.gagan.proj1.dbhelper.DbHelper;

/**
 * Created by Gagan on 4/19/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        int status = NetworkUtil.getConnectivityStatusString(context);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {

            } else {

            }
            DbHelper.getDbHepler().onlineStatusChanged(context);
        }
    }
}
