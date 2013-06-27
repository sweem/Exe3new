package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		TextView tvHeader = (TextView) findViewById(R.id.txtHeader);
		TextView tvHWD = (TextView) findViewById(R.id.txtHWD);
		TextView tvHSAT = (TextView) findViewById(R.id.txtHSAT);
		TextView tvHSUN = (TextView) findViewById(R.id.txtHSUN);
		TextView tvPN = (TextView) findViewById(R.id.txtPN);
		TextView tvA = (TextView) findViewById(R.id.txtA);
		TextView tvPCA = (TextView) findViewById(R.id.txtPCA);
		
		Bundle b = getIntent().getExtras();
		Pharmacy ph = new Pharmacy(b.getString("id"), b.getString("chName"), b.getString("phName"), b.getString("addr"), b.getString("pCode"), b.getString("pArea"), b.getString("pNbr"), b.getString("opHWD"), b.getString("clHWD"), b.getString("opHSAT"), b.getString("clHSAT"), b.getString("opHSUN"), b.getString("clHSUN"), b.getString("lat"), b.getString("lon"), b.getFloat("distToPh"));
		tvHeader.setText(ph.getPharmacyName());
		tvHWD.setText(ph.getOpeningHoursWD());
		tvHSAT.setText(ph.getOpeningHoursSAT());
		tvHSUN.setText(ph.getOpeningHoursSUN());
		tvPN.setText(ph.getPhoneNbr());
		tvA.setText(ph.getAddress());
		tvPCA.setText(ph.getPostalAC());
	}


	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}*/
	
}