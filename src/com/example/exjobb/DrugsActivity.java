package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class DrugsActivity extends Activity implements OnItemSelectedListener {
	ArrayList<String> drugs;
	ArrayList<String> types;
	ArrayList<String> strengths;
	ArrayList<String> volumes;
	ArrayList<Integer> nbr;
	ArrayAdapter<String> typAdapter;
	ArrayAdapter<String> strAdapter;
	ArrayAdapter<String> volAdapter;
	ArrayAdapter<Integer> nbrAdapter;
	Spinner typSpinner, strSpinner, volSpinner, nbrSpinner;
	String choosenDru, choosenTyp, choosenStr, choosenVol;
	int choosenNbr;
	String choosenDrugID;
	DBAdapter db;
	int currSelT, currSelS, currSelV, currSelN;
	boolean drugOutOfStock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drugs);
		
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
        
        drugs = db.getAllDrugNames();
		final ArrayAdapter<String> druAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, drugs);
		AutoCompleteTextView druTV = (AutoCompleteTextView) findViewById(R.id.txtDrugs);		
		druTV.setThreshold(2);
		druTV.setAdapter(druAdapter);
		
		types = new ArrayList<String>();
		typSpinner = (Spinner) findViewById(R.id.spiType);
		typAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
		typSpinner.setAdapter(typAdapter);
		typAdapter.setNotifyOnChange(true);
		
		strengths = new ArrayList<String>();
		strSpinner = (Spinner) findViewById(R.id.spiPot);
		strAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strengths);
		strSpinner.setAdapter(strAdapter);
		strAdapter.setNotifyOnChange(true);
		
		volumes = new ArrayList<String>();
		volSpinner = (Spinner) findViewById(R.id.spiVol);
		volAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, volumes);
		volSpinner.setAdapter(volAdapter);
		volAdapter.setNotifyOnChange(true);
		
		nbr = new ArrayList<Integer>();
		nbrSpinner = (Spinner) findViewById(R.id.spiNbr);
		nbrAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, nbr);
		nbrSpinner.setAdapter(nbrAdapter);
		nbrAdapter.setNotifyOnChange(true);
	
		druTV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				db.open();
				choosenDru = (String) arg0.getItemAtPosition(arg2);
				types = db.getAllTypes(choosenDru);
				typAdapter.clear();
				strAdapter.clear();
				volAdapter.clear();
				nbrAdapter.clear();
				typAdapter.addAll(types);
				//Toast.makeText(getBaseContext(), "Filled typeSpinner", Toast.LENGTH_SHORT).show();
				
				choosenTyp = types.get(0);
				strengths = db.getAllStrengths(choosenDru, choosenTyp);
				strAdapter.clear();
				volAdapter.clear();
				nbrAdapter.clear();
				strAdapter.addAll(strengths);
				//Toast.makeText(getBaseContext(), "Filled potencySpinner", Toast.LENGTH_SHORT).show();
				
				choosenStr = strengths.get(0);
				volumes = db.getAllSizes(choosenDru, choosenTyp, choosenStr);
				volAdapter.clear();			
				nbrAdapter.clear();
				volAdapter.addAll(volumes);
				//Toast.makeText(getBaseContext(), "Filled volumeSpinner", Toast.LENGTH_SHORT).show();
				
				choosenVol = volumes.get(0);
				nbr = fillArrayWithNbrs();
				nbrAdapter.clear();
				nbrAdapter.addAll(nbr);
				//Toast.makeText(getBaseContext(), "Filled nbrSpinner", Toast.LENGTH_SHORT).show();
				
				choosenNbr = nbr.get(0);
				choosenDrugID = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);
				db.close();
			}
		});
		
		typSpinner.setOnItemSelectedListener(this);
		strSpinner.setOnItemSelectedListener(this);
		volSpinner.setOnItemSelectedListener(this);
		nbrSpinner.setOnItemSelectedListener(this);		
		db.close();
	}
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		db.open();
		int spiId = arg0.getId();
		int posObj = arg0.getSelectedItemPosition();
		
		switch(spiId) {
			case R.id.spiType:	//Toast.makeText(getBaseContext(), "You clicked on typeSpinner", Toast.LENGTH_SHORT).show();
								choosenTyp = types.get(posObj);
								strengths = db.getAllStrengths(choosenDru, choosenTyp);
								strAdapter.clear();
								volAdapter.clear();
								nbrAdapter.clear();
								strAdapter.addAll(strengths);
								strSpinner.setSelection(0);
								
								choosenStr = strengths.get(0);
								volumes = db.getAllSizes(choosenDru, choosenTyp, choosenStr);
								volAdapter.clear();			
								nbrAdapter.clear();
								volAdapter.addAll(volumes);
								volSpinner.setSelection(0);
								
								choosenVol = volumes.get(0);
								nbr = fillArrayWithNbrs();
								nbrAdapter.clear();
								nbrAdapter.addAll(nbr);
								nbrSpinner.setSelection(0);
								
								choosenNbr = nbr.get(0);
								choosenDrugID = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);								
								break;
			case R.id.spiPot: 	//Toast.makeText(getBaseContext(), "You clicked on potencySpinner", Toast.LENGTH_SHORT).show();
								choosenStr = strengths.get(posObj);
								volumes = db.getAllSizes(choosenDru, choosenTyp, choosenStr);
								volAdapter.clear();			
								nbrAdapter.clear();
								volAdapter.addAll(volumes);
								volSpinner.setSelection(0);
								
								choosenVol = volumes.get(0);
								nbr = fillArrayWithNbrs();
								nbrAdapter.clear();
								nbrAdapter.addAll(nbr);
								nbrSpinner.setSelection(0);
								
								choosenNbr = nbr.get(0);
								choosenDrugID = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);
								break;
			case R.id.spiVol: 	//Toast.makeText(getBaseContext(), "You clicked on volumeSpinner", Toast.LENGTH_SHORT).show();
								choosenVol = volumes.get(posObj);
								nbr = fillArrayWithNbrs();
								nbrAdapter.clear();
								nbrAdapter.addAll(nbr);
								nbrSpinner.setSelection(0);
								
								choosenNbr = nbr.get(0);
								choosenDrugID = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);
								break;
			case R.id.spiNbr: 	//Toast.makeText(getBaseContext(), "You clicked on numberSpinner", Toast.LENGTH_SHORT).show();
								choosenNbr = nbr.get(posObj);
								choosenDrugID = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);
								break;
		}
		db.close();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
    protected ArrayList<Integer> fillArrayWithNbrs() {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		
		for(int i = 0; i < 5; i++) {
			tmp.add(i+1);
		}
		return tmp;
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
	
	public void onClickNext(View view) {
		db.open();
		Intent i = new Intent(this, PharmaciesActivity2.class);
		/*choosenDrugID = "9"; //Which drug to search for
		choosenNbr = 1;*/ //Nbr of search drug
		i.putExtra("drugID", choosenDrugID);
		i.putExtra("nbrOfDrug", choosenNbr);
		i.putExtra("PhWithoutDr", false);
		ArrayList<Pharmacy> pids = db.getAllPharmacyIdWithDrugId2(choosenDrugID, choosenNbr); //Finds all pharmacyid and nbr of that drug with drugid and nbrOfDrug
		
		if(pids.size() == 0) { //Drug couldn't be found - Out of stock or too few items in stock
    		pids = db.getAllPharmacyIdWithDrugId2(choosenDrugID, 1);
    		if(pids.size() == 0) { //Drug out of stock
    			Log.e("Drug out of stock. ", "" + pids.size());
    			Fragment1 dialogFragment = Fragment1.newInstance("Inga läkemedel tillgängliga", "Kontakta det närmaste apoteket för att beställa.");
    			dialogFragment.show(getFragmentManager(), "dialog");
    			drugOutOfStock = true;
    			
    		 }
    		else { //Too few items of drug in stock
    			Log.e("Too few items in stock. ", "" + pids.size());
    			Fragment1 dialogFragment = Fragment1.newInstance("Få läkemedel tillgängliga", "Kontakta ett apoteket för att beställa fler.");
    			dialogFragment.show(getFragmentManager(), "dialog");
        		drugOutOfStock = false;
    		}
    	}
		else {
			startActivity(i);
		}
		
		//Log.e("drugID/nbrOfDrug", choosenDrugID + "/" + choosenNbr);
		//startActivity(new Intent(this, PharmaciesActivity.class));
		
		/*startActivity(i);*/
		
		//finish();
		db.close();
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drugs, menu);
		return true;
	}*/
	
	public void doPositiveClick() {
		Log.d("DrugsActivity", "User clicks on OK");
		Intent i = new Intent(this, PharmaciesActivity2.class);
		if(drugOutOfStock == false) {
			/*choosenDrugID = "9"; //Which drug to search for
			choosenNbr = 1;*/ //Nbr of search drug
			i.putExtra("drugID", choosenDrugID);
			i.putExtra("nbrOfDrug", choosenNbr);
			i.putExtra("PhWithoutDr", false);
			startActivity(i);
		}
		else {
			i.putExtra("PhWithoutDr", true);
			startActivity(i);
		}
	}
}
