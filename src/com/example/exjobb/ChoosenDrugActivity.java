package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ChoosenDrugActivity extends Activity {
	String choosenDrugID, choosenNbr;
	Boolean phWithoutDr;
	DBAdapter db;
	Cursor drug;
	String dName, typ, pot, siz, pref, pres, man, sub, pak;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choosen_drug);
		
		TextView txtDrug = (TextView) findViewById(R.id.txtDrugName);
		TextView txtMan = (TextView) findViewById(R.id.txtManuf);
		TextView txtPakTyPoSi = (TextView) findViewById(R.id.txtTyPoSi);
		TextView txtSub = (TextView) findViewById(R.id.txtSub);
		TextView txtPrefPr = (TextView) findViewById(R.id.txtPrice);
		TextView txtPres = (TextView) findViewById(R.id.txtPreOnly);
		
		Bundle b = getIntent().getExtras();
		choosenDrugID = b.getString("drugID");
		choosenNbr = b.getString("nbrOfDrug");
		phWithoutDr = b.getBoolean("PhWithoutDr");
		
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Valt l�kemedel");
		actionBar.setTitle("Hitta din medicin");
		
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
		drug = db.getDrug(choosenDrugID);
		
		int i = 0;
    	if (drug.moveToFirst()) {
            do {
            	//Log.e("Column " + i, drug.getString(i));
            	dName = drug.getString(1);
            	typ = drug.getString(2);
            	pot = drug.getString(3);
            	siz = drug.getString(4);
            	pref = drug.getString(5);
            	pres = drug.getString(6);
            	man = drug.getString(7);
            	sub = drug.getString(8);
            	pak = drug.getString(9);
            	i++;
            } while (drug.moveToNext());
        }
    	
    	drug.close();
    	db.close();
    	
    	txtDrug.setText(dName);
    	txtMan.setText(man);
    	txtPakTyPoSi.setText(typ + " " + pot + ", " + siz + ", " + pak);
    	
    	if(pref.equals("Nej")) {
    		txtPrefPr.setText("Ej f�rm�n");
    	}
    	else {
    		txtPrefPr.setText(pref);
    	}
    	
    	txtSub.setText(sub);
    	
    	if(pres.equals("Ja")) {
    		txtPres.setText("Receptbelagd");
    	}
    	else {
    		txtPres.setText("Receptfritt");
    	}
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
	
	public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
		//---copy 1K bytes at a time---
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
}