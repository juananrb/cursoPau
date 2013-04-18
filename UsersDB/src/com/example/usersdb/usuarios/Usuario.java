package com.example.usersdb.usuarios;

import java.io.Serializable;
import java.util.Date;

import android.text.format.DateFormat;

public class Usuario {
	protected long mId;
	protected String mNombre;
	protected String mApellidos;
	protected Date mFechaNacimiento;
	
	public long getId() {
		return mId;
	}
	public void setId(long mId) {
		this.mId = mId;
	}
	public String getNombre() {
		return mNombre;
	}
	public void setNombre(String mNombre) {
		this.mNombre = mNombre;
	}
	public String getApellidos() {
		return mApellidos;
	}
	public void setApellidos(String mApellidos) {
		this.mApellidos = mApellidos;
	}
	public Date getFechaNacimiento() {
		return mFechaNacimiento;
	}
	public void setFechaNacimiento(Date mFechaNacimiento) {
		this.mFechaNacimiento = mFechaNacimiento;
	}

	public String getFechaNacimientoString() {
//		if (mFechaNacimiento == null) 
//			return "<<Desconocido>>";
//		return DateFormat.format("dd/MM/yyyy", mFechaNacimiento).toString();
		return Usuario.getFechaNacimientoString(mFechaNacimiento);
	}
	
	static public String getFechaNacimientoString(Date d) {
		if (d == null) 
			return "<<Desconocido>>";
		return DateFormat.format("dd/MM/yyyy", d).toString();
	}
	
	public String toString() {
		return "(" + mId + ") " + mNombre + " " + mApellidos + " (" + getFechaNacimientoString() + ")";
	}
}
