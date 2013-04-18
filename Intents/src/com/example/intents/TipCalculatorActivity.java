package com.example.intents;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity {
	private EditText billAmountEditText;
	private RadioGroup rg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tips);

		billAmountEditText = (EditText) findViewById(R.id.billAmount);
		billAmountEditText.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				updateTextViews();
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
		});

		rg = (RadioGroup) findViewById(R.id.tipRadioGroup);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup grp, int chkdId) {
				updateTextViews();
				// set result to return to calling component
				TextView tip = (TextView) findViewById(R.id.tipAmount);
				Intent intent = new Intent();
				intent.putExtra("TipAmount", tip.getText());
				setResult(Activity.RESULT_OK, intent);
			}
		});

		// initialize the bill amount and tip percent using passed in
		// extras from the intent that activated this activity
		Intent intent = getIntent();
		if (intent.hasExtra("BillAmount") && intent.hasExtra("TipPercent")) {
			double billAmount = intent.getDoubleExtra("BillAmount", 100);
			int tipPercent = intent.getIntExtra("TipPercent", 18);

			billAmountEditText.setText("" + billAmount);

			switch (tipPercent) {
			case 10:
				RadioButton rb = (RadioButton) findViewById(R.id.ten);
				rb.setChecked(true);
				break;
			case 15:
				rb = (RadioButton) findViewById(R.id.fifteen);
				rb.setChecked(true);
				break;
			case 18:
				rb = (RadioButton) findViewById(R.id.eighteen);
				rb.setChecked(true);
				break;
			default:
				rb = (RadioButton) findViewById(R.id.twenty);
				rb.setChecked(true);
				break;
			}
		}
	}

	private void updateTextViews() {
		Resources res = getResources();
		TextView tipAmountTextView = (TextView) findViewById(R.id.tipAmount);
		TextView totalBillTextView = (TextView) findViewById(R.id.totalBill);
		RadioButton rb = (RadioButton) findViewById(rg
				.getCheckedRadioButtonId());

		try {
			float billAmount = Float.parseFloat(billAmountEditText.getText().toString());
			// Use split function to remove % sign from String
			float tipPercent = Float.parseFloat(rb.getText().toString()
					.split("%")[0]);
			tipPercent = tipPercent / 100;

			float tip = billAmount * tipPercent;
			float total = billAmount + tip;

			String tipLabel = res.getString(R.string.tip_amount_label2);
			String totalLabel = res.getString(R.string.total_bill_label2);
			tipAmountTextView.setText(String.format(tipLabel, tip));
			totalBillTextView.setText(String.format(totalLabel, total));
			
		} catch (NumberFormatException e) {
			tipAmountTextView.setText(R.string.tip_amount_label);
			totalBillTextView.setText(R.string.total_bill_label);
			System.err.println(e);
		}
	}
}