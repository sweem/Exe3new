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
	String[] drugs;
	ArrayList<String> drugs2;
	String[] types;
	private ArrayList<String> types2;
	String[] volumes;
	String[] potency;
	String[] nbr;
	String choosenDrug;
	DBAdapter db;
	int currSel;
	ArrayAdapter<String> adapter2; //Onödigt?
	
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
       
        //---get all contacts---
        db.open();
        //Cursor c = db.getAllContacts();
        //Cursor c = db.getAllDrugs();
        //Cursor c = db.getAllDrugNames();
        //Toast.makeText(getBaseContext(), "antal rader " + c.getCount(), Toast.LENGTH_LONG).show();
        /*if (c.moveToFirst()) {
            do {
                //DisplayContact(c);
            	DisplayDrug(c);
            } while (c.moveToNext());
        }
        db.close();*/
		
		//drugs = getResources().getStringArray(R.array.drugs_array);
        //drugs = db.getAllDrugNames();
        drugs2 = db.getAllDrugNames(); //new ArrayList<String>();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, drugs2);
		AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.txtDrugs);		
		textView.setThreshold(2);
		textView.setAdapter(adapter);
		/*textView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				choosenDrug = (String) arg0.getItemAtPosition(arg2);
				//int index = (Integer)arg0.getItemAtPosition(arg2);
				Toast.makeText(getBaseContext(), "You've clicked item: " + choosenDrug, Toast.LENGTH_SHORT).show();
				adapter.clear();
				
			}
		});*/
		
		//types = getResources().getStringArray(R.array.type_array);
		types2 = new ArrayList<String>();
		//types2.add("Tablett");
		//types2.add("Kräm");
		Spinner sp1 = (Spinner) findViewById(R.id.spiType);
		final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types2);
		sp1.setAdapter(adapter2);
		adapter2.setNotifyOnChange(true);
				
		textView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				choosenDrug = (String) arg0.getItemAtPosition(arg2);
				//types2 = db.getAllTypes("Alvedon");
				//adapter2.add("Test test");
				adapter2.add("Kräm");
				//ArrayList<String> tmp = new ArrayList<String>();
				//types2 = db.getAllTypes("Alvedon");
				/*for(int i = 0; i < types2.size(); i++) {
					adapter2.add(types2.get(i));
				}*/
				//int index = (Integer)arg0.getItemAtPosition(arg2);
				Toast.makeText(getBaseContext(), "You've clicked item: " + choosenDrug, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		currSel = -1;
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(currSel != -1) {
					int index = arg0.getSelectedItemPosition();
					Toast.makeText(getBaseContext(), "You've selected item: " + types2.get(index), Toast.LENGTH_SHORT).show();
					//types = db.getAllTypes(choosenDrug);	
				}
				currSel++;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		potency = getResources().getStringArray(R.array.potency_array);
		Spinner sp2 = (Spinner) findViewById(R.id.spiPot);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, potency);
		sp2.setAdapter(adapter3);
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				//Toast.makeText(getBaseContext(), "You've selected item: " + potency[index], Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		volumes = getResources().getStringArray(R.array.volumes_array);
		Spinner sp4 = (Spinner) findViewById(R.id.spiVol);
		ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, volumes);
		sp4.setAdapter(adapter5);
		sp4.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				//Toast.makeText(getBaseContext(), "You've selected item: " + volumes[index], Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				//Toast.makeText(getBaseContext(), "You've selected item: " + potency[index], Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		nbr = getResources().getStringArray(R.array.nbr_array);
		Spinner sp3 = (Spinner) findViewById(R.id.spiNbr);
		//ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.nbr_array, R.layout.spinner_item_row);
		ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nbr);
		sp3.setAdapter(adapter4);
		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				int index = arg0.getSelectedItemPosition();
				//Toast.makeText(getBaseContext(), "You've selected item: " + nbr[index], Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		db.close();
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

    /*public void DisplayContact(Cursor c) {
        Toast.makeText(this,
        		"id: " + c.getString(0) + "\n" +
                "Name: " + c.getString(1) + "\n" +
                "Email:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }*/
    
    public void DisplayDrug(Cursor c) {
        /*Toast.makeText(this,
        		"Id: " + c.getString(0) + "\n" +
                "Drug_name: " + c.getString(1) + "\n" +
                "Type: " + c.getString(2) + "\n" +
                "Potency: " + c.getString(3) + "\n" +
                "Size: " + c.getShort(4) + "\n" +
                "Preferential price: " + c.getString(5) + "\n" +
                "Prescription only: " + c.getString(6),
                Toast.LENGTH_LONG).show();*/
        Toast.makeText(this,
                "Drug_name: " + c.getString(0),
                Toast.LENGTH_LONG).show();
    }
	
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
