package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class PharmaciesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pharmacies);
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
