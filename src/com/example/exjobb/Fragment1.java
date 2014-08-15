package com.example.exjobb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/*
 * Displaying an activity as an dialog.
 */

public class Fragment1 extends DialogFragment {
	static Fragment1 newInstance(String title, String message) {
		Fragment1 fragment = new Fragment1();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		fragment.setArguments(args);
		return fragment;
	}
	
	/*
	 * Is performed when the showDialog() method is called.
	 */
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");
		return new AlertDialog.Builder(getActivity())
		.setIcon(R.drawable.ic_launcher)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			/*
			 * Is performed when the user clicks ok.
			 */
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((DrugsActivity) getActivity()).doPositiveClick();
				
			}
		}).create();
	}
}

