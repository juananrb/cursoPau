package com.ejemplo.proyectoaplicacion;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final static int DIALOG_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_dialog);
    }
    
    public void mostrarDialogo(View view){
    	showDialog(1);
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog detalles = null;
    	switch(id){
    		case DIALOG_LOGIN:
    			LayoutInflater inflater = LayoutInflater.from(this);
    			View dialogview = inflater.inflate(R.layout.dialog_layout, 
    					                           null);
    			AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
    			dialogbuilder.setTitle("Login de usuario");
    			dialogbuilder.setView(dialogview);
    			detalles = dialogbuilder.create();
    			break;
    	}
    	return detalles;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
			case DIALOG_LOGIN:
				final AlertDialog alertDialog = (AlertDialog) dialog;
				final EditText usuario = 
						(EditText) alertDialog.findViewById(R.id.txt_name);
				final EditText password = 
						(EditText) alertDialog.findViewById(R.id.password);
				Button btnLogin = 
						(Button) alertDialog.findViewById(R.id.btn_login);
				Button btnCancel = 
						(Button) alertDialog.findViewById(R.id.btn_cancel);
				
				btnLogin.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						alertDialog.dismiss();
						Toast.makeText(getApplicationContext(), 
								       "Usuario: " + usuario.getText() + 
								       "\nPassword: " + password.getText(), 
								       Toast.LENGTH_SHORT).show();
					}
				});
				btnCancel.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View v) {
						alertDialog.dismiss();
					}
				});
				break;
			default:
				break;
		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}








