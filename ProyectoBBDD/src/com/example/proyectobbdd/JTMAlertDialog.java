package com.example.proyectobbdd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.widget.EditText;

public class JTMAlertDialog implements DialogInterface.OnClickListener{

	private int id_pregunta = 0;
	InterfaceJTMAlertDialog JTMp = null;
	
	EditText input = null;

	@SuppressWarnings("deprecation")
	public void JTMPreguntar(final InterfaceJTMAlertDialog JTMp, int id_pregunta, String titol, String missatge, 
			String boto1, String boto2, String boto3) {
		
		this.id_pregunta = id_pregunta;
		this.JTMp = JTMp;
		
		AlertDialog ad;
		
		ad = new AlertDialog.Builder((Context)JTMp).create();
		ad.setTitle(titol);
		ad.setMessage(missatge);
		ad.setButton(boto1, this);
		if (boto2 != "") ad.setButton2(boto2, this);		
		if (boto3 != "") ad.setButton3(boto3, this);
		
		ad.show();
	}
	
	// Sols per a crides des de classes anònimes
	@SuppressWarnings("deprecation")
	public void JTMPreguntar(Context ctx, final InterfaceJTMAlertDialog JTMp, int id_pregunta, String titol, String missatge, 
			String boto1, String boto2, String boto3) {
		
		this.id_pregunta = id_pregunta;
		this.JTMp = JTMp;
		
		AlertDialog ad;
		
		ad = new AlertDialog.Builder(ctx).create();
		ad.setTitle(titol);
		ad.setMessage(missatge);
		ad.setButton(boto1, this);
		if (boto2 != "") ad.setButton2(boto2, this);		
		if (boto3 != "") ad.setButton3(boto3, this);
		
		ad.show();
	}
	
	public void JTMPreguntarText(final InterfaceJTMAlertDialog JTMp, int id_pregunta, String titol, String missatge, 
			String missatgeDefecte, String boto1, String boto2) {
		
		this.id_pregunta = id_pregunta;
		this.JTMp = JTMp;
		
		AlertDialog.Builder ad = new AlertDialog.Builder((Context)JTMp);
		ad.setTitle(titol);
		ad.setMessage(missatge);
		
		input = new EditText((Context)JTMp);
		ad.setView(input);
		input.setText(missatgeDefecte);
		
		ad.setPositiveButton(boto1, this);
		ad.setNegativeButton(boto2, this);
		
		ad.show();
	}
	
	public void JTMPreguntarLlista(final InterfaceJTMAlertDialog JTMp, int id_pregunta, String titol, String missatge, 
	String[] llista) {

		this.id_pregunta = id_pregunta;
		this.JTMp = JTMp;
		
		AlertDialog.Builder ad = new AlertDialog.Builder((Context)JTMp);
		ad.setTitle(titol);
		ad.setItems(llista, this);
		
		ad.show();		
	}
	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		if (input == null || arg1 == -2) {
			JTMp.resposta(id_pregunta, arg1, "");				
		} else {
			Editable text = input.getText();
			JTMp.resposta(id_pregunta, arg1, text.toString());		
		}
	}
}
