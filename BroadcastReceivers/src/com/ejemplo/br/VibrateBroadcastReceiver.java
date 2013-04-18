package com.ejemplo.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

public class VibrateBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_VIBRATE="com.example.VIBRATE";
    private static final String VIBRATE_DURATION="durationInMS";
    private static String TAG = "VibrateBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_VIBRATE)) {
            int duration = intent.getIntExtra(VIBRATE_DURATION, 100);
            Vibrator v = (Vibrator) 
                  context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(duration);
            Log.v(TAG, "---------- " + intent.getAction() + " " + duration + "ms");
        }
    }
}