package com.ejemplo.calculadora;

import android.content.Context;
import android.util.Log;

public class Operacion {
	private int cant1;
	private int cant2;
	private String op;
	private int total;
	private Context ctx;

	public Operacion() {
	}
	
	public Operacion(Context ctx) {
		this.ctx = ctx;
	}

	public Operacion(int cant1, int cant2, String op) {
		super();
		this.cant1 = cant1;
		this.cant2 = cant2;
		this.op = op;
	}
	
	public int calcular(){
		Log.d("Calcular", "c1: " + cant1 + "-Cant2: " + cant2 + "-Op: " + op);
		switch(op.charAt(0)){
			case '+':
				total = cant1 + cant2;
				break;
			case '-':
				total = cant1 - cant2;
				break;
			case '*':
				total = cant1 * cant2;
				break;
			case '/':
				total = cant1 / cant2;
				break;
			default:
				break;
		}
		return total;
	}

	public int getCant1() {
		return cant1;
	}

	public void setCant1(int cant1) {
		this.cant1 = cant1;
	}

	public int getCant2() {
		return cant2;
	}

	public void setCant2(int cant2) {
		this.cant2 = cant2;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	

}
