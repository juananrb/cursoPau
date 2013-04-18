package com.example.trabajandoconthreads;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutocompletadoActivity extends Activity {
	private AutoCompleteTextView actv = null;
	private ArrayAdapter<String> adaptador = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autocompletado);
		actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		adaptador = new ArrayAdapter<String>(this, 
				android.R.layout.simple_expandable_list_item_1,
				new String[] {"Barcelona", "Lleida", "Girona", "Tarragona"});
		actv.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.autocompletado, menu);
		return true;
	}

}
