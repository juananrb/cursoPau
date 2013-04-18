package com.ejemplos.android;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class DibujarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		VistaEspecial vista1 = new VistaEspecial(this);
		setContentView(vista1);
		registerForContextMenu(vista1);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			                        ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Menu contexto");
		menu.add(0, v.getId(), 0, "Accion 1");
		menu.add(0, v.getId(), 0, "Accion 2");
	}

	class VistaEspecial extends View{
		float x = 50;
		float y = 50;
		String accion = "";
		Path ruta = new Path();

		public VistaEspecial(Context context) {
			super(context);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.rgb(255, 255, 120));
			Paint paint = new Paint();
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(2);
			paint.setColor(Color.RED);
			
			if(accion.equals("down")){
				ruta.moveTo(x, y);
			}
			if(accion.equals("move")){
				ruta.lineTo(x, y);
			}
			if(accion.equals("top")){
				paint.setColor(Color.WHITE);
				Toast.makeText(getApplicationContext(), 
						       "Pulsacion", Toast.LENGTH_SHORT).show();
			}
			canvas.drawPath(ruta, paint);
		}//onDraw

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			x = event.getX();
			y = event.getY();
			if(event.getAction() == MotionEvent.ACTION_DOWN){
				accion = "down";
			}
			if(event.getAction() == MotionEvent.ACTION_MOVE){
				accion = "move";
			}
			if(event.getAction() == MotionEvent.EDGE_TOP){
				accion = "top";
			}
			invalidate();
			return true;
		}
		
		
		
	}

}








