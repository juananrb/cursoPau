package com.example.proyectobbdd;

import java.util.List;

import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class ListViewActivity extends ListActivity implements OnItemClickListener, InterfaceJTMAlertDialog {

	public int idToModify;
	private ModeloDatos modelo;
	private String accion = "NADA";
	private String indiceUsuBorrar = null;
	
	List<Usuario> listaUsuarios = null;
	String[] sUsuarios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);
		Log.d("ProyectoBBDD", this.getLocalClassName().toString()+": Passa per onCreate");

		modelo = new ModeloDatos(this);
		modelo.abrirConexionBD();
		listaUsuarios = modelo.selectAll();
		
		Intent intent = getIntent();
		if (intent.hasExtra("ACCION")) {
			accion = intent.getExtras().getString("ACCION");
		}
		
		sUsuarios = new String[listaUsuarios.size()];
		int i = 0;
		for (Usuario usuario : listaUsuarios) {
			sUsuarios[i] = usuario.getName() + " - " + usuario.getNumber() + " - " + usuario.getSkypeid() + " - " + usuario.getAddress();
			i++;
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sUsuarios);
		this.setListAdapter(adapter);
		getListView().setOnItemClickListener(this);
	}

 
	@Override
	public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
		Usuario usuario = listaUsuarios.get(arg2);
		String indice = String.valueOf(usuario.getId());
		
		// Actualització 
		if (accion.matches("modificarUsuario")) {
			Log.d("ProyectoBBDD", "Ha seleccionado modificar el usuario: " + indice);
	        Bundle extras = new Bundle();
	        extras.putString("ACCION", "modificarUsuario");
	        extras.putString("IDMODIFICAR", indice);
	        startActivity(new Intent().setComponent(new ComponentName
	        		(ListViewActivity.this, UsuarioActivity.class)).putExtras(extras));
	        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			finish();
		}
		
		// Esborrat
		if (accion.matches("bajaUsuario")) {
			indiceUsuBorrar = indice;
			new JTMAlertDialog().JTMPreguntar(this, 1, "Mantenimiento de usuarios", 
					"¿Desea borrar el usuario: " + usuario.getName() + "?", "Aceptar", "Cancelar", "");
		}

		// Selecció simple
		if (accion.matches("NADA")) {
			finish();
	        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
	}
	
	@Override
	public void resposta(int id_pregunta, int id_resposta, String text) {
		if (id_resposta == -1) {
			Log.d("ProyectoBBDD", "Ha seleccionado borrar el usuario: " + indiceUsuBorrar);
			modelo.delete(Integer.parseInt(indiceUsuBorrar));	
		}
		finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
