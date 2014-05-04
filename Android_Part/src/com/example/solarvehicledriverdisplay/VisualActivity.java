package com.example.solarvehicledriverdisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class VisualActivity extends FragmentActivity
{
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	
	ViewPager mViewPager;
	public static VisualActivity mVisualActivity;
	public static ArrayList<DataObject> dataList;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (mVisualActivity == null)
		{
			mVisualActivity = this;
		}
		
		
		super.onCreate(savedInstanceState);
		
		// Receive DataObject from MainActivity
		dataList = (ArrayList<DataObject>)getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);
		
		setContentView(R.layout.activity_visual);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visual, menu);
		return true;
	}
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}
	
	public View visualize(int arg)
	{
		double[] x = new double[dataList.size()];
		XYSeries series = null;
		XYSeriesRenderer r = null;
		
		for (int i = 0; i < dataList.size(); i++)
		{
			x[i] = i;
		}
		
		switch (arg) {
		case 1:
			series= new XYSeries("Speed");
			
			for (int i = 0; i < x.length; i++)
			{
				series.add(x[i], dataList.get(i).speed);
			}
			
			r = new XYSeriesRenderer();
			r.setColor(Color.BLUE);
			r.setPointStyle(PointStyle.POINT);
			
			break;
		case 2:
			series= new XYSeries("Battery Charge");
			
			for (int i = 0; i < x.length; i++)
			{
				series.add(x[i], dataList.get(i).batteryCurrent);
			}
			
			r = new XYSeriesRenderer();
			r.setColor(Color.RED);
			r.setPointStyle(PointStyle.POINT);
			
			break;
		case 3:
			series= new XYSeries("Power");
			
			for (int i = 0; i < x.length; i++)
			{
				series.add(x[i], dataList.get(i).arrayPower);
			}
			
			r = new XYSeriesRenderer();
			r.setColor(Color.CYAN);
			r.setPointStyle(PointStyle.POINT);
			
			break;
		case 4:
			series= new XYSeries("Motor Current");
			
			for (int i = 0; i < x.length; i++)
			{
				series.add(x[i], dataList.get(i).motorCurrent);
			}
			
			r = new XYSeriesRenderer();
			r.setColor(Color.GREEN);
			r.setPointStyle(PointStyle.POINT);
			
			break;
		case 5:
			series= new XYSeries("Battery Current");
			
			for (int i = 0; i < x.length; i++)
			{
				series.add(x[i], dataList.get(i).batteryCurrent);
			}
			
			r = new XYSeriesRenderer();
			r.setColor(Color.YELLOW);
			r.setPointStyle(PointStyle.POINT);
			
			break;
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);
		
		View chart = ChartFactory.getLineChartView(this, dataset, renderer);
		
		return chart;
	}
	
	public View visualizeTest()
	{
		double[] x = new double[]{1,3,5,7,9,11};
		double[] y = new double[]{3,14,5,30,20,25};
		
		XYSeries series = new XYSeries("Speed");
		
		for (int i = 0; i < x.length; i++)
		{
			series.add(x[i], y[i]);
		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		
		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);
		r.setPointStyle(PointStyle.CIRCLE);
		
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.addSeriesRenderer(r);
		
		View chart = ChartFactory.getLineChartView(this, dataset, renderer);
		//setContentView(chart);
		return chart;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			/*View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));*/
			
			int arg = getArguments().getInt(ARG_SECTION_NUMBER);
			
			return VisualActivity.mVisualActivity.visualize(arg);
			
			//return rootView;
		}
	}
}
