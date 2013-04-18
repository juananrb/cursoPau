package com.ejemplo.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TelefonoBroadcastReceiver extends BroadcastReceiver {
    private static String TAG = "TelefonoBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.v(TAG, "Llamada saliente: " + phoneNumber);
        }
    }
}