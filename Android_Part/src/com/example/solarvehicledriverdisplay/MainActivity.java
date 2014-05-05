package com.example.solarvehicledriverdisplay;

import java.io.IOException;
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
	
	// List to store every randomly generated data object -- Edited by Zhuo Chen
	public ArrayList<DataObject> dataList= new ArrayList<DataObject>();
	// key of dataList sent to VisualActivity from MainAcitivity when click view analysis -- Edited by Zhuo Chen
	public final static String EXTRA_MESSAGE = "com.example.VisualActivity.MESSAGE";
	// restriction number of generating data -- Edited by Zhuo Chen
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
				        	
				        	// create temp DataObject for each randomly generated data
				        	// and add it into list -- Edited by Zhuo Chen
				        	DataObject temp = new DataObject(obj.speed, obj.batteryCharge, obj.arrayPower, obj.motorCurrent, obj.batteryCurrent);
				        	dataList.add(temp);
				        	count++;
				        	final TextView speedTextView = (TextView)findViewById(R.id.speed);
				        	if (obj.speed < 10)
				        	{
				        		speedTextView.setText(""+obj.speed+"  mph");
				        	}
				        	else
				        	{
				        		speedTextView.setText(""+obj.speed+" mph");
				        	}
				        	TextView batteryChargeTextView = (TextView)findViewById(R.id.batteryCharge);
				        	batteryChargeTextView.setText(""+obj.batteryCharge+"%");
				        	TextView powerTextView = (TextView)findViewById(R.id.power);
				        	powerTextView.setText(""+obj.arrayPower+" W");
				        	TextView motorCurrentTextView = (TextView)findViewById(R.id.motorCurrent);
				        	motorCurrentTextView.setText(""+obj.motorCurrent+" A");
				        	TextView batteryCurrentTextView = (TextView)findViewById(R.id.batteryCurrent);
				        	batteryCurrentTextView.setText(""+obj.batteryCurrent+" A");
				        	
				        	//invalidate
//				        	speedTextView.invalidate();
//				        	batteryChargeTextView.invalidate();
//				        	powerTextView.invalidate();
//				        	motorCurrentTextView.invalidate();
//				        	batteryCurrentTextView.invalidate();
//				        	
//				        	speedTextView.postInvalidate();
//				        	batteryChargeTextView.postInvalidate();
//				        	powerTextView.postInvalidate();
//				        	motorCurrentTextView.postInvalidate();
//				        	batteryCurrentTextView.postInvalidate();
				        	try {
								temp.send(temp);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				        	
				        	runOnUiThread(new Runnable() {
				        		   @Override
				        		   public void run() {
				        			   speedTextView.setText(""+obj.speed+"  mph");
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
	
	/**
	 * @author Zhuo Chen
	 * 
	 * 1.update the speed, batteryCharge, power, motorCurrent, batteryCurrent
	 *   TextView in MainActivity UI interface
	 * 2.need to be called each time when data generated
	 * 
	 * @param data
	 */
	public void update(DataObject data)
	{	
		TextView speed = (TextView)this.findViewById(R.id.speed);
		TextView batteryCharge = (TextView)this.findViewById(R.id.batteryCharge);
		TextView power = (TextView)this.findViewById(R.id.power);
		TextView batteryCurrent = (TextView)this.findViewById(R.id.batteryCurrent);
		TextView motorCurrent = (TextView)this.findViewById(R.id.motorCurrent);
		
		String speedStr;
		
		if (data.speed < 10)
		{
			speedStr = data.speed + "  mph";
		}
		else
		{
			speedStr = data.speed + " mph";
		}
		
		String batteryStr = data.batteryCharge + "%";
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
	
	
	/**
	 * @author Zhuo Chen
	 * 
	 * 1.view analysis button handler to create a fragment activity VisualActivity.class
	 * 2.send dataList to VisualActivity using intent
	 * 
	 * @param view
	 */
	public void viewClick(View view)
	{
		Intent intent = new Intent(this, VisualActivity.class);
    	Bundle bundle = new Bundle();
    	//ArrayList<DataObject> objlist = getDataFromDatabases();
    	bundle.putSerializable(EXTRA_MESSAGE, dataList);
    	intent.putExtras(bundle);
		startActivity(intent);
	}
}
