package com.ejemplos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class TareaAsincronaActivity extends Activity {
	private String url = "http://t1.gstatic.com/images?q=tbn:ANd9GcRodub9ePrHg8ddbHjhpnrXK3qIWJ86jgNlbqM7UYg8J4vsHwCo";
	private DescargarImagenAsync tareaDesc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tarea_asincrona);
	}
	
	public void hacerTarea(View view){
		if(tareaDesc != null){
			AsyncTask.Status descEstado = tareaDesc.getStatus();
			Log.d("hacerTarea", "La tarea esta: " + descEstado);
			if(descEstado == AsyncTask.Status.FINISHED){
				Log.d("hacerTarea", "Esta acabada");
				return;
			}
		}
		tareaDesc = new DescargarImagenAsync(this);
		tareaDesc.execute(url);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tarea_asincrona, menu);
		return true;
	}

}
