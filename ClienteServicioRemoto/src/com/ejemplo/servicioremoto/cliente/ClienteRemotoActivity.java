package com.ejemplo.servicioremoto.cliente;

import com.ejemplo.servicioremoto.IServicioRemotoActivity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClienteRemotoActivity extends Activity 
                                   implements ServiceConnection {
	private IServicioRemotoActivity servicio = null;
	private Button bindbtn;
	private Button callbtn;
	private Button closebtn;
	private EditText et;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_remoto);
        bindbtn = (Button) findViewById(R.id.bindbtn);
        callbtn = (Button) findViewById(R.id.callbtn);
        closebtn = (Button) findViewById(R.id.closebtn);
        et = (EditText) findViewById(R.id.editText1);
        
    }
    
    public void conexionSR(View view){
    	/*bindService(new Intent(IServicioRemotoActivity.class.getName()), 
    			        serConn, Context.BIND_AUTO_CREATE);*/
    	bindService(new Intent(IServicioRemotoActivity.class.getName()), 
		            this, Context.BIND_AUTO_CREATE);    	
    	bindbtn.setEnabled(false);
    	callbtn.setEnabled(true);
    	closebtn.setEnabled(true);
    }
    
    public void peticionSR(View view){
    	callService();
    }
    
    public void cerrarConexionSR(View view){
    	//unbindService(serConn);
    	unbindService(this);
    	bindbtn.setEnabled(true);
    	callbtn.setEnabled(false);
    	closebtn.setEnabled(false);
    }
    
    private void callService(){
    	String val = et.getText().toString();
    	try {
			Toast.makeText(this, "El SR nos devuelve: " + servicio.leerValor(val), 
					       Toast.LENGTH_SHORT).show();
		} catch (RemoteException e) {
			Log.e("callService", "Error e: " + e.getMessage());
		}
    }

  /*  private ServiceConnection serConn = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.d("serConn", "-----onServiceConnected");
			servicio = IServicioRemotoActivity.Stub.asInterface(service);
			callService();
		}
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.d("serConn", "-----onServiceDisconnected");
			servicio = null;//Para el GC le va bien asignarlo a null
		}
    };*/
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cliente_remoto, menu);
        return true;
    }

    @Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		Log.d("serConn", "-----onServiceConnected");
		servicio = IServicioRemotoActivity.Stub.asInterface(service);
		callService();
	}
	@Override
	public void onServiceDisconnected(ComponentName arg0) {
		Log.d("serConn", "-----onServiceDisconnected");
		servicio = null;
	}
    
    
    
}









