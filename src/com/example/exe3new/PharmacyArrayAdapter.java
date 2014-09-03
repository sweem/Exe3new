package com.example.exe3new;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * My own PharmacyArrayAdapter for my list of pharmacies to be displayed.
 */

public class PharmacyArrayAdapter extends ArrayAdapter<Pharmacy> {
	private Context context;
	private int iconId;
	private ArrayList<Pharmacy> pharmacies;
	//Time time;
	private Calendar cal;
	
	/*
	 * Constructor.
	 */
	
	public PharmacyArrayAdapter(Context context, int iconId, ArrayList<Pharmacy> pharmacies, Calendar cal) {
		super(context, iconId, pharmacies);
		this.context = context;
		this.iconId = iconId;
		this.pharmacies = pharmacies;
		//this.time = time;
		this.cal = cal;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		View rowView = convertView;
		PharmacyChoiceHolder holder = null;
		
		if(rowView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			rowView = inflater.inflate(iconId, parent, false);
			
			holder = new PharmacyChoiceHolder();
			holder.imgIcon = (ImageView) rowView.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
			holder.txtDist = (TextView) rowView.findViewById(R.id.txtDist);
			holder.txtOpen = (TextView) rowView.findViewById(R.id.txtOpen);
			holder.txtStock = (TextView) rowView.findViewById(R.id.txtStock);
					
			rowView.setTag(holder);
		}
		else {
			holder = (PharmacyChoiceHolder) rowView.getTag();
		}
		
		Pharmacy ph = pharmacies.get(pos);
		holder.imgIcon.setImageResource(ph.getIcon());
		holder.txtTitle.setText(ph.getPharmacyName());
		holder.txtDist.setText(ph.getDistance());

		String opHour = ph.getOpeningHoursToday(cal); //time
		if(opHour.equals("Stängt")) {
			holder.txtOpen.setTextColor(0xffff0000);
		} else {
			holder.txtOpen.setTextColor(0xff238e23);
		}
		holder.txtOpen.setText(opHour);
		//holder.txtOpen.setText(ph.getOpeningHoursToday(time));
		
		if(holder.txtStock != null)
			holder.txtStock.setText(ph.getNbrOfDrug());
		return rowView;
	}
	
	/*
	* PharmacyChoiceHolder is a static inner class that holds references to all inner views from a row.
	*/
	
	static class PharmacyChoiceHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView txtDist;
		TextView txtOpen;
		TextView txtStock;
	}
}
