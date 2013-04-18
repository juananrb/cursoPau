package com.example.trabajandoconthreads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TareaThreadActivity extends Activity implements OnClickListener {
	ProgressDialog progreso;
	Controlador handler = new Controlador();
	int maximo = 100;
	int retardo = 100;
	TextView texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarea_thread);
		Button boton = (Button) findViewById(R.id.button1);
		boton.setOnClickListener(this);
		texto = (TextView) findViewById(R.id.textView1);
		
		String nombre = Thread.currentThread().getName();
		int prioridad = Thread.currentThread().getPriority();
		Thread.currentThread().setName("miercoles");
		Log.d("TareaThreadActivity", "Nombre: " + nombre + 
				", Prioridad: " + prioridad + 
				", Ahora se llama " + Thread.currentThread().getName());	

		
	}
	
	public void invocarActivity(View view){
		Intent intento = new Intent(this, AutocompletadoActivity.class);
		startActivity(intento);
	}
	
	
	
	
	@Override
	public void onClick(View view){
		showDialog(1);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		progreso = new ProgressDialog(this);
		progreso.setProgressStyle(1);
		progreso.setMessage("Ejemplo");
		return progreso;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		progreso = (ProgressDialog) dialog;
		progreso.setProgressStyle(1);
		progreso.setMax(maximo);
		progreso.setProgress(0);
		progreso.setMessage("Ejecutando hilo en background...");
		Hilo thread = new Hilo();
		thread.start();
	}
	
	class Controlador extends Handler{
		@Override
		public void handleMessage(Message msg) {
			int total = msg.getData().getInt("total");
			progreso.setProgress(total);
			texto.setText("Total: " + total + " Maximo: " + maximo);
			if(total == maximo){
				dismissDialog(1);
			}
		}
	}
	
	class Hilo extends Thread{
		@Override
		public void run() {
			for (int i = 0; i <= maximo; i++) {
				try {
					Thread.sleep(retardo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				Bundle b = new Bundle();
				b.putInt("total", i);
				msg.setData(b);
				handler.sendMessage(msg);
			}
			super.run();
		}
		
	}
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarea_thread, menu);
		return true;
	}


}
