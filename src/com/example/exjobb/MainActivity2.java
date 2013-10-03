package com.example.exjobb;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity2 extends Activity {
	private ListView lstView;
	//GeoPoint p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
		//actionBar.setSubtitle("Undermeny");
		actionBar.setTitle("Hitta din medicin");
		
		Choice choices[] = new Choice[] {
				new Choice(R.drawable.tablett_ikon, "Hitta läkemedel"), 
				new Choice(R.drawable.recept_ikon, "Mina recept"), 
				new Choice(R.drawable.apotek_ikon, "Hitta apotek")
				};
		ChoiceArrayAdapter adapter = new ChoiceArrayAdapter(this, R.layout.lstview_item_row, choices);
		
		lstView = (ListView) findViewById(R.id.lstView);
		lstView.setAdapter(adapter);
		
		lstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				//Toast.makeText(getBaseContext(), "Has pos " + pos, Toast.LENGTH_LONG).show();
				
				switch(pos) {
				case 0: startActivity(new Intent(MainActivity2.this, DrugsActivity.class));
						//finish();
						break;
				case 1: startActivity(new Intent(MainActivity2.this, LoginActivity.class));
						//finish();
						break;
				case 2: Intent i = new Intent(MainActivity2.this, PharmaciesActivity2.class);
						i.putExtra("phWithoutDr", true);
						startActivity(i);
						break;
				}
			}
		});
		
		//TextView  tv = (TextView) findViewById(R.id.txtHeader);
		//tv.setText("hehe");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*public void onListItemClick (ListView parent, View v, int pos, long id) {
		Choice item = (Choice) getListAdapter().getItem(pos);
		Toast.makeText(this, "You've selected " + item.title, Toast.LENGTH_SHORT).show();
	}*/
	
	/*public void onResume() {
		super.onResume();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		
		lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ll);
	}

	public void onPause() {
		lm.removeUpdates(ll);
	}*/

}
