package com.ejemplos.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CalculadoraActivity extends Activity {
	private EditText billAmountText;
	private RadioGroup rg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		billAmountText = (EditText) findViewById(R.id.billAmount);
		billAmountText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, 
					                  int before, int count) {
				updateTextView();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, 
					                      int count, int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});//Fin del listener
		
		rg = (RadioGroup) findViewById(R.id.tipRadioGroup);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				updateTextView();				
			}
		});

	}//fin de onCreate
	
	private void updateTextView(){
		Resources res = getResources();
		TextView tipAmount = (TextView) findViewById(R.id.tipAmount);
		TextView totalBill = (TextView) findViewById(R.id.totalBill);
		RadioButton rb = (RadioButton) 
				            findViewById(rg.getCheckedRadioButtonId());
		
		try {
			float billAmount = Float.parseFloat(billAmountText.getText().toString());
			float porcentaje = 
					Float.parseFloat(rb.getText().toString().split("%")[0]);
			porcentaje = porcentaje / 100;
			float tip = billAmount * porcentaje;
			float total = billAmount + tip;
			
			tipAmount.setText(
					String.format(res.getString(R.string.tip_amount_label2), tip));
			totalBill.setText(res.getString(R.string.total_bill_label2, total));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cakluladora, menu);
		return true;
	}

}
