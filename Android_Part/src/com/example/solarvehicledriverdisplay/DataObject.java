package com.example.solarvehicledriverdisplay;

import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

/*********************************
 * 
 * 
 * @author suzhaolun
 * 
 * Usage:
 * 1.create a DataObject object using the constructor with no parameters
 * 2.use the random() function to generate new DataObject that changes 
 *   consistently based on previous random values
 * 3.use the send() function to send data to server
 * 4.use the getResponse() function to get more data from server
 * 
 * 
 * note: url port number TBD
 ********************************/

public class DataObject{
	int speed;
	int batteryCharge;
	int arrayPower;
	int motorCurrent;
	int batteryCurrent;

	public DataObject(){
		speed = 30;
		batteryCharge = 50;
		arrayPower = 500;
		motorCurrent = 50;
		batteryCurrent = 50;
		
	}

	public DataObject(int SPEED,int BATTERYCHARGE, int ARRAYPOWER, int MOTORCURRENT, int BATTERYCURRENT){
		speed = SPEED;
		batteryCharge = BATTERYCHARGE;
		arrayPower = ARRAYPOWER;
		motorCurrent = MOTORCURRENT;
		batteryCurrent = BATTERYCURRENT;
		
	}

	public DataObject(DataObject old){
		speed = old.speed;
		batteryCharge = old.batteryCharge;
		arrayPower = old.arrayPower;
		motorCurrent = old.motorCurrent;
		batteryCurrent = old.batteryCurrent;
		
	}
	
	public DataObject random(){
		DataObject random = new DataObject(this);
		random.speed = (int) (random.speed + (Math.random()-0.5)*5);
		this.speed = random.speed;
		random.batteryCharge = (int) (random.batteryCurrent + (Math.random()-0.5)*5);
		this.batteryCharge = random.batteryCharge;
		random.arrayPower = (int) (random.arrayPower + (Math.random()-0.5)*10);
		this.arrayPower = random.arrayPower;
		random.motorCurrent = (int) (random.motorCurrent + (Math.random()-0.5)*5);
		this.motorCurrent = random.motorCurrent;
		random.batteryCurrent = (int) (batteryCurrent + (Math.random()-0.5)*5);
		this.batteryCurrent = random.batteryCurrent;

		if (random.speed > 60) {random.speed = 60; }
		if (random.batteryCharge > 100) {random.batteryCharge = 100; }
		if (random.arrayPower > 1000) {random.arrayPower = 1000; }
		if (random.motorCurrent > 100) {random.motorCurrent = 100; }
		if (random.batteryCurrent > 100) {random.batteryCurrent = 100; }

		if (random.speed < 0) {random.speed = 0; }
		if (random.batteryCharge < 0) {random.batteryCharge = 0; }
		if (random.arrayPower < 0) {random.arrayPower = 0; }
		if (random.motorCurrent < 0) {random.motorCurrent = 0; }
		if (random.batteryCurrent < 0) {random.batteryCurrent = 0; }
		
		try{
    		Thread.sleep(200);
    	}catch(Exception e){
    		Log.println(1, "Sleep", "Failed\n");
    	}

		return random;

	}
	
	/******************************************************************************
	 * 
	 * @send function
	 * send a json object for server to store 
	 * the Return is set to false and doesn't 
	 * expect server to return
	 * 
	 * @getResponse function
	 * send a json object with Return set to true
	 * expect the server to return json object 
	 * 
	 *******************************************************************************/
	
	
	public static int send(DataObject obj){
//		int speed;
//		int batteryCharge;
//		int arrayPower;
//		int motorCurrent;
//		int batteryCurrent;
		
		//create json object
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		
		try{
			jsonobj.put("Speed", obj.speed);
			jsonobj.put("BatteryCharge", obj.batteryCharge);
			jsonobj.put("ArrayPower", obj.arrayPower);
			jsonobj.put("MotorCurrent", obj.motorCurrent);
			jsonobj.put("batteryCurrent", obj.batteryCurrent);
			jsonobj.put("Return", false);						//Return is set to false
		}catch(Exception e){
			Log.println(1, "Json Object", "Unable to set json object value\n");
		}
		
		//create httpclient
		String url = "www.data.cs.purdue.edu:";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httppostreq = new HttpPost(url);
		StringEntity se = null;
		try{
			se = new StringEntity(jsonobj.toString());
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		}catch(Exception e){
			Log.println(1, "String Entity", "toString method error");
		}
		httppostreq.setEntity(se);
		HttpResponse httpresponse = null;
		try{
			httpresponse = httpclient.execute(httppostreq);
		}catch(Exception e){
			Log.println(1, "Http Request", "can't send message");
		}
		
		Log.d("HttpResponse", ""+httpresponse);
		
		return 0;
		
	}
	
	public static  DataObject[] getResponse(){
		JSONObject jsonobj;
		jsonobj = new JSONObject();
		
		try{
			jsonobj.put("Speed", null);
			jsonobj.put("BatteryCharge", null);
			jsonobj.put("ArrayPower", null);
			jsonobj.put("MotorCurrent", null);
			jsonobj.put("batteryCurrent", null);
			jsonobj.put("Return", true);			// Return is set to true
		}catch(Exception e){
			Log.println(1, "Json Object", "Unable to set json object value\n");
		}
		
		//create httpclient
				String url = "www.data.cs.purdue.edu:";
				DefaultHttpClient httpclient = new DefaultHttpClient();
				HttpPost httppostreq = new HttpPost(url);
				StringEntity se = null;
				try{
					se = new StringEntity(jsonobj.toString());
					se.setContentType("application/json;charset=UTF-8");
					se.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
				}catch(Exception e){
					Log.println(1, "String Entity", "toString method error");
				}
				httppostreq.setEntity(se);
				HttpResponse httpresponse = null;
				try{
					httpresponse = httpclient.execute(httppostreq);
				}catch(Exception e){
					Log.println(1, "Http Request", "can't send message");
				}
				
				Log.d("HttpResponse", ""+httpresponse);
				HttpEntity responseEntity = httpresponse.getEntity();
				InputStream is = null;
				try{
					is = responseEntity.getContent();
				}catch(Exception e){
					Log.println(1, "Get content", "response entity");
				}
				
				
		return null;
	}


	

}