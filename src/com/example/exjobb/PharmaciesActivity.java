package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PharmaciesActivity extends Activity {
	double dist;
	private ListView lstView;
	//double latA, lonA, latB, lonB;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharmacies2);
		
		Choice choices[] = new Choice[] {
				new Choice(R.drawable.apotek_ikon, "Apotek Svanen", "1,60 km", "08:00-18:00"), 
				new Choice(R.drawable.apotek_ikon, "Apotek Norringen", "1,70 km", "10:00-19:00"), 
				new Choice(R.drawable.apotek_ikon, "Apotek Lund", "2,30 km", "08:00-20:00")
				};
		
		
		PharmacyArrayAdapter adapter = new PharmacyArrayAdapter(this, R.layout.lstview_item_row2, choices);
		
		lstView = (ListView) findViewById(R.id.lstView);
		View header = (View) getLayoutInflater().inflate(R.layout.lstview_header_row2, null);
		View footer = (View) getLayoutInflater().inflate(R.layout.lstview_footer_row, null);
		
		lstView.addHeaderView(header);
		//lstView.addFooterView(footer);
		lstView.setAdapter(adapter);
		
		lstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Toast.makeText(getBaseContext(), "Has pos " + pos, Toast.LENGTH_LONG).show();
				
				/*switch(pos) {
				case 1: Toast.makeText(getBaseContext(), "You clicked on a item with pos " + pos ".", Toast.LENGTH_SHORT).show();
						finish();
						break;
				case 2: startActivity(new Intent(MainActivity.this, LoginActivity.class));
						finish();
						break;
				case 3: break;
				}*/
			}
		});
		
		dist = getDistFrom(55.70624, 13.19186, 55.711543, 13.209518);
		
		Toast.makeText(getBaseContext(), "Distance between two points: " + dist + "km.", Toast.LENGTH_LONG).show();
		
	}
	
	/* Haversine formula*/
	private static double getDistFrom(double lat1, double lon1, double lat2, double lon2) {
		double earthRad = 6371;
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		
		double sindLat = Math.sin(dLat/2);
		double sindLon = Math.sin(dLon/2);
		
		double a = Math.pow(sindLat, 2) + Math.pow(sindLon, 2) * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double retDist = earthRad * c;  
		
		return retDist;
	}

	public void onClickNext(View view) {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
	public void onClickBack(View view) {
		startActivity(new Intent(this, DrugsActivity.class));
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pharmacies, menu);
		return true;
	}

}
