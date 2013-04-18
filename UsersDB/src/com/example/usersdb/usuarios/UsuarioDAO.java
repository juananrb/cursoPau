package com.example.usersdb.usuarios;

import java.util.Date;
import java.util.Vector;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsuarioDAO {
	private static final String CT_TAG = "UsuarioDAO";
	
	public static final String CT_TABLA = "tbl_usuarios";
	
	public static final String CT_ID = "id";
	public static final String CT_NOMBRE = "nombre";
	public static final String CT_APELLIDOS = "apellidos";
	public static final String CT_FECHA_NAC = "fecha_nac";
	
	protected static final String[] CT_CAMPOS = {CT_ID, CT_NOMBRE, CT_APELLIDOS, CT_FECHA_NAC, CT_ID + " as _id"};
	
	SQLiteDatabase mDB;
	
	public static void createTable(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + CT_TABLA +
				"(" + CT_ID + " INTEGER PRIMARY KEY, " +
				CT_NOMBRE + " STRING, " +
				CT_APELLIDOS + " STRING, " +
				CT_FECHA_NAC + " INTEGER)";
		try {
			db.execSQL(sql);
			Log.i(CT_TAG, "Base de datos creada");
		}
		catch (Exception e) {
			Log.e(CT_TAG, "Error creando tabla: " + e.getMessage());
		}
	}

	public UsuarioDAO(SQLiteDatabase db) {
		mDB = db;
	}
	
	public long insertar(Usuario u) {
		ContentValues cv = getCVFromUser(u);
		try {
			return mDB.insert(CT_TABLA, null, cv);
		}
		catch (Exception e) {
			Log.e(CT_TAG, "Error insertando usuario: " + e.getMessage());
			return -1;
		}
	}

	private ContentValues getCVFromUser(Usuario u) {
		ContentValues cv = new ContentValues();
		cv.put(CT_NOMBRE, u.getNombre());
		cv.put(CT_APELLIDOS, u.getApellidos());
		if (u.getFechaNacimiento() != null) 
			cv.put(CT_FECHA_NAC, u.getFechaNacimiento().getTime());
		else
			cv.put(CT_FECHA_NAC, (Long)null);
		return cv;
	}
	
	public long update(Usuario u) {
		ContentValues cv = getCVFromUser(u);
		try {
			String params[] = {Long.toString(u.getId())};
			return mDB.update(CT_TABLA, cv, CT_ID + "= ? ", params);
		} catch (Exception e) {
			Log.e(CT_TAG, "Error actualizando usuario: " + e.getMessage());
			return 0;
		}
	}
	
	public long delete(long id) {
		try {
			String params[] = {Long.toString(id)};
			return mDB.delete(CT_TABLA, CT_ID + "= ? ", params);
		} catch (Exception e) {
			Log.e(CT_TAG, "Error borrando usuario: " + e.getMessage());
			return 0;
		}
	}
	
	public long delete(Usuario u) {
		return delete(u.getId());
	}
	
	public Vector<Usuario> getAll() {
		Cursor c = null;
		try {
			Vector<Usuario> res = new Vector<Usuario>();
			c = mDB.query(CT_TABLA, CT_CAMPOS, null, null, null, null, CT_APELLIDOS + ", " + CT_NOMBRE);
			if (c.moveToFirst()) {
				do {
					// Tratar registro
					res.add(getUsuarioFromCursor(c));
				} while (c.moveToNext());
			}
			c.close();
			return res;
		} catch (Exception e) { 
			Log.e(CT_TAG, "Error obteniendo usuarios: " + e.getMessage());
			if (c != null) 
				c.close();
			return null;
		}
	}
	
	public Cursor getAllAsCursor() {
		Cursor c = null;
		try {
			c = mDB.query(CT_TABLA, CT_CAMPOS, null, null, null, null, CT_APELLIDOS + ", " + CT_NOMBRE);
			return c;
		} catch (Exception e) { 
			Log.e(CT_TAG, "Error obteniendo usuarios: " + e.getMessage());
			if (c != null) 
				c.close();
			return null;
		}
	}

	public Usuario getById(long id) {
		Cursor c = null;
		try {
			Usuario res = null;
			String params[] = {Long.toString(id)};
			c = mDB.query(CT_TABLA, CT_CAMPOS, CT_ID + "=?", params, null, null, null);
			if (c.moveToFirst()) {
				// Tratar registro
				res = getUsuarioFromCursor(c);
			} 
			c.close();
			return res;
		} catch (Exception e) { 
			Log.e(CT_TAG, "Error obteniendo usuarios: " + e.getMessage());
			if (c != null) 
				c.close();
			return null;
		}
	}

	private Usuario getUsuarioFromCursor(Cursor c) {
		Usuario u = new Usuario();
		u.setId(c.getLong(c.getColumnIndex(CT_ID)));
		u.setNombre(c.getString(c.getColumnIndex(CT_NOMBRE)));
		u.setApellidos(c.getString(c.getColumnIndex(CT_APELLIDOS)));
		u.setFechaNacimiento(new Date(c.getLong(c.getColumnIndex(CT_FECHA_NAC))));
		return u;
	}
}
