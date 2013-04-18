package com.example.proyectobbdd;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GestionActivity extends Activity implements OnClickListener, InterfaceJTMAlertDialog {

	ModeloDatos modelo = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        
        findViewById(R.id.btn_AltaUsuario).setOnClickListener((OnClickListener) this);
        findViewById(R.id.btn_ModificarUsuario).setOnClickListener((OnClickListener) this);
        findViewById(R.id.btn_BajaUsuario).setOnClickListener((OnClickListener) this);
        findViewById(R.id.btn_ListadoUsuarios).setOnClickListener((OnClickListener) this);        
    }

    @Override
	public void onClick(View boto) {
        Bundle extras = new Bundle();
        
		switch (boto.getId()) {
		case R.id.btn_AltaUsuario:
	        startActivity(new Intent().setComponent(new ComponentName
	        		(GestionActivity.this, UsuarioActivity.class)));
			break;

		case R.id.btn_ModificarUsuario:
	        extras.putString("ACCION", "modificarUsuario");
	        startActivity(new Intent().setComponent(new ComponentName
	        		(GestionActivity.this, ListViewActivity.class)).putExtras(extras));
			break;

		case R.id.btn_BajaUsuario:
	        extras.putString("ACCION", "bajaUsuario");
	        startActivity(new Intent().setComponent(new ComponentName
	        		(GestionActivity.this, ListViewActivity.class)).putExtras(extras));
			break;

		case R.id.btn_ListadoUsuarios:
	        startActivity(new Intent().setComponent(new ComponentName
	        		(GestionActivity.this, ListViewActivity.class)));
			break;
		}
		
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
    
    public void borrarTodo(View v){
		new JTMAlertDialog().JTMPreguntar(this, 1, "Mantenimiento de usuarios", 
				"Se borrarán todos los datos ¿Es correcto?", "Aceptar", "Cancelar", "");		

    }

	@Override
	public void resposta(int id_pregunta, int id_resposta, String text) {
		if (id_resposta==-1) {
			modelo.abrirConexionBD();
	    	modelo.deleteAll();
			modelo.cerrarConexionBD();
		}
	}

}
