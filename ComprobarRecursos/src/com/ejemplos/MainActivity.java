package com.ejemplos;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String tag = "MainActivity";
	private UtilidadesCR cr = null;
	
	public MainActivity() {
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        cr = new UtilidadesCR(this);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Log.d("MainActivity", "Interceptando evento: " + 
		                      item.getTitle().toString());
		TextView txt1 = getTextView();
		if(item.getItemId() == R.id.stringbuffer){
			cr.testStringBuffer(txt1);
		}
		if(item.getItemId() == R.id.comprobar_propiedades){
			cr.testPropiedades(txt1);
		}
		if(item.getItemId() == R.id.comprobar_xml){
			cr.testXML(txt1);
		}
		if(item.getItemId() == R.id.comprobar_asset){
			cr.testAsset(txt1);
		}
		if(item.getItemId() == R.id.comprobar_strings){
			cr.testStrings(txt1);
		}
		if(item.getItemId() == R.id.comprobar_raw){
			cr.testRaw(txt1);
		}
		
		return true;
	}
    
    protected TextView getTextView(){
    	return (TextView) this.findViewById(R.id.text1);
    }
    
}







