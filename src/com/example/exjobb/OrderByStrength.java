package com.example.exjobb;

import java.util.Comparator;

import android.util.Log;

public class OrderByStrength implements Comparator<String> {//500 mg/30mg, 24 mg/ml ect is not included here.
	@Override
	public int compare(String lhs, String rhs) {
		StringBuffer sbl = new StringBuffer(lhs);
		StringBuffer sbr = new StringBuffer(rhs);
		
		int endl = sbl.indexOf(" mg");
		int endr = sbr.indexOf(" mg");
		
		int tmpl = Integer.parseInt(sbl.substring(0, endl));
		Log.e("tmpl", Integer.toString(tmpl));
		int tmpr = Integer.parseInt(sbr.substring(0, endr));
		Log.e("tmpr", Integer.toString(tmpr));
		
		int retVal = 0;
		if(tmpl > tmpr) {
			retVal = 1;
		}
		else if(tmpl < tmpr) {
			retVal = -1;
		}
		else if(tmpl == tmpr) {
			retVal = 0;
		}
		
		return retVal;
	}
}
