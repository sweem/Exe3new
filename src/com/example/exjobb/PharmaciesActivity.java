package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PharmaciesActivity extends Activity {
	double dist;
	private ListView lstView;
	DBAdapter db;
	String choosenDrugID;
	int nbrOfDrug;
	
	LocationManager lm;
	LocationListener ll;
	double latitude;
	double longitude;
	boolean phWithoutDr;
	//double latA, lonA, latB, lonB;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharmacies);
		
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
		
		Bundle b = getIntent().getExtras();
		phWithoutDr = b.getBoolean("PhWithoutDr");
		if(phWithoutDr == false) {
			//Log.e("PhWithoutDrug", "False");
			choosenDrugID = b.getString("drugID");
			nbrOfDrug = b.getInt("nbrOfDrug");
		}		
		
		db = new DBAdapter(this);
        try {
            String destPath = "/data/data/" + getPackageName() +
                "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("mydb"),
                    new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        db.open();
        final ArrayList<Pharmacy> arr;
        final Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //Toast.makeText(getBaseContext(), "Lat: " + loc.getLatitude() + " and lon: " + loc.getLongitude(), Toast.LENGTH_LONG).show();
        //final ArrayList<Pharmacy> arr = db.getAllPharmaciesWithDrugId(choosenDrugID, nbrOfDrug, loc, phWithoutDr);
        
        if(phWithoutDr == false) {
        	//Log.e("PhWithourDr time for sql", "false");
        	arr = db.getPharmaciesWithDrugId(choosenDrugID, nbrOfDrug, loc, true);
        }
        else {
        	//Log.e("PhWithourDr time for sql", "true");
        	arr = db.getPharmaciesWithoutDrugId(loc, true);
        }
        
        //Toast.makeText(getBaseContext(), "You've choosen " + nbrOfDrug + " of a drug with id " + choosenDrugID, Toast.LENGTH_LONG).show();
        int dayInWeek = db.getCurrentDay();
        String day;
        
        if (dayInWeek == 1) {
        	day = "Sunday";
        }
        else if(dayInWeek == 7) {
        	day = "Saturday";
        }
        else {
        	day = "Weekday";
        }
        //Toast.makeText(getBaseContext(), "It's " + day + " that is day in week " + dayInWeek + " and the time is " + db.getCurrentTime(), Toast.LENGTH_LONG).show();
        /*for(int i = 0; i < arr.size(); i++) {
        	Toast.makeText(getBaseContext(), "Pharmacy " + arr.get(i).id + " has drug in stock. ", Toast.LENGTH_LONG).show();
        }*/
        
        db.close();
        
		PharmacyArrayAdapter adapter = new PharmacyArrayAdapter(this, R.layout.lstview_item_row2, arr);
        lstView = (ListView) findViewById(R.id.lstView);
		View header = (View) getLayoutInflater().inflate(R.layout.lstview_header_row2, null);
		//View footer = (View) getLayoutInflater().inflate(R.layout.lstview_footer_row, null);
		
		lstView.addHeaderView(header);
		//lstView.addFooterView(footer);
		lstView.setAdapter(adapter);
		
		lstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				//Toast.makeText(getBaseContext(), "Has pos " + pos, Toast.LENGTH_LONG).show();
				//Toast.makeText(getBaseContext(), "You clicked on a item with pos " + pos + ".", Toast.LENGTH_SHORT).show();
				Pharmacy ph = arr.get(pos-1);
		        longitude = loc.getLongitude();
		        latitude = loc.getLatitude();
		        //Log.e("Ph ID/Lat/Lon", id + "/" + latitude + "/" + longitude);
				Intent i = new Intent(PharmaciesActivity.this, DetailsActivity.class);
				i.putExtra("id", ph.id);
				i.putExtra("curLat", latitude);
				i.putExtra("curLon", longitude);
				startActivity(i);
				
				/*switch(pos) {
				case 1: Toast.makeText(getBaseContext(), "You clicked on a item with pos " + pos ".", Toast.LENGTH_SHORT).show();
						//finish();
						break;
				case 2: startActivity(new Intent(MainActivity.this, LoginActivity.class));
						//finish();
						break;
				case 3: break;
				}*/
			}
		});
		
		/*dist = getDistFrom(55.70624, 13.19186, 55.711543, 13.209518);
		Toast.makeText(getBaseContext(), "Distance between two points: " + dist + "km.", Toast.LENGTH_LONG).show();*/
		
	}
	
	
	public void CopyDB(InputStream inputStream, 
		    OutputStream outputStream) throws IOException {
		        //---copy 1K bytes at a time---
		        byte[] buffer = new byte[1024];
		        int length;
		        while ((length = inputStream.read(buffer)) > 0) {
		            outputStream.write(buffer, 0, length);
		        }
		        inputStream.close();
		        outputStream.close();
	}
	
	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			if(loc != null) {
				latitude = loc.getLatitude();
				longitude = loc.getLongitude();
				//Toast.makeText(getBaseContext(), "Location changed. Lat is now " + loc.getLatitude() + " and lon is " + loc.getLongitude() + ".", Toast.LENGTH_LONG).show();
				//String myLocation = "Lat: " + loc.getLatitude() + " and lon: " + loc.getLongitude() + ".";
				//p = new GeoPoint((int) (loc.getLatitude() * 1E6), (int) (loc.getLongitude() * 1E6));
				//Log.e("My current location", myLocation);
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

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pharmacies, menu);
		return true;
	}*/

}
