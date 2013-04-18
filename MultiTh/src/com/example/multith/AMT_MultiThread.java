package com.example.multith;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AMT_MultiThread extends Activity {
	protected final static String CT_TAG = "AMT_MultiThread";
	int mRegistrosProcesados = 0;
	
	TextView mTVMsg1;
	TextView mTVMsg2;
	
	ProcesadorBatch mPB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amt_multi_thread);
		
		mTVMsg1 = (TextView)findViewById(R.id.amt_tv_msg1);
		mTVMsg2 = (TextView)findViewById(R.id.amt_tv_msg2);
		
		findViewById(R.id.amt_bt_lento).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				funcionLento();
			}
		});
		
		findViewById(R.id.amt_bt_rapido).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				funcionRapida();
			}
		});

		findViewById(R.id.amt_bt_lentoasync).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				funcionAsyncTask();
			}
		});
		
		findViewById(R.id.amt_bt_startservice).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				iniciarServicio();
			}
		});
		
		findViewById(R.id.amt_bt_stopservice).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pararServicio();
			}
		});
		
		findViewById(R.id.amt_bt_conecta).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				conectaServicio();
			}
		});
		
		findViewById(R.id.amt_bt_desconecta).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				desconectaServicio();
			}
		});
		
		findViewById(R.id.amt_bt_refresca).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				preguntaServicio();
			}
		});
	}
	
	private void refrescaRegistros(int i) {
		//synchronized (this) {
			mRegistrosProcesados += i;
		//}
		mTVMsg1.setText("Registros procesados: " + mRegistrosProcesados);
	}
	
	private void funcionRapida() {
		refrescaRegistros(1);
	}
	
	private void funcionLento() {
		Thread lentorro = new Thread(new Runnable() {
			@Override
			public void run() {
				Log.i(CT_TAG, "Soy lentorro y mi nombre java es: " + Thread.currentThread().getName());
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Log.i(CT_TAG, "Ahora estoy en: " + Thread.currentThread().getName());
						refrescaRegistros(100);
					}
				});	
			}
		});
		Log.i(CT_TAG, "Voy a inciar a lentorro. Soy: " + Thread.currentThread().getName());
		lentorro.start();
	}
	
	class ProcesadorBatch extends AsyncTask<Integer, Integer, String> {
		protected static final String CT_TAG_ASYNC = "ProcesadorBatch";
		
		@Override
		protected String doInBackground(Integer... params) {
			Log.i(CT_TAG_ASYNC, "doInBackground iniciado desde: " + Thread.currentThread().getName());
			int nCiclos = params[0];
			int nRegistrosCiclo = params[1];
			
			for(int i = 0; i < nCiclos && !isCancelled(); i++) {
				try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				publishProgress(nRegistrosCiclo);
			}
			
			mPB = null;
			Log.i(CT_TAG_ASYNC, "doInBackground finalizando desde: " + Thread.currentThread().getName());
			return isCancelled() ? "Cancelado" : "Finalizado";
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i(CT_TAG_ASYNC, "onPostExecute invocado desde: " + Thread.currentThread().getName());
		}

		@Override
		protected void onPreExecute() {
			Log.i(CT_TAG_ASYNC, "onPreExecute invocado desde: " + Thread.currentThread().getName());
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			Log.i(CT_TAG_ASYNC, "onProgressUpdate invocado desde: " + Thread.currentThread().getName());
			for (Integer prog : values)
				refrescaRegistros(prog);
		}

		@Override
		protected void onCancelled() {
			Log.i(CT_TAG_ASYNC, "onCancelled invocado desde: " + Thread.currentThread().getName());
		}
		
		
	}
	
	protected void funcionAsyncTask() {
		if (mPB == null) {
			mPB = new ProcesadorBatch();
			mPB.execute(/*nCicles*/7, /*nRegisters*/ 33);
		}
		Toast.makeText(this, "La tarea ya esta en marcha", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPB != null)
			mPB.cancel(true);
	}
	
	////////////// Funciones de control de servicio
	protected void iniciarServicio() {
		Intent i = new Intent(this, ServicioRegistros.class);
		i.putExtra(ServicioRegistros.CT_EXTRA_VALORES, 10);
		startService(i);
	}
	
	protected void pararServicio() {
		stopService(new Intent(this, ServicioRegistros.class));
	}
	
	////////////// Suscripcion al evento
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			mTVMsg2.setText("Actualización del servicio (" + 
					intent.getIntExtra(ServicioRegistros.CT_BROADCAST_PENDIENTES, 0) + "/" + 
					intent.getIntExtra(ServicioRegistros.CT_BROADCAST_PROCESADOS, 0) + ")");
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
		IntentFilter inf = new IntentFilter();
		inf.addAction(ServicioRegistros.CT_BROADCAST_ACCION);
		registerReceiver(mReceiver, inf);
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(mReceiver);
	}
	
	///////////////// Conexión "directa" al servicio
	ServicioRegistros.RegistrosBinder mConexion = null;
	ServiceConnection mServicio = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(CT_TAG, "Conectado al servicio");
			mConexion = (ServicioRegistros.RegistrosBinder)service;
		}
		
		@Override 
		public void onServiceDisconnected(ComponentName name) {
			mConexion = null;
		}
	};
	
	protected void conectaServicio() {
		bindService(new Intent(this, ServicioRegistros.class), mServicio, BIND_AUTO_CREATE);
	}
	
	protected void desconectaServicio() {
		if (mConexion != null)
			unbindService(mServicio);
		mConexion = null;
	}
	
	protected void preguntaServicio() {
		if (mConexion != null) {
			if (mConexion.isRunning()) {
				mTVMsg2.setText("Actualización del servicio (" + 
						mConexion.getPendientes() + "/" + 
						mConexion.getProcesados() + ")");
			} else {
				mTVMsg2.setText("El servicio está parado");
			}
		}
	}
}
