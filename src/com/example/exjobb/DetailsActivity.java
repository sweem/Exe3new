package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	DBAdapter db;
	String id;
	double curLat, curLon;
	Pharmacy ph;
	
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
        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        curLat = b.getDouble("curLat");
        curLon = b.getDouble("curLon");
        /*String msg = id + "/" + curLat + "/" + curLon;
        Log.e("ID/Lat/Lon", msg);*/
        
        ph = db.getPharmacyWithId(id);
        db.close();
       
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
	
	public void onClickDirections(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + curLat + "," + curLon + "&daddr=" + ph.lat + "," + ph.lon));
		startActivity(i);
		//finish();
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
}