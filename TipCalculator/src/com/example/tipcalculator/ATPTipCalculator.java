package com.example.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class ATPTipCalculator extends Activity {
	EditText mETAmount;
	TextView mTVTip;
	TextView mTVTotal;
	RadioGroup mRGGroup;
	Spinner mSPTips;
	
	View.OnClickListener mListenerRadio = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			calculaTips(v);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atptip_tip_calculator);
		
		// Mapeamos variables miembro
		mETAmount = (EditText)findViewById(R.id.atp_et_amount);
		mTVTip = (TextView)findViewById(R.id.atp_tv_tip);
		mTVTotal = (TextView)findViewById(R.id.atp_tv_total);
		mRGGroup = (RadioGroup)findViewById(R.id.atp_rg_group);
		mSPTips = (Spinner)findViewById(R.id.atp_sp_tips);
		
		preparaRadioButton(R.id.atp_rb_p10, .10);
		preparaRadioButton(R.id.atp_rb_p15, .15);
		preparaRadioButton(R.id.atp_rb_p18, .18);
		preparaRadioButton(R.id.atp_rb_p20, .20);
		
		// Mapeamos eventos
		mETAmount.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				calculaTips(null);
			}
			
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override public void afterTextChanged(Editable s) {}
		});
		
		// Carga de datos "a mano"
//		ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
//		spAdapter.add("10%");
//		spAdapter.add("15%");
//		spAdapter.add("18%");
//		spAdapter.add("20%");
		
		// Carga de datos desde array en recursos
		ArrayAdapter<String> spAdapter = 
				new ArrayAdapter<String>(
						this, 
						android.R.layout.simple_spinner_item, 
						getResources().getStringArray(R.array.atp_array_strings_porcentajes));
		mSPTips.setAdapter(spAdapter);
		
		final int porcentajes[] = getResources().getIntArray(R.array.atp_array_enteros_porcentajes);
		
		mSPTips.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) {
				Log.i("ATPTipCalculator", "Porcentaje a aplicar: " + porcentajes[pos]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
	
	private void preparaRadioButton(int id, double pct) {
		View v = findViewById(id);
		v.setTag(Double.valueOf(pct));
		v.setOnClickListener(mListenerRadio);
	}
	
	private void calculaTips(View v) {
		if (v == null) {
			int id = mRGGroup.getCheckedRadioButtonId();
			v = findViewById(id);
		}
		double pct = (Double)v.getTag();
		double amount = 0.0;
		try {
			amount = Double.parseDouble(mETAmount.getText().toString());
		} catch (Exception e) {}
		mTVTip.setText(getResources().getString(R.string.atp_valor, pct * amount));
		mTVTotal.setText(getResources().getString(R.string.atp_valor, amount + pct * amount));
	}
}
