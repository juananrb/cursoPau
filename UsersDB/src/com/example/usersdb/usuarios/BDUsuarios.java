package com.example.usersdb.usuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDUsuarios extends SQLiteOpenHelper {
	private final static String CT_BD = "usuarios.bd";
	private final static int CT_VERSION = 12;

	public BDUsuarios(Context context) {
		super(context, CT_BD, null, CT_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		UsuarioDAO.createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int old_ver, int new_ver) {
		int i = old_ver + new_ver;
	}

}