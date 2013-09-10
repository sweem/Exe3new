package com.example.exjobb;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PharmacyArrayAdapter extends ArrayAdapter<Pharmacy> {
	Context context;
	int iconId;
	ArrayList<Pharmacy> pharmacies;
	Time time;
	
	public PharmacyArrayAdapter(Context context, int iconId, ArrayList<Pharmacy> pharmacies, Time time) {
		super(context, iconId, pharmacies);
		this.context = context;
		this.iconId = iconId;
		this.pharmacies = pharmacies;
		this.time = time;
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

		String opHour = ph.getOpeningHoursToday(time);
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
	
	static class PharmacyChoiceHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView txtDist;
		TextView txtOpen;
		TextView txtStock;
	}
}
