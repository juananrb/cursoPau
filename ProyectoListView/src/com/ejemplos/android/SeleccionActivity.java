package com.ejemplos.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class SeleccionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccion);
		TextView campo = (TextView) findViewById(R.id.seleccion);
		Intent intento = getIntent();
		String producto = intento.getStringExtra("producto");
		campo.setText("Se ha seleccionado: "+ producto);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seleccion, menu);
		return true;
	}

}
