package com.example.exe3new;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.AssetManager;

public class CopyDB {
	private AssetManager mgr;
	private String packageName;
	
	public CopyDB(AssetManager aM, String pN) {
		mgr = aM;
		packageName = pN;
	}
	
	public void tryCopyDB() {
		try {
	        String destPath = "/data/data/" + packageName + "/databases"; //getPackageName()
	        File f = new File(destPath);
	        if (!f.exists()) {            	
	        	f.mkdirs();
	            f.createNewFile();
	        	
	        	//---copy the db from the assets folder into 
	        	// the databases folder---
	            CopyDB(mgr.open("mydb"), new FileOutputStream(destPath + "/MyDB")); //getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath + "/MyDB")
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/*
	 * Copy the database from the assets folder into the database folder.
	 */
	
	private void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
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
