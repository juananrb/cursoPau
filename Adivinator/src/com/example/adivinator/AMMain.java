package com.example.adivinator;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AMMain extends Activity {
	private static final String CT_TAG = "AMMain";
	private static final String CT_BUNDLE_INCOGNITA = "incognita";
	private static final String CT_BUNDLE_INTENTOS = "intentos";
	private static final String CT_BUNDLE_MAXIMO = "maximo";
	private static final int CT_DLG_EDITMAX = 1;
	
	EditText mValor;
	TextView mMensaje1;
	TextView mMensaje2;
//	Button mPrueba;
	
	EditText mETDlgMaximo;
	
	int mIncognita;
	int mIntentos;
	int mMaximo;

//	class PruebaListener implements View.OnClickListener{
//		@Override
//		public void onClick(View v) {
//			computaIntento();
//		}
//	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.am_main);
        
        mValor = (EditText) findViewById(R.id.am_et_valor);
        mMensaje1 = (TextView) findViewById(R.id.am_tv_mensaje1);
        mMensaje2 = (TextView) findViewById(R.id.am_tv_mensaje2);
        
        // Mapeo del botón paso a paso
//        mPrueba = (Button) findViewById(R.id.am_bt_prueba);
//        
//        PruebaListener miListener = new PruebaListener();
//        mPrueba.setOnClickListener(miListener);
        
        // Mapeo rápido del botón
        findViewById(R.id.am_bt_prueba).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				computaIntento();
			}
		});
        
        if (savedInstanceState == null) {
        	Log.d(CT_TAG, "onCreate sense estat");
        	restaurarConfiguracion();
        	inicializaIncognita();
        }
        else {
        	Log.d(CT_TAG, "onCreate amb estat per restaurar");
        	restaurarEstat(savedInstanceState);
        }
        
        refrescaIntentos();
    }
    
    private void restaurarConfiguracion() {
    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    	String max = pref.getString(getResources().getString(R.string.pref_valor_maximo), "100");
    	// pref_valor_maximo
    	try {
    		mMaximo = Integer.valueOf(max);
    	} catch (Exception e) {
    		mMaximo = 100;
    	}
    }

	private void restaurarEstat(Bundle savedInstanceState) {
		mIncognita = savedInstanceState.getInt(CT_BUNDLE_INCOGNITA);
		mIntentos = savedInstanceState.getInt(CT_BUNDLE_INTENTOS);
		mMaximo = savedInstanceState.getInt(CT_BUNDLE_MAXIMO);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
    	Log.d(CT_TAG, "onSaveInstanceState invocat");
		super.onSaveInstanceState(outState);
		
		outState.putInt(CT_BUNDLE_INCOGNITA, mIncognita);
		outState.putInt(CT_BUNDLE_INTENTOS, mIntentos);
		outState.putInt(CT_BUNDLE_MAXIMO, mMaximo);
	}

	@Override
	protected void onDestroy() {
		Log.d(CT_TAG, "onDestroy invocat");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d(CT_TAG, "onPause invocat");
		super.onPause();
	}

	@Override
	protected void onResume() {
    	Log.d(CT_TAG, "onResume invocat");
		super.onResume();
	}

	@Override
	protected void onStart() {
    	Log.d(CT_TAG, "onStart invocat");
		super.onStart();
	}

	@Override
	protected void onStop() {
    	Log.d(CT_TAG, "onStop invocat");
		super.onStop();
	}

	private void inicializaIncognita() {
    	mIntentos = 0;
    	Random gen = new Random();
    	mIncognita = gen.nextInt(mMaximo) + 1;
    	Log.d(CT_TAG, "La incógnita es " + mIncognita);
    }
    
    private void computaIntento() {
    	mIntentos++;
    	int valAct;
    	try {
    		valAct = Integer.valueOf(mValor.getText().toString());
    	} catch(Exception e) {
    		mMensaje1.setText(R.string.am_val_invalido);
    		return;
    	}
    	// Final
    	if (valAct == mIncognita) {
    		mMensaje1.setText(R.string.am_val_acertado);
    		refrescaIntentos();
    		findViewById(R.id.am_bt_prueba).setEnabled(false);
    		creaNotificacion();
    		return;
    	}
    	if (valAct < mIncognita) {
    		mMensaje1.setText(getResources().getString(R.string.am_mayor, valAct));
    		refrescaIntentos();
    	}
    	else {
    		mMensaje1.setText(getResources().getString(R.string.am_menor, valAct));
    		refrescaIntentos();
    	}
    	mValor.setText("");
    }
    
    private void creaNotificacion() {
    	Notification not = new Notification(R.drawable.ic_launcher, "Incógnita acertada", System.currentTimeMillis());
    	Intent i = new Intent(this, AMMain.class);
    	PendingIntent pi = PendingIntent.getActivity(this, 0, i, Notification.FLAG_AUTO_CANCEL);
    	not.setLatestEventInfo(this, "Incógnita", "Se acertó la incógnita", pi);
    	
    	NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    	nm.notify(1, not);
    }
    
    private void refrescaIntentos() {
    	String intentos = getResources().getQuantityString(R.plurals.am_intentos, mIntentos, mIntentos);
    	mMensaje2.setText(intentos);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.amm_options_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.am_mn_reset: Toast.makeText(this, "Se procedería a resetear", Toast.LENGTH_SHORT).show(); break;
		case R.id.am_mn_editmax: showDialog(CT_DLG_EDITMAX); break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case CT_DLG_EDITMAX:
			AlertDialog.Builder bldr = new AlertDialog.Builder(this);
			bldr.setTitle("Máximo").setMessage("Introduzca el máximo)");
			View v = getLayoutInflater().inflate(R.layout.am_dlg_maximo, null);
			mETDlgMaximo = (EditText)v.findViewById(R.id.am_et_dlg_max);
			bldr.setView(v);
			bldr.setPositiveButton("ok", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					resetMaximo();
				}
			});
			bldr.setNegativeButton("Cancel", null);
			return bldr.create();
		}
		return super.onCreateDialog(id);
	}

	protected void resetMaximo() {
		try {
			mMaximo = Integer.parseInt(mETDlgMaximo.getText().toString());
			mIntentos = 0;
			inicializaIncognita();
			refrescaIntentos();
			mMensaje1.setText("");
			
			// Grabamos el valor también en las preferencias
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
			SharedPreferences.Editor edit = pref.edit();
			edit.putString(getResources().getString(R.string.pref_valor_maximo), Integer.toString(mMaximo));
			edit.commit();
			
			// Acceso a otro fichero de preferencias
			SharedPreferences misPreferencias = getSharedPreferences("misprefs", Context.MODE_PRIVATE);
			misPreferencias.getInt("clave", 1500);
		} catch (Exception e) {
			return;
		}
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch(id) {
		case CT_DLG_EDITMAX:
			mETDlgMaximo.setText(Integer.toString(mMaximo));
		}
		super.onPrepareDialog(id, dialog, args);
	}
    
    
}
