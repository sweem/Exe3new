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
	//ArrayList<Choice> choices;
	
	/*public PharmacyArrayAdapter(Context context, int iconId, ArrayList<Choice> choices) {
		super(context, iconId, choices);
		this.context = context;
		this.iconId = iconId;
		this.choices = choices;
	}*/
	
	public PharmacyArrayAdapter(Context context, int iconId, ArrayList<Pharmacy> pharmacies) {
		super(context, iconId, pharmacies);
		this.context = context;
		this.iconId = iconId;
		this.pharmacies = pharmacies;
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
					
			rowView.setTag(holder);
		}
		else {
			holder = (PharmacyChoiceHolder) rowView.getTag();
		}
		
		//Choice choice = choices[pos];
		//Choice choice = choices.get(pos);
		Pharmacy ph = pharmacies.get(pos);
		/*holder.imgIcon.setImageResource(choice.icon);
		holder.txtTitle.setText(choice.title);
		holder.txtDist.setText(choice.distance);
		holder.txtOpen.setText(choice.hours);*/
		//holder.imgIcon.setImageResource(R.drawable.apotek_ikon);
		holder.imgIcon.setImageResource(ph.getIcon());
		holder.txtTitle.setText(ph.getPharmacyName());
		holder.txtDist.setText(ph.getDistance());
		holder.txtOpen.setText(ph.getOpeningHours());
		return rowView;
	}
	
	static class PharmacyChoiceHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView txtDist;
		TextView txtOpen;
	}
}
