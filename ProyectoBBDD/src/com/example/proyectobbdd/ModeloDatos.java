package com.example.proyectobbdd;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class ModeloDatos {

	private Context context;
	private static final String DATABASE_NAME = "usuarios.db";
	private static final int DATABASE_VERSION = 3;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String CREAR_TABLA = "Create Table Usuarios " +
			"(id INTEGER PRIMARY KEY, name TEXT, " +
			"number TEXT, skypeid TEXT, address TEXT)";
	private static final String ELIMINAR_TABLA = "Drop Table IF Exists Usuarios";
	private static final String INSERTAR = "Insert into usuarios " +
			"(name, number, skypeid, address) " +
			"values(?,?,?,?)";	
	OpenHelper openHelper = null;

	public ModeloDatos() {
		
	}

	public ModeloDatos(Context context) {
		super();
		this.context = context;
	}

	public void abrirConexionBD(){
		openHelper = new OpenHelper(context);
		db = openHelper.getWritableDatabase();
		insertStmt = db.compileStatement(INSERTAR);
		Log.d("ProyectoBBDD", "Abriendo conexión");
	}
	
	private class OpenHelper extends SQLiteOpenHelper {
	
		OpenHelper(Context context1) {
			super (context1, DATABASE_NAME, null, DATABASE_VERSION);
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("ProyectoBBDD", "OpenHelper.onCreate: Creant estructura de BD");
			db.execSQL(CREAR_TABLA);
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.d("ProyectoBBDD", "OpenHelper.onUpgrade: Actualitzant estructura de BD");
			db.execSQL(ELIMINAR_TABLA);
			db.execSQL(CREAR_TABLA);
			// Faltaria el procés de migració de dades
		}		
	}

	public void cerrarConexionBD(){
		Log.d("ProyectoBBDD", "Cerrando conexión");
		if (db != null) db.close();
	}
	
	public List<Usuario> selectAll() {
		
		return select(null);
	}
	
	public List<Usuario> select(String id) {
		List<Usuario> lista = new ArrayList<Usuario>();
		Cursor cursor = null;
		
		try {
			if (id == null)
				cursor = db.query("Usuarios", new String[]{"id", "name", "number", "skypeid", "address"},
						null, null, null, null, "name asc");
			else
				cursor = db.query("Usuarios", new String[]{"id", "name", "number", "skypeid", "address"},
						"id="+id, null, null, null, "name asc");
			
			if (cursor.moveToFirst()){
				do{
					Usuario usuario = new Usuario(cursor.getString(1), cursor.getString(2), 
							cursor.getString(3), cursor.getString(4));
					usuario.setId(Integer.parseInt(cursor.getString(0)));
					lista.add(usuario);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			Log.d("ProyectoBBDD", "select: Error al recuperar los usuarios: " + e.getMessage());
		} finally {
			if (cursor != null && !cursor.isClosed()) cursor.close();
		}
		
		return lista;
	}
	
	public long insert(Usuario usuario){
		insertStmt.bindString(1, usuario.getName()); //OJO QUE AQUÍ HAY QUE EMPEZAR POR EL 1, NO POR EL 0 COMO EN usuario
		insertStmt.bindString(2, usuario.getNumber()); 
		insertStmt.bindString(3, usuario.getSkypeid()); 
		insertStmt.bindString(4, usuario.getAddress()); 
				
		return insertStmt.executeInsert(); // Retorna el número de la PK
	}

	public void update(Usuario usuario){
		
		ContentValues valores = new ContentValues();
		valores.put("name", usuario.getName());
		valores.put("number", usuario.getNumber());
		valores.put("skypeid", usuario.getSkypeid());
		valores.put("address", usuario.getAddress());
		
		db.update("usuarios", valores, "id=?", new String[]{Integer.toString(usuario.getId())});		
	}
	
	public void delete(int indice){
		db.delete("Usuarios", "id="+indice, null);
		// També es podria fer amb els ?
		
	}
	
	public void deleteAll(){
		db.execSQL(ELIMINAR_TABLA);
		db.execSQL(CREAR_TABLA);
	}
}
