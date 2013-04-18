package com.ejemplos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.XmlResourceParser;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class UtilidadesCR {
	private Context mContext;

	public UtilidadesCR() {
	}
	
	public UtilidadesCR(Context ctx) {
		this.mContext = ctx;
	}
	
	public void testStringBuffer(TextView txt1){
		StringBuffer resultado = new StringBuffer();
		double t1 = System.currentTimeMillis();
		String s = "netmind";
		for (int i = 0; i < 1000; i++) {
			s += "netmind";
		}
		double t2 = System.currentTimeMillis();
		resultado.append("String ha tardado: " + (t2 - t1) + " milisegundos");
		double t3 = System.currentTimeMillis();
		StringBuffer sb = new StringBuffer("netmind");
		for (int i = 0; i < 40000; i++) {
			sb.append("netmind");
		}
		double t4 = System.currentTimeMillis();
		resultado.append("\nStringBuffer ha tardado: " + (t4 - t3) + 
				                                        " milisegundos");
		txt1.setText(txt1.getText() + "\n" + resultado.toString());
	}
	
	
	public void testPropiedades(TextView txt1){
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager manager = (WindowManager) 
				           mContext.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		txt1.append("\nMetric density: " + metrics.density);
		txt1.append("\nMetric densityDpi (>= API Level4): " + 
		                                         metrics.densityDpi);
		txt1.append("\nMetric alturaPixels: " + metrics.heightPixels);
		txt1.append("\nMetric anchuraPixels: " + metrics.widthPixels);
		txt1.append("\nMetric ydpi density: " + metrics.ydpi);
		//Es necesario concerle permisos
		//con <uses-permission... en AndroidManifest
		int numTareas = 1;
		ActivityManager mg = (ActivityManager) 
				              mContext.getSystemService(Context.ACTIVITY_SERVICE);
		RunningTaskInfo task = mg.getRunningTasks(numTareas).get(0);
		txt1.append("\nUltima tarea ejecutada: " + 
		                           task.baseActivity.toShortString() + 
		                           "\nCon PID: " + task.id);
		
	}
	
	public void testXML(TextView txt1){
		try {
			StringBuffer sb = new StringBuffer();
			Resources res = mContext.getResources();
			XmlResourceParser xpp = res.getXml(R.xml.testeo);
			xpp.next();
			int eventType = xpp.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_DOCUMENT){
					sb.append("***Inicio Documento***");
				}else if(eventType == XmlPullParser.START_TAG){
					sb.append("\nInicio tag: " + xpp.getName());
				}else if(eventType == XmlPullParser.END_TAG){
					sb.append("\nFin tag: " + xpp.getName());
				}else if(eventType == XmlPullParser.TEXT){
					sb.append("\nvalor: " + xpp.getText());
				}
				eventType = xpp.next();
			}
			sb.append("\n***Fin Documento***");
			txt1.append(sb.toString());
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testAsset(TextView txt1){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			AssetManager am = mContext.getAssets();
			is = am.open("Testeo.txt");
			baos = new ByteArrayOutputStream();
			int i = is.read();
			while(i != -1){
				baos.write(i);
				i = is.read();
			}
			txt1.append("\n" + baos.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			    if(baos != null) baos.close();
			    if(is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void testRaw(TextView txt1){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			Resources res = mContext.getResources();
			is = res.openRawResource(R.raw.prueba);
			baos = new ByteArrayOutputStream();
			int i = is.read();
			while(i != -1){
				baos.write(i);
				i = is.read();
			}
			txt1.append("\nRaw: " + baos.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
			    if(baos != null) baos.close();
			    if(is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void testStrings(TextView txt1){
		//Plurales, cuidado con los ceros
		Resources res = mContext.getResources();
		for (int i = 1; i < 5; i++) {
			txt1.append("\n" + res.getQuantityString(R.plurals.test_plurals,
					i));
		}
		
		//Colores
		int rojo = res.getColor(R.color.red);
		txt1.append("\nColor: " + rojo);
		
		//Dimensiones
		txt1.append("\nDimesion: " + res.getDimension(R.dimen.medio));
		
		//Array
		String[] cadenas = res.getStringArray(R.array.test_array);
		for (String item : cadenas) {
			txt1.append("\nElemento array: " + item);
		}
		
		//Strings en otro archivo:strings1.xml
		txt1.append("\nTitulo1: " + res.getString(R.string.titulo1));
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}












