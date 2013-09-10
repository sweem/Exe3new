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
import android.database.Cursor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChoosenDrugActivity extends Activity {
	String choosenDrugID;
	int choosenNbr;
	Boolean phWithoutDr;
	DBAdapter db;
	Cursor drug;
	String dName, typ, pot, siz, pref, pres, man, sub, pak, url;
	
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
		TextView txtInfo = (TextView) findViewById(R.id.txtInfo);
		ImageView imgView = (ImageView) findViewById(R.id.imgDru);
		
		Bundle b = getIntent().getExtras();
		choosenDrugID = b.getString("drugID");
		//Log.e("drugid", choosenDrugID);
		choosenNbr = b.getInt("nbrOfDrug");
		//Log.e("nbrofdrug", "" + choosenNbr);
		phWithoutDr = b.getBoolean("PhWithoutDr");
		
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("Valt läkemedel");
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
            	url = drug.getString(10);
            	i++;
            } while (drug.moveToNext());
        }
    	
    	drug.close();
    	db.close();
    	
    	txtDrug.setText(dName);
    	txtMan.setText(man);
    	txtPakTyPoSi.setText(typ + " " + pot + ", " + siz + ", " + pak.toLowerCase());
    	
    	if(pref.equals("Nej")) {
    		txtPrefPr.setText("Ej förmån");
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
    	
    	SpannableStringBuilder sb = new SpannableStringBuilder();
    	String txt = "För mer information se ";
    	String link = "bipacksedeln";
    	sb.append(txt);
    	sb.append(link);
    	ClickableSpan cs = new ClickableSpan() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
			}
    	};
    	sb.setSpan(cs, sb.length()-link.length(), sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    	sb.append(".");
    	txtInfo.setText(sb);
    	txtInfo.setMovementMethod(LinkMovementMethod.getInstance());
    	
    	switch(Integer.parseInt(choosenDrugID)) {
    		case 1: imgView.setImageResource(R.drawable.dru1);
    				break;
    		case 2: imgView.setImageResource(R.drawable.dru2);
    				break;
    		//case 3 - No pic
    		//case 4 - No pic
    		//case 5 - No pic
    		case 6: imgView.setImageResource(R.drawable.dru6);
					break;
			//case 7 - No pic
    		case 8: imgView.setImageResource(R.drawable.dru8);
    				break;
    		case 9: imgView.setImageResource(R.drawable.dru9);
					break;
    		case 10: imgView.setImageResource(R.drawable.dru10);
					break;
    		case 11: imgView.setImageResource(R.drawable.dru11);
    				break;
    		case 12:  imgView.setImageResource(R.drawable.dru12);
					break;
    		case 13:  imgView.setImageResource(R.drawable.dru13);
					break;
			//case 14 - No pic
			//case 15 - No pic 
    		case 16: imgView.setImageResource(R.drawable.dru16);
					break;
    		case 17: imgView.setImageResource(R.drawable.dru17);
					break;
    		case 18: imgView.setImageResource(R.drawable.dru18);
					break;
    		case 19: imgView.setImageResource(R.drawable.dru19);
					break;
    		case 20: imgView.setImageResource(R.drawable.dru20);
					break;
    		case 21: imgView.setImageResource(R.drawable.dru21);
					break;	
			//case 22 - No pic
       		case 23: imgView.setImageResource(R.drawable.dru23);
    				break;	
       		case 24: imgView.setImageResource(R.drawable.dru24);
    				break;	
    		default: imgView.setImageResource(R.drawable.ic_launcher);
    	}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choosen_drug, menu);
		return true;
	}*/

	public void onClickNext(View view) {
		Intent i = new Intent(this, PharmaciesActivity2.class);
		/*choosenDrugID = "9"; //Which drug to search for
		choosenNbr = 1;*/ //Nbr of search drug
		i.putExtra("drugID", choosenDrugID);
		Log.e("drugid", choosenDrugID);
		i.putExtra("nbrOfDrug", choosenNbr);
		Log.e("nbrofdrug", "" + choosenNbr);
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
