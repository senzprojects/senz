package com.score.senz.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by root on 12/16/15.
 */
public class InstallationReceiver extends BroadcastReceiver {
    private static final String TAG = InstallationReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if("android.intent.action.PACKAGE_REMOVED".equals(action)){
            // here i wrote the code of  delete device id in server side

            Log.e(TAG, "DDDDDDDDDDDDDDDDDDDDDDDDD Uninstall receiver Recceived DDDDDDDDDDDDDDDDDDDDDDDDD");

        }
    }
}
