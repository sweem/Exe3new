package com.example.exjobb;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ActionBar actionBar = getActionBar();
		//actionBar.setSubtitle("Undermeny");
		actionBar.setTitle("Hitta din medicin");
		
		Button btn1 = (Button) findViewById(R.id.btn1);
		btn1.setBackgroundColor(Color.WHITE);
		Button btn2 = (Button) findViewById(R.id.btn2);
		btn2.setBackgroundColor(Color.WHITE);
		Button btn3 = (Button) findViewById(R.id.btn3);
		btn3.setBackgroundColor(Color.WHITE);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickDr(View view) {
		startActivity(new Intent(MainActivity.this, DrugsActivity.class));
	}
	
	public void onClickPr(View view) {
		startActivity(new Intent(MainActivity.this, LoginActivity.class));
	}
	
	public void onClickPh(View view) {
		Intent i = new Intent(MainActivity.this, PharmaciesActivity2.class);
		i.putExtra("phWithoutDr", true);
		startActivity(i);
	}
}
