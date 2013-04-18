package com.ejemplos.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class NotificacionActivity extends Activity {
	private boolean continuar = true;
	int retraso = 100;
	int contador = 0;
	NotificationManager manager;
	Notification notificacion;
	String ntfText = "";
	Context context;
	Intent ntfIntent;
	PendingIntent pendingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificacion);
		
		manager = (NotificationManager) 
				              getSystemService(Context.NOTIFICATION_SERVICE);
		int icon = R.drawable.ic_launcher;
		String texto = "ejecutandose";
		long cuando = System.currentTimeMillis();
		
		notificacion = new Notification(icon, texto, cuando);
		
		context = getApplicationContext();
		ntfIntent = new Intent(this, NotificacionActivity.class);
		pendingIntent = PendingIntent.getActivity(this, 0, ntfIntent, 0);
	
		Contar contar = new Contar();
		Thread hilo = new Thread(contar);
		hilo.start();
	}
	
	class Contar implements Runnable{
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			while(continuar){
				try {
					Thread.sleep(retraso);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				contador++;
				ntfText = "contador: " + contador;
				notificacion.setLatestEventInfo(context, "Hilo trabajando", 
						                        ntfText, pendingIntent);
				int ref = 1;
				manager.notify(ref, notificacion);
				manager.notify(2, notificacion);
				if(contador == 200) continuar = false;
			}//fin de while
		}//run
	}//Contar

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notificacion, menu);
		return true;
	}

}
