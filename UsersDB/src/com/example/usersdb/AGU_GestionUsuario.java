package com.example.usersdb;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usersdb.usuarios.BDUsuarios;
import com.example.usersdb.usuarios.Usuario;
import com.example.usersdb.usuarios.UsuarioDAO;

public class AGU_GestionUsuario extends Activity {
	public static final String CT_EXTRA_ID = "claveUs";
	protected static final int CT_DIALOGO_FECHA = 1;
	protected static final String CT_SAVE_FECHA = "bundle_fecha";
	
	TextView mId;
	EditText mNombre;
	EditText mApellidos;
	TextView mFechaNac;
	
	Usuario mUsuario;
	UsuarioDAO mUsuarioDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agu_gestion_usurio);
		
		// Mapeo campos
		mId = (TextView)findViewById(R.id.agu_tv_id);
		mNombre = (EditText)findViewById(R.id.agu_et_nombre);
		mApellidos = (EditText)findViewById(R.id.agu_et_apellidos);
		mFechaNac = (TextView)findViewById(R.id.agu_tv_fecha);
		
		findViewById(R.id.agu_bt_cambiar).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogoFecha();
			}
		});
		
		mUsuarioDAO = new UsuarioDAO(new BDUsuarios(this).getWritableDatabase());
		
		// Vemos si tenemos que insertar o actualizar
		Intent i = getIntent();
		long id = i.getLongExtra(CT_EXTRA_ID, -1l);
		if (id == -1l) {
			// Insertar
			mUsuario = new Usuario();
			mUsuario.setId(-1l);
		} else {
			// Actualitzar
			mUsuario = mUsuarioDAO.getById(id);
			if (mUsuario == null) {
				Toast.makeText(this, "El usuario con id " + id + " no existe, se realizará una inserción", Toast.LENGTH_LONG).show();
				mUsuario = new Usuario();
				mUsuario.setId(-1l);
			}
		}
		
		if (savedInstanceState != null) {
			mUsuario.setFechaNacimiento(new Date(savedInstanceState.getLong(CT_SAVE_FECHA)));
		}
		
		muestraUsuario();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (!isFinishing()) 
			return;
		
		mUsuario.setNombre(mNombre.getText().toString());
		mUsuario.setApellidos(mApellidos.getText().toString());
		if (mUsuario.getId() == -1) {
			mUsuarioDAO.insertar(mUsuario);
		}
		else {
			mUsuarioDAO.update(mUsuario);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(CT_SAVE_FECHA, mUsuario.getFechaNacimiento().getTime());
	}

	private void muestraUsuario() {
		mId.setText(mUsuario.getId() == -1l ? "<<No establecido>>" : Long.toString(mUsuario.getId()));
		mNombre.setText(mUsuario.getNombre());
		mApellidos.setText(mUsuario.getApellidos());
		mFechaNac.setText(mUsuario.getFechaNacimientoString());
	}

	protected void dialogoFecha() {
		showDialog(CT_DIALOGO_FECHA);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case CT_DIALOGO_FECHA:
			DatePickerDialog dlg = new DatePickerDialog(this, new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					Calendar c = Calendar.getInstance();
					c.set(year, monthOfYear, dayOfMonth);
					mUsuario.setFechaNacimiento(c.getTime());
					mFechaNac.setText(mUsuario.getFechaNacimientoString());
				}
			}, 1970, 0, 1);
			return dlg;
		}
		return super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch(id) {
		case CT_DIALOGO_FECHA:
			if (mUsuario.getFechaNacimiento() != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(mUsuario.getFechaNacimiento());
				((DatePickerDialog)dialog).updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
			} else 
				((DatePickerDialog)dialog).updateDate(1980, 0, 1);
		}
		super.onPrepareDialog(id, dialog);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.agu__gestion_usurio, menu);
		return true;
	}

}
