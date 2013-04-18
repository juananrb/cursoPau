package com.example.adivinator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AEMenu extends Activity {
	private static final String CT_TAG = "AEMenu"; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ae_menu);
        
        findViewById(R.id.ae_bt_adivinator).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				invocaAdivinator();
			}
		});
        
        findViewById(R.id.ae_bt_settings).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				invocaSettings();
			}
		});
        
        if (savedInstanceState == null) {
        	Log.d(CT_TAG, "onCreate sense estat");
        }
        else {
        	Log.d(CT_TAG, "onCreate amb estat per restaurar");
        }
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
    	Log.d(CT_TAG, "onSaveInstanceState invocat");
		super.onSaveInstanceState(outState);
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


	private void invocaAdivinator(){
		Intent i = new Intent(this, AMMain.class);
		startActivity(i);
	}
	
	private void invocaSettings(){
		Intent i = new Intent(this, APPreferencias.class);
		startActivity(i);
	}
}
