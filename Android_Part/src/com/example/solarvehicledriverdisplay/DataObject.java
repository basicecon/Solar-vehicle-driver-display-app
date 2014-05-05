package com.example.solarvehicledriverdisplay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
 * 2.use the random() function to change the ints 
 *    based on their previous values
 * 3.use the send() function to send data to server
 * 4.use the getResponse() function to get more data from server
 * 
 * 
 * note: url port number TBD
 ********************************/

public class DataObject implements Serializable{
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
		
		this.speed = (int) (this.speed + (Math.random()-0.45)*30);
		//this.speed = this.speed;
		this.batteryCharge = (int) (this.batteryCurrent + (Math.random()-0.45)*1);
		//this.batteryCharge = this.batteryCharge;
		this.arrayPower = (int) (this.arrayPower + (Math.random()-0.45)*10);
		//this.arrayPower = this.arrayPower;
		this.motorCurrent = (int) (this.motorCurrent + (Math.random()-0.45)*5);
		//this.motorCurrent = this.motorCurrent;
		this.batteryCurrent = (int) (batteryCurrent + (Math.random()-0.45)*5);
		//this.batteryCurrent = this.batteryCurrent;

		if (this.speed > 60) {this.speed = 60; }
		if (this.batteryCharge > 100) {this.batteryCharge = 100; }
		if (this.arrayPower > 1000) {this.arrayPower = 1000; }
		if (this.motorCurrent > 100) {this.motorCurrent = 100; }
		if (this.batteryCurrent > 100) {this.batteryCurrent = 100; }

		if (this.speed < 0) {this.speed = 0; }
		if (this.batteryCharge < 0) {this.batteryCharge = 0; }
		if (this.arrayPower < 0) {this.arrayPower = 0; }
		if (this.motorCurrent < 0) {this.motorCurrent = 0; }
		if (this.batteryCurrent < 0) {this.batteryCurrent = 0; }
		
		

		return this;

	}
	
	/******************************************************************************
	 * 
	 * @throws IOException 
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
	
	
	public static int send(DataObject obj) throws IOException{
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
		String url = "www.data.cs.purdue.edu:7539/SolarCar/car_info.cgi?action=post&arraypower="+obj.arrayPower+"&batterycurrent="+obj.batteryCurrent+"&speed="+obj.speed+"&motorcurrent="+obj.motorCurrent+"&batterycharge="+obj.batteryCharge;
//		DefaultHttpClient httpclient = new DefaultHttpClient();
//		HttpPost httppostreq = new HttpPost(url);
//		StringEntity se = null;
//		try{
//			se = new StringEntity(jsonobj.toString());
//			se.setContentType("application/json;charset=UTF-8");
//			se.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
//		}catch(Exception e){
//			Log.println(1, "String Entity", "toString method error");
//		}
//		httppostreq.setEntity(se);
//		HttpResponse httpresponse = null;
//		try{
//			httpresponse = httpclient.execute(httppostreq);
//		}catch(Exception e){
//			Log.println(1, "Http Request", "can't send message");
//		}
//		
//		Log.d("HttpResponse", ""+httpresponse);
//		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpResponse response = httpclient.execute(new HttpGet(url));
	    StatusLine statusLine = response.getStatusLine();
	    if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        out.close();
	        String responseString = out.toString();
	        //..more logic
	    } else{
	        //Closes the connection.
	        response.getEntity().getContent().close();
	        throw new IOException(statusLine.getReasonPhrase());
	    }
		
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