package com.example.solarvehicledriverdisplay;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		
		String speedStr = data.speed + " m/s";
		String batteryStr = data.batteryCharge + " ?";
		String powerStr = data.arrayPower + " W";
		String batteryCurrentStr = data.batteryCurrent + " A";
		String motorCurrentStr = data.motorCurrent + " A";
		
		speed.setText(speedStr);
		batteryCharge.setText(batteryStr);
		power.setText(powerStr);
		batteryCurrent.setText(batteryCurrentStr);
		motorCurrent.setText(motorCurrentStr);
	}
	
	
}
