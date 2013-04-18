package com.ejemplo.servicioremoto;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

//Como Serviciop hereda de Service
public class ServidorRemotoActivity extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ServidorRemotoActivity", ".....onCreate");
    }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("ServidorRemotoActivity", ".....onDestroy");
	}
	
	public class ServidorRemotoActivityImpl 
	                            extends IServicioRemotoActivity.Stub {
		@Override
		public double leerValor(String valor) throws RemoteException {
			Log.d("ServidorRemotoActivity", ".....leerValor: " + valor);
			if(valor.equals("MSFT")){
				return 110.25;
			}else if(valor.equals("APPL")){
				return 121.55;
			}else{
				return 0;
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("ServidorRemotoActivity", ".....onBind");
		return new ServidorRemotoActivityImpl();
	}


    
    
}
