package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChoosenDrugActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosen_drug);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choosen_drug, menu);
		return true;
	}

}
