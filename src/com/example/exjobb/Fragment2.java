package com.example.exjobb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class Fragment2 extends DialogFragment {
	static Fragment2 newInstance(String title, String message) {
		Fragment2 fragment = new Fragment2();
		Bundle args = new Bundle();
		args.putString("title", title);
		args.putString("message", message);
		fragment.setArguments(args);
		return fragment;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		String title = getArguments().getString("title");
		String message = getArguments().getString("message");
		return new AlertDialog.Builder(getActivity())
		.setIcon(R.drawable.ic_launcher)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((PharmaciesActivity2) getActivity()).doPositiveClick();
				
			}
		}).create();
		/*.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((DrugsActivity) getActivity()).doNegativeClick();
				
			}
		}).create();*/
	}
}

