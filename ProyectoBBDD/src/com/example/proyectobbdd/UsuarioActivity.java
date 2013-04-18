package com.example.proyectobbdd;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class UsuarioActivity extends Activity implements InterfaceJTMAlertDialog {
	private ModeloDatos modelo;
	private String indiceUsuModificar = null;
	List<Usuario> listaUsuarios = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario);
		Log.d("ProyectoBBDD", this.getLocalClassName().toString()+": Passa per onCreate");

		modelo = new ModeloDatos(this);
		modelo.abrirConexionBD();
		
		Intent intent = getIntent();
		if (intent.hasExtra("IDMODIFICAR")) {
			indiceUsuModificar = intent.getExtras().getString("IDMODIFICAR");
			listaUsuarios = modelo.select(indiceUsuModificar);

			if (listaUsuarios.size()>0) {
				Usuario usuario = listaUsuarios.get(0);
				((EditText)findViewById(R.id.name)).setText(usuario.getName());
				((EditText)findViewById(R.id.number)).setText(usuario.getNumber());
				((EditText)findViewById(R.id.Skypeid)).setText(usuario.getSkypeid());
				((EditText)findViewById(R.id.address)).setText(usuario.getAddress());
			}
		}
	}

	public void Aceptar(View v){
		String name = ((EditText)findViewById(R.id.name)).getText().toString();
		String number = ((EditText)findViewById(R.id.number)).getText().toString();
		String skypeid = ((EditText)findViewById(R.id.Skypeid)).getText().toString();
		String address = ((EditText)findViewById(R.id.address)).getText().toString();
		
		Usuario usuario = new Usuario(name, number, skypeid, address);
		
		if (indiceUsuModificar != null) {
			usuario.setId(Integer.parseInt(indiceUsuModificar));
			modelo.update(usuario);
		}
		else
			modelo.insert(usuario);
		
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	public void Cancelar(View v){
		new JTMAlertDialog().JTMPreguntar(this, 1, "Mantenimiento de usuarios", 
				"Se perderán los cambios. ¿Es correcto?", "Aceptar", "Cancelar", "");		
	}

	@Override
	public void resposta(int id_pregunta, int id_resposta, String text) {
		if (id_resposta==-1) {
			finish();
	        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}
	
	@Override
	protected void onStart() {
		Log.d("ProyectoBBDD", this.getLocalClassName().toString()+": Passa per onStart");
        modelo = new ModeloDatos(this);
		modelo.abrirConexionBD();
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d("ProyectoBBDD", this.getLocalClassName().toString()+": Passa per onStop");
		modelo.cerrarConexionBD();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.d("ProyectoBBDD", this.getLocalClassName().toString()+": Passa per onDestroy");
		modelo.cerrarConexionBD();
		super.onDestroy();
	}

}
