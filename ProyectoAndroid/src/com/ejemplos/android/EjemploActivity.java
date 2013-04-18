package com.ejemplos.android;

import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.view.*;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class EjemploActivity extends Activity implements OnClickListener {
	private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejemplo);
        
        textView = (TextView) findViewById(R.id.textView1);
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(this);
        Locale locale = Locale.getDefault();
        Locale locale1 = getResources().getConfiguration().locale;
        Log.d("EjemploActivity", "El locale es: ".concat(locale.toString()));
        Log.d("EjemploActivity", "El locale1 es: ".concat(locale1.toString()));
    }
    
    @Override
	public void onClick(View view) {
		if(view.getId()  ==  R.id.button1){
			//textView.setText(getString(R.string.cambiar_texto));
			textView.setText(R.string.cambiar_texto);
		}
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ejemplo, menu);
        return true;
    }


	@Override
	protected void onDestroy() {
		Log.d("EjemploActivity", "Invocando metodo onDestroy");
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		Log.d("EjemploActivity", "Invocando metodo onPause");
		super.onPause();
	}


	@Override
	protected void onRestart() {
		Log.d("EjemploActivity", "Invocando metodo onRestart");
		super.onRestart();
	}


	@Override
	protected void onResume() {
		Log.d("EjemploActivity", "Invocando metodo onResume");
		super.onResume();
	}


	@Override
	protected void onStart() {
		Log.d("EjemploActivity", "Invocando metodo onStart");
		super.onStart();
	}


	@Override
	protected void onStop() {
		Log.d("EjemploActivity", "Invocando metodo onStop");
		super.onStop();
	}



    
    
    
}
