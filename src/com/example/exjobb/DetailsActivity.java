package com.example.exjobb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/*
 * DetailsActivity shows details (like opening hours, contact information and address) about the selected pharmacy.
 */

public class DetailsActivity extends Activity {
	DBAdapter db;
	String id;
	double curLat, curLon;
	Pharmacy ph;
	TextView tvPN;
	int curDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);
		
		ActionBar actionBar = getActionBar();
		
		TextView tvHeaderHWD = (TextView) findViewById(R.id.txtHeaderHWD); 
		TextView tvHeaderHSAT = (TextView) findViewById(R.id.txtHeaderHSAT); 
		TextView tvHeaderHSUN = (TextView) findViewById(R.id.txtHeaderHSUN); 
		
		TextView tvHWD = (TextView) findViewById(R.id.txtHWD);
		TextView tvHSAT = (TextView) findViewById(R.id.txtHSAT);
		TextView tvHSUN = (TextView) findViewById(R.id.txtHSUN);
		tvPN = (TextView) findViewById(R.id.txtPN);
			
		TextView tvA = (TextView) findViewById(R.id.txtA);
		TextView tvPCA = (TextView) findViewById(R.id.txtPCA);
		TextView tvWP = (TextView) findViewById(R.id.txtWP);
		
		db = new DBAdapter(this);
        try {
            String destPath = "/data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);
            if (!f.exists()) {            	
            	f.mkdirs();
                f.createNewFile();
            	
            	//---copy the db from the assets folder into 
            	// the databases folder---
                CopyDB(getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath + "/MyDB"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        db.open();
        //day = db.getCurrentDay();
        Bundle b = getIntent().getExtras();
        id = b.getString("id");
        curLat = b.getDouble("curLat");
        curLon = b.getDouble("curLon");
        curDay = b.getInt("curDay");
        Log.e("Curday in details", "" + curDay);
        
        ph = db.getPharmacyWithId(id);
        db.close();
       
		actionBar.setTitle(ph.getPharmacyName());
		tvHWD.setText(ph.getOpeningHoursWD());
		tvHSAT.setText(ph.getOpeningHoursSAT());
		tvHSUN.setText(ph.getOpeningHoursSUN());
		
		if(curDay == 1) { //Sunday
			tvHeaderHSUN.setTypeface(null, Typeface.BOLD);
			tvHSUN.setTypeface(null, Typeface.BOLD);
		}
		else if(curDay == 7) { //Saturday
			tvHeaderHSAT.setTypeface(null, Typeface.BOLD);
			tvHSAT.setTypeface(null, Typeface.BOLD);
		}
		else {
			tvHeaderHWD.setTypeface(null, Typeface.BOLD);
			tvHWD.setTypeface(null, Typeface.BOLD);
		}
		
		tvPN.setText(ph.getPhoneNbr());
		tvWP.setText(ph.getWebPage());
		tvA.setText(ph.getAddress());
		tvPCA.setText(ph.getPostalAC());
	}

	/*
	 * If performed when the button is clicked. The GPS is started.
	 */
	
	public void onClickDirections(View view) {
		Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + curLat + "," + curLon + "&daddr=" + ph.lat + "," + ph.lon));
		startActivity(i);
		//finish();
	}
	
    /*
     * Copy the database from the assets folder into the database folder.
     */
	
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