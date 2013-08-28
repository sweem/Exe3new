package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ChoosenDrugActivity extends Activity {
	String choosenDrugID, choosenNbr;
	Boolean phWithoutDr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosen_drug);
		
		Bundle b = getIntent().getExtras();
		choosenDrugID = b.getString("drugID");
		choosenNbr = b.getString("nbrOfDrug");
		phWithoutDr = b.getBoolean("PhWithoutDr");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choosen_drug, menu);
		return true;
	}

	public void onClickNext(View view) {
		Intent i = new Intent(this, PharmaciesActivity2.class);
		/*choosenDrugID = "9"; //Which drug to search for
		choosenNbr = 1;*/ //Nbr of search drug
		i.putExtra("drugID", choosenDrugID);
		i.putExtra("nbrOfDrug", choosenNbr);
		i.putExtra("phWithoutDr", false);
		startActivity(i);
		finish();
	}
}
