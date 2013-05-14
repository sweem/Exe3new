package com.example.exjobb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChoiceArrayAdapter extends ArrayAdapter<Choice> {
	Context context;
	int iconId;
	Choice choices[] = null;
	
	public ChoiceArrayAdapter(Context context, int iconId, Choice[] choices) {
		super(context, iconId, choices);
		this.context = context;
		this.iconId = iconId;
		this.choices = choices;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ChoiceHolder holder = null;
		
		if(rowView == null) {
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			rowView = inflater.inflate(iconId, parent, false);
			
			holder = new ChoiceHolder();
			holder.imgIcon = (ImageView) rowView.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) rowView.findViewById(R.id.txtTitle);
			
			rowView.setTag(holder);
		}
		else {
			holder = (ChoiceHolder) rowView.getTag();
		}
		
		Choice choice = choices[pos];
		holder.txtTitle.setText(choice.title);
		holder.imgIcon.setImageResource(choice.icon);
		
		return rowView;
	}
	
	static class ChoiceHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
