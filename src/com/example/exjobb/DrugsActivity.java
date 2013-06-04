package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

public class DrugsActivity extends Activity {
	ArrayList<String> drugs;
	ArrayList<String> types;
	ArrayList<String> strengths;
	ArrayList<String> volumes;
	ArrayList<Integer> nbr;
	String choosenDru, choosenTyp, choosenStr, choosenVol;
	int choosenNbr;
	String choosenDrugId;
	DBAdapter db;
	int currSelT, currSelS, currSelV, currSelN;
	
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
        
        drugs = db.getAllDrugNames(); //new ArrayList<String>();
		final ArrayAdapter<String> druAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, drugs);
		AutoCompleteTextView druTV = (AutoCompleteTextView) findViewById(R.id.txtDrugs);		
		druTV.setThreshold(2);
		druTV.setAdapter(druAdapter);
		
		types = new ArrayList<String>();
		Spinner typSpinner = (Spinner) findViewById(R.id.spiType);
		final ArrayAdapter<String> typAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
		typSpinner.setAdapter(typAdapter);
		typAdapter.setNotifyOnChange(true);
		currSelT = -1;
		
		strengths = new ArrayList<String>(); //getResources().getStringArray(R.array.potency_array);
		Spinner strSpinner = (Spinner) findViewById(R.id.spiPot);
		final ArrayAdapter<String> strAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strengths);
		strSpinner.setAdapter(strAdapter);
		strAdapter.setNotifyOnChange(true);
		currSelS = -1;
		
		volumes = new ArrayList<String>(); //getResources().getStringArray(R.array.volumes_array);
		Spinner volSpinner = (Spinner) findViewById(R.id.spiVol);
		final ArrayAdapter<String> volAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, volumes);
		volSpinner.setAdapter(volAdapter);
		volAdapter.setNotifyOnChange(true);
		currSelV = -1;
		
		nbr = new ArrayList<Integer>(); //getResources().getStringArray(R.array.nbr_array);
		Spinner nbrSpinner = (Spinner) findViewById(R.id.spiNbr);
		final ArrayAdapter<Integer> nbrAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, nbr);
		nbrSpinner.setAdapter(nbrAdapter);
		nbrAdapter.setNotifyOnChange(true);
		currSelN = -1;
	
		druTV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				db.open();
				choosenDru = (String) arg0.getItemAtPosition(arg2);
				types = db.getAllTypes(choosenDru);
				
				//if(typesA.getCount() > 0) {
				//Toast.makeText(getBaseContext(), "TypesA has " + typesA.getCount() + " objects. Emptying typesA.", Toast.LENGTH_SHORT).show();
				typAdapter.clear();
				//}
				
				//if(strengthsA.getCount() > 0) {
				//Toast.makeText(getBaseContext(), "StrengthsA has " + strengthsA.getCount() + " objects. Emptying strengthsA.", Toast.LENGTH_SHORT).show();
				strAdapter.clear();
				//}
				
				//Toast.makeText(getBaseContext(), "VolumesA has " + volumesA.getCount() + " objects. Emptying volumesA.", Toast.LENGTH_SHORT).show();
				volAdapter.clear();
				
				typAdapter.addAll(types);
				
				//Toast.makeText(getBaseContext(), "You've clicked item: " + choosenDrug, Toast.LENGTH_SHORT).show();
				db.close();
			}
		});

		typSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				db.open();
				Toast.makeText(getBaseContext(), "CurrSelT is " + currSelT, Toast.LENGTH_SHORT).show();
				if(currSelT != -1 || typAdapter.getCount() == 1) {
					//Toast.makeText(getBaseContext(), "CurrSelT is " + currSelT, Toast.LENGTH_SHORT).show();
					int index = arg0.getSelectedItemPosition();
					choosenTyp = types.get(index);
					strengths = db.getAllStrengths(choosenDru, choosenTyp);
					
					//if(strengthsA.getCount() > 0)
					strAdapter.clear();
					volAdapter.clear();
					nbrAdapter.clear();
					
					strAdapter.addAll(strengths);
					Toast.makeText(getBaseContext(), "CurrSelS is " + currSelS, Toast.LENGTH_SHORT).show();
					
					//Toast.makeText(getBaseContext(), "You've choosen type: " + choosenTyp + " for the drug: " + choosenDru, Toast.LENGTH_SHORT).show();
				}
				currSelT++;
				db.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		strSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				db.open();
				Toast.makeText(getBaseContext(), "CurrSelS is " + currSelS, Toast.LENGTH_SHORT).show();
				if(currSelS != -1 || strAdapter.getCount() == 1) {
					//Toast.makeText(getBaseContext(), "CurrSelS != -1", Toast.LENGTH_SHORT).show();
					int index = arg0.getSelectedItemPosition();
					choosenStr = strengths.get(index);
					volumes = db.getAllSizes(choosenDru, choosenTyp, choosenStr);
					
					volAdapter.clear();
					nbrAdapter.clear();
					
					volAdapter.addAll(volumes);
					//Toast.makeText(getBaseContext(), "You've choosen strength: " + choosenStr + " for the drug " + choosenDru + " with type " + choosenTyp, Toast.LENGTH_LONG).show();
				}
				currSelS++;
				db.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		volSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				db.open();
				Toast.makeText(getBaseContext(), "CurrSelV is " + currSelV, Toast.LENGTH_SHORT).show();
				if(currSelV != -1 || volAdapter.getCount() == 1) {
					//Toast.makeText(getBaseContext(), "CurrSelV != -1", Toast.LENGTH_SHORT).show();
					int index = arg0.getSelectedItemPosition();
					choosenVol = volumes.get(index);
					nbr = fillArrayWithNbrs();
					
					nbrAdapter.clear();
					
					nbrAdapter.addAll(nbr);
					//Toast.makeText(getBaseContext(), "You've selected item: " + volumes[index], Toast.LENGTH_SHORT).show();
				}
				currSelV++;
				db.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		nbrSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				db.open();
				//Toast.makeText(getBaseContext(), "CurrSelN is " + currSelN, Toast.LENGTH_SHORT).show();
				if(currSelN != -1) {
					//Toast.makeText(getBaseContext(), "CurrSelN is " + currSelN, Toast.LENGTH_SHORT).show();
					int index = arg0.getSelectedItemPosition();
					choosenNbr = nbr.get(index);
					choosenDrugId = db.getDrugRowId(choosenDru, choosenTyp, choosenStr, choosenVol);
					
					//Toast.makeText(getBaseContext(), "You've selected rowid: " + choosenDrugId, Toast.LENGTH_SHORT).show();
				}
				currSelN++;
				db.close();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		db.close();
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
    
    /*public void DisplayDrug(Cursor c) {
        Toast.makeText(this, "Drug_name: " + c.getString(0), Toast.LENGTH_LONG).show();
    }*/
	
	public void onClickNext(View view) {
		startActivity(new Intent(this, PharmaciesActivity.class));
		finish();
	}
	
	public void onClickBack(View view) {
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drugs, menu);
		return true;
	}*/

}
