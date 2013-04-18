package com.example.multith;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ServicioRegistros extends Service {
	public static final String CT_TAG = "ServicioRegistros";
	public static final String CT_EXTRA_VALORES = "valores";
	
	public static final String CT_BROADCAST_ACCION = "com.example.multith.record_processed";
	public static final String CT_BROADCAST_PROCESADOS = "proc";
	public static final String CT_BROADCAST_PENDIENTES = "pend";
	
	
	int mRegistrosProcesados = 0;
	int mRegistrosPendientes = 0;
	
	Procesador mProc;

	class Procesador extends AsyncTask<Integer, Integer, Integer> {
		@Override
		protected Integer doInBackground(Integer... params) {
			while (mRegistrosPendientes > 0 && !isCancelled()) {
				// Processo el registre
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				synchronized (ServicioRegistros.this) {
					mRegistrosPendientes--;
				}

				publishProgress(1);
			}
			
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			mRegistrosProcesados++;
			Log.i(CT_TAG, "Registro procesado. Quedan " + mRegistrosPendientes + " (Thread: " + 
					Thread.currentThread().getName() + ")");
			
			Intent i = new Intent(CT_BROADCAST_ACCION);
			i.putExtra(CT_BROADCAST_PROCESADOS, mRegistrosProcesados);
			i.putExtra(CT_BROADCAST_PENDIENTES, mRegistrosPendientes);
			sendBroadcast(i);
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (mRegistrosPendientes > 0) {
				iniciarTareaAsincrona();
			} else {
				stopSelf();
				mProc = null;
				stopForeground(true);
			}
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(CT_TAG, "onStartCommand invocado con startId=" + startId + " desde el thread " + 
					Thread.currentThread().getName());
		
		synchronized (this) {
			mRegistrosPendientes += intent.getIntExtra(CT_EXTRA_VALORES, 0);
		}
		
		if (mProc == null) {
			iniciarTareaAsincrona();
			
			// Pasamos el servicio a foreground
			Notification not = new Notification(
					android.R.drawable.ic_media_play, 
					"Iniciado servicio de registros", 
					System.currentTimeMillis());
			
			Intent i = new Intent(this, AMT_MultiThread.class);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			not.setLatestEventInfo(this, "Servicio registro", "Se están procesando registros", pi);
			
			startForeground(1, not);
		}
		return START_STICKY | START_REDELIVER_INTENT;
	}

	private void iniciarTareaAsincrona() {
		mProc = new Procesador();
		mProc.execute(0);
		Log.i(CT_TAG, "Inicio la tarea del servicio");
	}

	@Override
	public void onDestroy() {
		Log.i(CT_TAG, "onDestroy invocado desde el thread " + Thread.currentThread().getName() + 
				" habiendo procesado " + mRegistrosProcesados + " registros");
		super.onDestroy();
		if (mProc != null) 
			mProc.cancel(true);
	}

//	protected void acabarTrabajo() {
//		// acabar, limpiar,...
//		stopSelf(77);
//	}
	//////////////////////////////////////////
	public class RegistrosBinder extends Binder {
		public boolean isRunning() {
			return mProc != null;
		}
		
		public int getPendientes() {
			return mRegistrosPendientes;
		}

		public int getProcesados() {
			return mRegistrosProcesados;
		}
	}
	
	RegistrosBinder myBinder = new RegistrosBinder();
	
	@Override
	public IBinder onBind(Intent arg0) {
		Log.i(CT_TAG, "Nuevo cliente conectado");
		return myBinder;
	}

}
