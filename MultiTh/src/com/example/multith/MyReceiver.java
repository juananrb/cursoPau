package com.example.multith;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	protected static final String CT_TAG = "MyReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(CT_TAG, "Actualización del servicio (" + 
				intent.getIntExtra(ServicioRegistros.CT_BROADCAST_PENDIENTES, 0) + "/" + 
				intent.getIntExtra(ServicioRegistros.CT_BROADCAST_PROCESADOS, 0) + ")");
	}

}
