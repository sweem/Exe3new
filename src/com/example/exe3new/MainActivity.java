package com.example.exe3new;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

/*
 * The MainActivity with three choices (buttons) - Hitta läkemedel, Mina recept and Hitta apotek.
 */

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("Hitta din medicin");
		
	}
	
	/*
	 * When clicked DrugsActivity is started.
	 */
	
	public void onClickDr(View view) {
		startActivity(new Intent(MainActivity.this, DrugsActivity.class));
	}
	
	/*
	 * When clicked LoginActivity is started.
	 */
	
	public void onClickPr(View view) {
		startActivity(new Intent(MainActivity.this, LoginActivity.class));
	}
	
	/*
	 * When clicked PharmaciesActivity2 is started and data is passed with an intent.
	 */
	
	public void onClickPh(View view) {
		Intent i = new Intent(MainActivity.this, PharmaciesActivity2.class);
		i.putExtra("phWithoutDr", true);
		startActivity(i);
	}
}
