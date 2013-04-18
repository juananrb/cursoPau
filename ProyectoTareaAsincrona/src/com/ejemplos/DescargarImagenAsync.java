package com.ejemplos;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.graphics.*;
import android.app.*;

public class DescargarImagenAsync extends AsyncTask<String, Integer, Bitmap> {
	private Context mContext;
	
	public DescargarImagenAsync() {
		// TODO Auto-generated constructor stub
	}
	
	public DescargarImagenAsync(Context context) {
		this.mContext = context;
	}
	
	@Override
	protected void onPreExecute() {
		// Colocariamos cosas a hacer previamente a la ejecucion de la tarea
		Log.d("onPreExecute", Thread.currentThread().getName());
	}
	
	@Override
	protected Bitmap doInBackground(String... url) {
		Log.d("doInBackground", Thread.currentThread().getName());
		HttpClient httpClient = ClienteHttp.getHttpCliente();
		
		HttpGet request = new HttpGet(url[0]);
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(params, 60000);
		request.setParams(params);
		publishProgress(20);
		
		try {
			HttpResponse response = httpClient.execute(request);
			publishProgress(50);
			
			byte[] imagen = EntityUtils.toByteArray(response.getEntity());
			
			publishProgress(75);
			
			Bitmap bmt = BitmapFactory.decodeByteArray(imagen, 0,
					                                   imagen.length);
			publishProgress(100);
			
			return bmt;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;//OJO
	}
	
	@Override
	protected void onProgressUpdate(Integer... progreso) {
		Log.d("onProgressUpdate", Thread.currentThread().getName());
		TextView mText = (TextView) ((Activity) mContext).findViewById(R.id.textView1);
		mText.setText("progreso de la tarea: " + progreso[0]);
	}
	
	@Override
	protected void onPostExecute(Bitmap result) {
		Log.d("onPostExecute", Thread.currentThread().getName());
		if(result != null){
			ImageView mImagen = (ImageView) 
			           ((Activity) mContext).findViewById(R.id.imageView1);
			mImagen.setImageBitmap(result);
		}else{
			TextView mText = (TextView) 
					           ((Activity) mContext).findViewById(R.id.textView1);
			mText.setText("Se ha producido un error, intentalo mas tarde.");
		}
	}


}








