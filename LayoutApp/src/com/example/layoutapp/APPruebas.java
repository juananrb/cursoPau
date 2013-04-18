package com.example.layoutapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class APPruebas extends Activity {
	ListView mLVLista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ap_pruebas);
		
		// Trabajo con el ListView
//		setContentView(R.layout.custom_listview);
//		mLVLista = (ListView)findViewById(R.id.clv_lv_lista);
//		MyAdapter ad = new MyAdapter(this);
//		mLVLista.setAdapter(ad);
		
		findViewById(R.id.ap_bt_segundo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.pruebasvarias.segundaactividad");
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.appruebas, menu);
		return true;
	}

}
