package com.example.solarvehicledriverdisplay;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	DataObject obj = new DataObject();
	public ArrayList<DataObject> dataList= new ArrayList<DataObject>();
	public final static String EXTRA_MESSAGE = "com.example.VisualActivity.MESSAGE";
	public final int DEFAULTNUMBER = 500;
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setStartButonListener();
	}
	
	public void setStartButonListener(){
		//set button listener
				Button mybutton = (Button) findViewById(R.id.start);
				Log.d("System.err","Debug\n");
				try{
				mybutton.setOnClickListener(new View.OnClickListener() {
				    public void onClick(View v) {
				    	if(obj == null){
				    		obj = new DataObject();
				    	}
				        Log.println(1, "Debug", "Debug\n");
				        int i = 0;
				        while(i < 1 && count < DEFAULTNUMBER){
				        	obj.random();
				        	DataObject temp = new DataObject(obj.speed, obj.batteryCharge, obj.arrayPower, obj.motorCurrent, obj.batteryCurrent);
				        	dataList.add(temp);
				        	count++;
				        	final TextView speedTextView = (TextView)findViewById(R.id.speed);
				        	speedTextView.setText(""+obj.speed+" mph");
				        	TextView batteryChargeTextView = (TextView)findViewById(R.id.batteryCharge);
				        	batteryChargeTextView.setText(""+obj.batteryCharge+" C");
				        	TextView powerTextView = (TextView)findViewById(R.id.power);
				        	powerTextView.setText(""+obj.arrayPower+" W");
				        	TextView motorCurrentTextView = (TextView)findViewById(R.id.motorCurrent);
				        	motorCurrentTextView.setText(""+obj.motorCurrent+" A");
				        	TextView batteryCurrentTextView = (TextView)findViewById(R.id.batteryCurrent);
				        	batteryCurrentTextView.setText(""+obj.batteryCurrent+" A");
				        	
				        	//invalidate
				        	speedTextView.invalidate();
				        	batteryChargeTextView.invalidate();
				        	powerTextView.invalidate();
				        	motorCurrentTextView.invalidate();
				        	batteryCurrentTextView.invalidate();
				        	
				        	speedTextView.postInvalidate();
				        	batteryChargeTextView.postInvalidate();
				        	powerTextView.postInvalidate();
				        	motorCurrentTextView.postInvalidate();
				        	batteryCurrentTextView.postInvalidate();
				        	
				        	runOnUiThread(new Runnable() {
				        		   @Override
				        		   public void run() {
				        			   speedTextView.setText(""+obj.speed+" mph");
				        		   }
				        		});
//				        	try{
//				        		Thread.sleep(200);
//				        	}catch(Exception e){
//				        		Log.println(1, "Sleep", "Failed\n");
//				        	}
				        	
				        	new Handler().postDelayed(new Runnable() {

				                @Override
				                public void run() {
				                	update(obj);
				                }
				            }, 100);
				            
				        	
				        	i++;
				        }
				    }
				});
				}catch(Exception e){
					Log.println(1, "setbutton listener", "Failed\n");
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void update(DataObject data)
	{	
		TextView speed = (TextView)this.findViewById(R.id.speed);
		TextView batteryCharge = (TextView)this.findViewById(R.id.batteryCharge);
		TextView power = (TextView)this.findViewById(R.id.power);
		TextView batteryCurrent = (TextView)this.findViewById(R.id.batteryCurrent);
		TextView motorCurrent = (TextView)this.findViewById(R.id.motorCurrent);
		
		String speedStr = data.speed + " mph";
		String batteryStr = data.batteryCharge + " C";
		String powerStr = data.arrayPower + " W";
		String batteryCurrentStr = data.batteryCurrent + " A";
		String motorCurrentStr = data.motorCurrent + " A";
		
		speed.setText(speedStr);
		batteryCharge.setText(batteryStr);
		power.setText(powerStr);
		batteryCurrent.setText(batteryCurrentStr);
		motorCurrent.setText(motorCurrentStr);
		
		Button mybutton = (Button) findViewById(R.id.start);
		mybutton.performClick();
	}
	
	public void viewClick(View view)
	{
		Intent intent = new Intent(this, VisualActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putSerializable(EXTRA_MESSAGE, dataList);
    	intent.putExtras(bundle);
		startActivity(intent);
	}
}
