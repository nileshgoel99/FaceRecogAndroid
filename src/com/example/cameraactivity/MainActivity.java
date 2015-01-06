package com.example.cameraactivity;



import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	Button btnTakePhoto;
	ImageView imgTakenPhoto;
	TextView ipnumber;
	TextView portnumber;
	static String IP;
	static String port;
	private static final int CAM_REQUEST = 1313;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipnumber = (TextView)findViewById(R.id.ip);
        portnumber = (TextView)findViewById(R.id.port);
        btnTakePhoto = (Button) findViewById(R.id.buton1);
 
        //imgTakenPhoto = (ImageView) findViewById(R.id.imageview1);
        
        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());
        
        
        
        
    }

    
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){    	
    	
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == CAM_REQUEST){
    		Bitmap thumbnail = 	(Bitmap) data.getExtras().get("data");
    		//Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.d);
    		ByteArrayOutputStream stream = new ByteArrayOutputStream();
    		thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    		byte[] byteArray = stream.toByteArray();
    	
    		
    		//imgTakenPhoto.setImageBitmap(thumbnail);
    		
    		
    		//startActivity(getWhosWho);
    		String text = "";
    		String attachmentName = "face";
    		String attachmentFileName = "photo.jpg";
    		String clrf = "\r\n";
    		String twoHypens = "--";
    		String boundary = "$$$$$BoundBig$$$$$";
    		
    		
    		BufferedReader reader = null;
    		try {
    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    			StrictMode.setThreadPolicy(policy);
    			URL url = new URL("http://192.168.1.101:8080/FaceRec/rest/face/recognize");
    			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    			
    			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" +boundary);
    			conn.setRequestMethod("POST");
    			conn.setDoOutput(true);
    			//OutputStream wr = conn.getOutputStream();
    			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    			
    			wr.writeBytes(twoHypens + boundary + clrf);
    			wr.writeBytes("Content-Disposition: form-data; name=\""+attachmentName+"\"; filename=\""+attachmentFileName+"\"");
    			wr.writeBytes(clrf);
    			wr.writeBytes("Content-Type: image/jpeg");
    			wr.writeBytes(clrf);
    			wr.writeBytes(clrf);
    			byte[] pixels = new byte[thumbnail.getWidth() * thumbnail.getHeight()];
    			for(int i=0;i<thumbnail.getWidth();++i){
    				
    				for(int j=0;j<thumbnail.getHeight();++j){
    					
    					pixels[i+j] = (byte) ((thumbnail.getPixel(i, j) & 0x80) >> 7);
    				}
    			}
    			wr.write(byteArray);
    			//wr.write(pixels);
    			wr.writeBytes(clrf);
    			wr.writeBytes(twoHypens + boundary + twoHypens + clrf);
    			wr.flush();
    			wr.close();
    			long count = 0l;
    			int n=byteArray.length;
    			//wr.write(byteArray, 0 ,n);
    			
    			
    			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    			StringBuilder sb = new StringBuilder();
    			String line = "";
    			
    			while((line = reader.readLine()) != null){
    				
    				sb.append(line + "\n");
    			}
    			
    			text = sb.toString();
    			
    			//TextView textview2 = (TextView)findViewById(R.id.textView2);
    			//textview2.setText(text);
    			
    			System.out.println(text);
    			Log.e("RESP", text);
    			
    			String resjson = "{\"name\":\"Obama\",\"corpid\":\"A234232\",\"disgust\":\"0.05\",\"sadness\":\"0.24\",\"anger\":\"0.37\",\"happiness\":\"0.0\",\"neutral\":\"0.34\",\"surprise\":\"0.0\",\"fear\":\"0.0\"}";
    		
    		
    		Intent who = new Intent(this, CorpId.class);
			who.putExtra("BitmapImage", thumbnail);
			who.putExtra("resjson", text);
			startActivity(who);
    			
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} 
    	} 
    }
    
    public void getCorpId(View view){
		
		//Intent getWhosWho = new Intent(this, CorpId.class);
		//startActivity(getWhosWho);
    	
    	
	}
    public void getSettings(View view){
		
		Intent settings = new Intent(this, CorpId.class);
		startActivity(settings);
    	
	}
    class btnTakePhotoClicker implements Button.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAM_REQUEST);
			
			
		}
    	
    }
}
