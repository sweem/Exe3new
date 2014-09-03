package com.example.exe3new;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/*
 * PharmaciesAcitivity2 shows pharmacies that are either just open (in the tab open) or some open and some closed (in the tab all).  
 */

public class PharmaciesActivity2 extends FragmentActivity implements ActionBar.TabListener {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharmacies2);
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Funna apotek");
		actionBar.setTitle("Hitta din medicin");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	/*
	 * When the given tab is selected, switch to the corresponding page in the ViewPager.
	 */
	
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
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
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A fragment representing a section of the app, but that simply displays pharmacies.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";	
		private ListView lstView;
		private TextView txtView;
		private DBAdapter db;
		private String choosenDrugID;
		private int nbrOfDrug;
		
		private LocationManager lm;
		private LocationListener ll;
		private double latitude;
		private double longitude;
		private boolean phWithoutDr, noOpenPh;
		private Calendar cal;
		
		private CopyDB copyDB;
		
		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_pharmacies22, container, false);
			return rootView;
		}
		
		/*
		 * Called when the activity's onCreate() method has been returned. Gets information from the database with the helper class
		 * DBAdapter and fills the tabs with it depending on where is is called from (phWithoutDr) - directly from MainActivity or 
		 * ChoosenDrugActivity, the users current position and day.
		 */
		
		public void onActivityCreated (Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
			ll = new MyLocationListener();
			noOpenPh = false;
			cal = Calendar.getInstance();
			
			cal.set(Calendar.DAY_OF_WEEK, 3); //Change current day
	     	
	    	cal.set(Calendar.HOUR_OF_DAY, 9); //Change current time
	     	cal.set(Calendar.MINUTE, 0);
	     	cal.set(Calendar.SECOND, 0);
	     	cal.set(Calendar.MILLISECOND, 0);
			
			Bundle b = getActivity().getIntent().getExtras();
			phWithoutDr = b.getBoolean("phWithoutDr");
			
			if(phWithoutDr == false) {
				//Log.e("PhWithoutDrug in F", "False");
				choosenDrugID = b.getString("drugID");
				nbrOfDrug = b.getInt("nbrOfDrug");
			} /*else {
				Log.e("PhWithoutDrug in F", "True");
			}*/
			
			db = new DBAdapter(getActivity());
			copyDB = new CopyDB(getActivity().getAssets(), getActivity().getPackageName());
			copyDB.tryCopyDB();
	       
	        db.open();
			
			final ArrayList<Pharmacy> arr;
			PharmacyArrayAdapter adapter;
	        final Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                
	        String section = Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER));
	        
	        if(phWithoutDr == false) { //Without drugID
	        	//Log.e("Without drugID", "False");
	        	ArrayList<Pharmacy> pids = db.getAllPharmacyIdWithDrugId(choosenDrugID, nbrOfDrug); //Finds all pharmacyid and nbr of drug with drugid
	        	
	        	if(pids.size() == 0) { //Drug couldn't be found - Out of stock or too few items in stock
	        		pids = db.getAllPharmacyIdWithDrugId(choosenDrugID, 1);
	        		if(pids.size() == 0) { //Drug out of stock
	        			//Log.e("Drug out of stock. ", "" + pids.size());
	        			pids = db.getAllPharmacyIdWithDrugId(choosenDrugID, 0);
	        			
	        			if(section.equals("1")) {
		        			Fragment2 dialogFragment = Fragment2.newInstance("Inga läkemedel tillgängliga", "Kontakta ett apotek för att beställa.");
		        			dialogFragment.show(getActivity().getFragmentManager(), "dialog");
	        			}
	        		 }
	        		else { //To few items of that drug in stock
	        			//Log.e("Too few items in stock. ", "" + pids.size());
	        			if(section.equals("1")) {
	        				Fragment2 dialogFragment = Fragment2.newInstance("Få läkemedel tillgängliga", "Kontakta ett apotek för att beställa fler.");
	        				dialogFragment.show(getActivity().getFragmentManager(), "dialog");
	        			}
	        		}
	        	}
	        	
	        	if(section.equals("1")) {
	        		arr = db.getPharmaciesWithDrugId(choosenDrugID, nbrOfDrug, pids, loc, false, cal);
	        	} else {
	        		arr = db.getPharmaciesWithDrugId(choosenDrugID, nbrOfDrug, pids, loc, true, cal);
	        		
	        		if(arr.isEmpty() == true) {
	        			//Log.e("Arr is empty", "True");
	        			/*Fragment2 dialogFragment = Fragment2.newInstance("Inga öppna apotek", "Kontrollera apotekens öppettider.");
        				dialogFragment.show(getActivity().getFragmentManager(), "dialog");*/
	        			noOpenPh = true;
	        		}
	        	}
	        	
	        	adapter = new PharmacyArrayAdapter(getActivity(), R.layout.lstview_item_rowwd, arr, cal);
	        }
	        else { //With drugID
	        	//Log.e("Without drugID/in sec", "True/" + section);
	        	if(section.equals("1")) {
	        		arr = db.getPharmaciesWithoutDrugId(loc, false, cal);
	        	} else {
	        		arr = db.getPharmaciesWithoutDrugId(loc, true, cal);
	        		
	        		if(arr.isEmpty() == true) {
	        			//Log.e("Arr is empty", "True");
	        			/*Fragment2 dialogFragment = Fragment2.newInstance("Inga öppna apotek", "Kontrollera apotekens öppettider.");
        				dialogFragment.show(getActivity().getFragmentManager(), "dialog");*/
	        			noOpenPh = true;
	        		}
	        	}
	        	
	        	adapter = new PharmacyArrayAdapter(getActivity(), R.layout.lstview_item_rowwod, arr, cal);
	        }
			
	        db.close();
	        
        	txtView = (TextView) getView().findViewById(R.id.txtView);
	        
	        if(noOpenPh == true) {
	        	//Log.e("No open ph/in sec", "True/" + section);
    			Fragment2 dialogFragment = Fragment2.newInstance("Inga öppna apotek", "Kontrollera apotekens öppettider.");
				dialogFragment.show(getActivity().getFragmentManager(), "dialog");
	        	//txtView = (TextView) getView().findViewById(R.id.txtView);
	        } else {
	        	//Log.e("No open ph/in sec", "False/" + section);
				lstView = (ListView) getView().findViewById(R.id.lstView);
	        	//txtView = (TextView) getView().findViewById(R.id.txtView);
	        	lstView.setEmptyView(txtView);
				lstView.setAdapter(adapter);
				
				lstView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						//Toast.makeText(getActivity(), "You clicked on a item with pos " + arg2 + ".", Toast.LENGTH_SHORT).show();
						Pharmacy ph = arr.get(arg2);
				        longitude = loc.getLongitude();
				        latitude = loc.getLatitude();
						Intent i = new Intent(getActivity(), DetailsActivity.class);
						i.putExtra("id", ph.getId()); //ph.id
						i.putExtra("curLat", latitude);
						i.putExtra("curLon", longitude);
						i.putExtra("curDay", cal.get(Calendar.DAY_OF_WEEK)); //time.getCurrentDay()
						startActivity(i);
					}
				});
	        }
		}
		
		public void onResume() {
			super.onResume();
			
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
		}
		
		public void onPause() {
			super.onPause();
			
			lm.removeUpdates(ll);
		}
		
		private class MyLocationListener implements LocationListener {
			@Override
			public void onLocationChanged(Location loc) {
				if(loc != null) {
					latitude = loc.getLatitude();
					//Log.e("Lat: ", Double.toString(latitude));
					longitude = loc.getLongitude();
					//Log.e("Lon: ", Double.toString(longitude));
				}
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
			}	
		}
	}

	/*
	 * Is performed when OK-button is clicked in the Fragment.
	 */
	
	public void doPositiveClick() {
		//Log.d("PharmacieActivity2", "User clicks on OK");
	}
}
