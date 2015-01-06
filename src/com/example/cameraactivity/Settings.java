package com.example.cameraactivity;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;



public class Settings extends Activity {

	EditText ipaddressText;
	EditText portText;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
      
        setContentView(R.layout.settings);
        
        
       
        
    }
	public void getHome(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		ipaddressText = (EditText)findViewById(R.id.ipAddressValue);
		portText = (EditText)findViewById(R.id.portNumberValue);
		String ipaddress = ipaddressText.getText().toString();
		String port = portText.getText().toString();
		Log.e("IP", ipaddress);
		setResult(RESULT_OK, intent);
		intent.putExtra(MainActivity.IP, ipaddress);
		intent.putExtra(MainActivity.port, port);
		
		// TODO Add extras or a data URI to this intent as appropriate.
		
		finish();
		
    	
	} 
}
