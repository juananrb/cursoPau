package com.example.usersdb;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import com.example.usersdb.usuarios.BDUsuarios;
import com.example.usersdb.usuarios.Usuario;
import com.example.usersdb.usuarios.UsuarioDAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AUL_UserList extends Activity {
	private static final String CT_TAG = "AUL_UserList";
	SQLiteDatabase mDB;
	UsuarioDAO mUsuarioDAO;
	ListView mLVUsuarios;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aul_user_list);
		
		mDB = new BDUsuarios(this).getWritableDatabase();
		mUsuarioDAO = new UsuarioDAO(mDB);
		
		mLVUsuarios = (ListView)findViewById(R.id.aul_lv_usuarios);
		registerForContextMenu(mLVUsuarios);
		
		//prepararLVDesdeArrayAdapter();
		prepararLVDesdeCursorAdapter();
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v == mLVUsuarios) {
			getMenuInflater().inflate(R.menu.aul_contextual, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch(item.getItemId()) {
		case R.id.aul_mn_editar:
			actualizaPorActivity(info.id);
			break;
		case R.id.aul_mn_borrar:
			mUsuarioDAO.delete(info.id);
			SimpleCursorAdapter adp = (SimpleCursorAdapter)mLVUsuarios.getAdapter();
			adp.getCursor().requery();
			// borrarUsuario(info.id);
			break;
		default: return super.onContextItemSelected(item);
		}
		return true;
	}

	private void prepararLVDesdeArrayAdapter() {
		ArrayAdapter<Usuario> adapter = 
				new ArrayAdapter<Usuario>(
						this, 
						android.R.layout.simple_list_item_1,
						mUsuarioDAO.getAll());
		mLVUsuarios.setAdapter(adapter);
	}
	
	private void prepararLVDesdeCursorAdapter() {
		Cursor c = mUsuarioDAO.getAllAsCursor();
		String from[] = {UsuarioDAO.CT_ID, UsuarioDAO.CT_APELLIDOS};
		int to[] = {R.id.aul_li_tv_id, R.id.aul_li_tv_nombre};
		
		SimpleCursorAdapter adapter = 
				new SimpleCursorAdapter(
						this, 
						R.layout.aul_list_item, 
						c, 
						from, 
						to) {

							@Override
							public void bindView(View view, Context context, Cursor cursor) {
								super.bindView(view, context, cursor);
								
								// Mapeo correcto de la fecha
								String sFecha = "";
								if (cursor.isNull(cursor.getColumnIndex(UsuarioDAO.CT_FECHA_NAC))) {
									sFecha = Usuario.getFechaNacimientoString(null);
								} else {
									sFecha = Usuario.getFechaNacimientoString(new Date(cursor.getLong(cursor.getColumnIndex(UsuarioDAO.CT_FECHA_NAC))));
								}
								
								((TextView)view.findViewById(R.id.aul_li_tv_fecha)).setText(sFecha);
								
								// Mapeo correcto del nombre
								TextView tvNombre = (TextView)view.findViewById(R.id.aul_li_tv_nombre);
								String texto = tvNombre.getText().toString();
								texto += ", ";
								texto += cursor.getString(cursor.getColumnIndex(UsuarioDAO.CT_NOMBRE));
								tvNombre.setText(texto);
							}
			
		};
		
		mLVUsuarios.setAdapter(adapter);
		startManagingCursor(c);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.aul_options, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.aul_mn_nuevo:
			insertaPorActivity();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void hazAccion(View v) {
		//insertaUsuario();
		//actualizarUsuario4();
		//borrarUsuario(4);
		insertaPorActivity();
		//actualizaPorActivity();
	}
	
	private void insertaPorActivity() {
		startActivity(new Intent(this, AGU_GestionUsuario.class));
	}
	
	private void actualizaPorActivity(long id) {
		Intent i = new Intent(this, AGU_GestionUsuario.class);
		i.putExtra(AGU_GestionUsuario.CT_EXTRA_ID, id);
		startActivity(i);
	}
	
	private void borrarUsuario(long id) {
//		Usuario u = new Usuario();
//		u.setId(id);
//		mUsuarioDAO.delete(u);
		long records = mUsuarioDAO.delete(id);
		Log.i(CT_TAG, "Usuarios borrados: " + records);
	}
	
	private void actualizarUsuario4() {
		Usuario u = new Usuario();
		u.setNombre("Rodrigo");
		u.setApellidos("Rodríguez");
		Calendar c = Calendar.getInstance();
		c.set(2005, 0, 1);
		u.setFechaNacimiento(c.getTime());
		u.setId(4);
		
		long records = mUsuarioDAO.update(u);
		Log.i(CT_TAG, "Usuarios actualizados: " + records);
	}
	
	private void insertaUsuario() {
		Usuario u = new Usuario();
		u.setNombre("Fernandito");
		u.setApellidos("Fernández");
		Calendar c = Calendar.getInstance();
		c.set(1978, 0, 1);
		u.setFechaNacimiento(c.getTime());
		
		long id = mUsuarioDAO.insertar(u);
		Log.i(CT_TAG, "Usuario insertado con id " + id);
		
	}

	public void hazQuery(View v) {
		Vector<Usuario> todos = mUsuarioDAO.getAll();
		for (Usuario u : todos) 
			Log.i(CT_TAG, u.toString());
	}
}
