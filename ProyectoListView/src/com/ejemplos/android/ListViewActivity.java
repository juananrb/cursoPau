package com.ejemplos.android;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

public class ListViewActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_list_view);
		
		String[] productos = 
				getResources().getStringArray(R.array.adobe_products);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_view,
				                                R.id.seleccion, productos));
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, 
					                int position, long id) {
				// TODO Auto-generated method stub
				String producto = ((TextView)view).getText().toString();
				Intent intento = new Intent(getApplicationContext(), 
						                    SeleccionActivity.class);
				intento.putExtra("producto", producto);
				startActivity(intento);
			}
			
		});	
	}



}
