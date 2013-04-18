package com.example.proyectograbacionvideo;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class GrabacionVideoActivity extends Activity 
                      implements SurfaceHolder.Callback,
                      OnInfoListener, OnErrorListener {
	private MediaRecorder mRecorder = null;
	private String archivoSalida = null;
	private VideoView mVideoView = null;
	private SurfaceHolder mHolder = null;
	private Button initBtn = null;
	private Button startBtn = null;
	private Button stopBtn = null;
	private Button playBtn = null;
	private Button stopPlayBtn = null;
	private Camera mCamera = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grabacion_video);
		initBtn = (Button) findViewById(R.id.button1);
		startBtn = (Button) findViewById(R.id.button2);
		stopBtn = (Button) findViewById(R.id.button3);
		playBtn = (Button) findViewById(R.id.button4);
		stopPlayBtn = (Button) findViewById(R.id.button5);
		mVideoView = (VideoView) findViewById(R.id.videoView1);
		
	}
	
	public void botonPulsado(View view){
		switch (view.getId()) {
			case R.id.button1:
				initRecorder();
				break;
			case R.id.button2:
				startRecording();
				break;	
			case R.id.button3:
				stopRecording();
				break;				
			case R.id.button4:
				playRecording();
				break;				
			case R.id.button5:
				stopPlayRecording();
				break;	
		}
	}
		
    private void stopRecording() {
		if(mRecorder != null){
			mRecorder.setOnInfoListener(this);
	    	mRecorder.setOnErrorListener(this);
	    	
	    	mRecorder.stop();
	    	releaseRecorder();
	    	releaseCamera();
		}
	}
    

	private void releaseCamera() {
		if(mCamera != null){
			try {
				mCamera.reconnect();
				mCamera.release();
				mCamera = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void releaseRecorder() {
		if(mRecorder != null){
			mRecorder.release();
			mRecorder = null;
		}
	}

	private void initRecorder(){
    	if(mRecorder != null) return;
    	archivoSalida = Environment.getExternalStorageDirectory() +
    			                    "/grabacion.mp4";
    	File f = new File(archivoSalida);
    	if(f.exists()){
    		f.delete();
    	}
    	
    	try {
			mCamera.stopPreview();
			mCamera.unlock();
			mRecorder = new MediaRecorder();
			mRecorder.setCamera(mCamera);

			mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			mRecorder.setVideoSize(176, 144);
			mRecorder.setVideoFrameRate(15);
			
			mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mRecorder.setMaxDuration(7000); // limit to 7 seconds
			mRecorder.setPreviewDisplay(mHolder.getSurface());
			mRecorder.setOutputFile(archivoSalida);

			mRecorder.prepare();
			initBtn.setEnabled(false);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void startRecording(){
    	mRecorder.setOnInfoListener(this);
    	mRecorder.setOnErrorListener(this);
    	mRecorder.start();
    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grabacion_video, menu);
		return true;
	}

	@Override
	public void onError(MediaRecorder arg0, int arg1, int arg2) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(mHolder);//AVISO
			mCamera.startPreview();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	private void playRecording(){
		MediaController mc = new MediaController(this);
		mVideoView.setMediaController(mc);
		mVideoView.setVideoPath(archivoSalida);
		mVideoView.start();
	}
	
	private void stopPlayRecording(){
		mVideoView.stopPlayback();
	}

	@Override
	public void onInfo(MediaRecorder arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	private boolean initCamera() {
		try {
			mCamera = Camera.open();
			Camera.Parameters camParams = mCamera.getParameters();
			mCamera.lock();

			mHolder = mVideoView.getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		} catch (RuntimeException re) {
			re.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (!initCamera())
			finish();
	}
}
