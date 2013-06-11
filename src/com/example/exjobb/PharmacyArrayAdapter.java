package com.example.exjobb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PharmacyArrayAdapter extends ArrayAdapter<Choice> {
	Context context;
	int iconId;
	Choice choices[] = null;
	
	public PharmacyArrayAdapter(Context context, int iconId, Choice[] choices) {
		super(context, iconId, choices);
		this.context = context;
		this.iconId = iconId;
		this.choices = choices;
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
		
		Choice choice = choices[pos];
		holder.imgIcon.setImageResource(choice.icon);
		holder.txtTitle.setText(choice.title);
		holder.txtDist.setText(choice.distance);
		holder.txtOpen.setText(choice.hours);
		
		return rowView;
	}
	
	static class PharmacyChoiceHolder {
		ImageView imgIcon;
		TextView txtTitle;
		TextView txtDist;
		TextView txtOpen;
	}
}
