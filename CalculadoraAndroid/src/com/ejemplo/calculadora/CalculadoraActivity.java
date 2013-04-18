package com.ejemplo.calculadora;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.*;

public class CalculadoraActivity extends Activity {
	private Operacion operacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculadora);
		operacion = new Operacion(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calculadora, menu);
		return true;
	}
	
	public void operacionSeleccionada(View view){
		EditText c1 = (EditText) this.findViewById(R.id.etc1);
		EditText c2 = (EditText) this.findViewById(R.id.etc2);
		EditText op = (EditText) this.findViewById(R.id.etop);
		int cant1 = Integer.parseInt(c1.getText().toString());
		int cant2 = Integer.parseInt(c2.getText().toString());
		String strOp = op.getText().toString();
		Operacion op1 = new Operacion(cant1, cant2, strOp);
		int total = op1.calcular();
		EditText intTotal = (EditText) this.findViewById(R.id.etotal);
		intTotal.setText(Integer.toString(total));
	}

}
