package com.example.exjobb;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.EditText;

public class Login2Activity extends Activity {
	private String personNbr;
	private EditText personNbrView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login2);
		
		personNbrView = (EditText) findViewById(R.id.personnbr);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login2, menu);
		return true;
	}

}
