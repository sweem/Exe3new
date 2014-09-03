package com.example.exe3new;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

/*
 * The DrugsActivity gets attributes about the selected drug from the database with
 * the helper class DBADApter.
 */

public class DrugsActivity extends Activity implements OnItemSelectedListener {
	private ArrayList<String> drugs;
	private ArrayList<String> types;
	private ArrayList<String> strengths;
	private ArrayList<String> volumes;
	private ArrayList<Integer> nbr;
	private ArrayAdapter<String> typAdapter;
	private ArrayAdapter<String> strAdapter;
	private ArrayAdapter<String> volAdapter;
	private ArrayAdapter<Integer> nbrAdapter;
	private Spinner typSpinner, strSpinner, volSpinner, nbrSpinner;
	private String choosenDru, choosenTyp, choosenStr, choosenVol;
	private int choosenNbr;
	private String choosenDrugID;
	private DBAdapter db;
	private int currSelT, currSelS, currSelV, currSelN;
	private boolean drugOutOfStock;
	private AutoCompleteTextView druTV;
	private CopyDB copyDB;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drugs);
		
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Välj läkemedel");
		actionBar.setTitle("Hitta din medicin");
		
		db = new DBAdapter(this);
		copyDB = new CopyDB(getBaseContext().getAssets(), getPackageName());
		copyDB.tryCopyDB();
       
        db.open();
        
        drugs = db.getAllDrugNames();
		final ArrayAdapter<String> druAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, drugs);
		druTV = (AutoCompleteTextView) findViewById(R.id.txtDrugs);
		druTV.setThreshold(1);
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
		
		druTV.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {				
				if((count != before) && choosenDru != null) {
					//Log.e("Drug doesn't exist", "True");
					typAdapter.clear();
					strAdapter.clear();
					volAdapter.clear();
					nbrAdapter.clear();
					choosenDrugID = null;
					choosenNbr = 0;
				}
			}
		});
		
		db.close();
	}
	
	/*
	 * Handles the selection events for the spinners when attribute is selected. Fills them with attributes and empties them when needed. 
	 */
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		db.open();
		int spiId = arg0.getId();
		int posObj = arg0.getSelectedItemPosition();
		
		switch(spiId) {
			case R.id.spiType:	
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

	/*
	 * Handles the selection events for the spinners when selection disappears from the attribute. 
	 */
	
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
	
	/*
	 * When the next button is clicked ChoosedDrugActivity with extra data is started if a drug has been choosen.
	 */
	
	public void onClickNext(View view) {
		db.open();
		
		if (choosenDrugID != null && choosenNbr > 0) {
			Intent i = new Intent(this, ChoosenDrugActivity.class);
			i.putExtra("drugID", choosenDrugID);
			i.putExtra("nbrOfDrug", choosenNbr);
			i.putExtra("phWithoutDr", false);
			startActivity(i);
		} else {
			if(druTV.length() == 0) { //AutoCompleteTextView is empty
				Fragment1 dialogFragment = Fragment1.newInstance("Välj läkemedel", "Inget valt läkemedel.");
				dialogFragment.show(getFragmentManager(), "dialog");
			} else { //Invalid drugName
				Fragment1 dialogFragment = Fragment1.newInstance("Välj läkemedel", "Valt läkemedel finns ej.");
				dialogFragment.show(getFragmentManager(), "dialog");
			}
		}
		
		db.close();
	}
	
	/*
	 * Is performed when OK-button is clicked in the Fragment.
	 */
	
	public void doPositiveClick() {
		//Log.d("DrugsActivity", "User clicks on OK");
	}
}
