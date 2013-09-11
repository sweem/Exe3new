package com.example.exjobb;

import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class Pharmacy {
	public String id;
	public String chName;
	public String phName;
	public String addr;
	public String pCode;
	public String pArea;
	public String wPage;
	public String pNbr;
	public String opHWD;
	public String clHWD;
	public String opHSAT;
	public String clHSAT;
	public String opHSUN;
	public String clHSUN;
	public String lat;
	public String lon;
	public float distToPh;
	public int iconId;
	public String nbrOfDrug;
	
	public Pharmacy(String rID, String chainName, String pharmacyName, String address, String postalCode, String postalArea, String webPage, String phoneNbr, String openingHoursWD, String closingHoursWD, String openingHoursSAT, String closingHoursSAT, String openingHoursSUN, String closingHoursSUN, String latitude, String longitude) {
		super();
		this.id = rID;
		this.chName = chainName;
		this.phName = pharmacyName;
		this.addr = address;
		this.pCode = postalCode;
		this.pArea = postalArea;
		this.wPage = webPage;
		this.pNbr = phoneNbr;
		this.opHWD = openingHoursWD;
		this.clHWD = closingHoursWD;
		this.opHSAT = openingHoursSAT;
		this.clHSAT = closingHoursSAT;
		this.opHSUN = openingHoursSUN;
		this.clHSUN = closingHoursSUN;
		this.lat = latitude;
		this.lon = longitude;
		nbrOfDrug = null;
	}
	
	public Pharmacy(String rID, String chainName, String pharmacyName, String address, String postalCode, String postalArea, String webPage, String phoneNbr, String openingHoursWD, String closingHoursWD, String openingHoursSAT, String closingHoursSAT, String openingHoursSUN, String closingHoursSUN, String latitude, String longitude, float distToPharmacy) {
		super();
		this.id = rID;
		this.chName = chainName;
		this.phName = pharmacyName;
		this.addr = address;
		this.pCode = postalCode;
		this.pArea = postalArea;
		this.wPage = webPage;
		this.pNbr = phoneNbr;
		this.opHWD = openingHoursWD;
		this.clHWD = closingHoursWD;
		this.opHSAT = openingHoursSAT;
		this.clHSAT = closingHoursSAT;
		this.opHSUN = openingHoursSUN;
		this.clHSUN = closingHoursSUN;
		this.lat = latitude;
		this.lon = longitude;
		this.distToPh = distToPharmacy;
		nbrOfDrug = null;
	}
	
	public Pharmacy(String rID, String chainName, String pharmacyName, String address, String postalCode, String postalArea, String webPage, String phoneNbr, String openingHoursWD, String closingHoursWD, String openingHoursSAT, String closingHoursSAT, String openingHoursSUN, String closingHoursSUN, String latitude, String longitude, float distToPharmacy, String nbr) {
		super();
		this.id = rID;
		this.chName = chainName;
		this.phName = pharmacyName;
		this.addr = address;
		this.pCode = postalCode;
		this.pArea = postalArea;
		this.wPage = webPage;
		this.pNbr = phoneNbr;
		this.opHWD = openingHoursWD;
		this.clHWD = closingHoursWD;
		this.opHSAT = openingHoursSAT;
		this.clHSAT = closingHoursSAT;
		this.opHSUN = openingHoursSUN;
		this.clHSUN = closingHoursSUN;
		this.lat = latitude;
		this.lon = longitude;
		this.distToPh = distToPharmacy;
		this.nbrOfDrug = nbr;
	}
	
	public Pharmacy(String rID, String nbr) {
		super();
		this.id = rID;
		this.nbrOfDrug = nbr;
	}
	
	public String getNbrOfDrug() {
		if(nbrOfDrug == null) {
			return "";
		}
		else if(Integer.parseInt(nbrOfDrug) > 1) {
			return nbrOfDrug + " st tillgängliga";
		}
		else {
			return nbrOfDrug + " st tillgänglig";
		}
	}
	
	public String getDistance() {
		StringBuffer sb = new StringBuffer();
		int iDist = Math.round(distToPh);
		double dDist;
		if (iDist < 1000) {
			dDist = ((double) iDist)/(10);
			iDist = (int) Math.round(dDist);
			int retDist = iDist*10;
			sb.append(retDist);
			sb.append(" m");
		}
		else {
			dDist = ((double) iDist)/(100);
			iDist = (int) Math.round(dDist);
			double retDist = ((double) iDist)/(10);
			sb.append(retDist);
			sb.append(" km");
		}
		return sb.toString();
	}
	
	public void setDistance(float dist) {
		distToPh = dist;
	}
	
	public String getOpeningHoursToday(Calendar cal) { //Time time	
		Calendar cur = cal; //time.getCal(); //Calendar.getInstance();
		int curDay = cal.get(Calendar.DAY_OF_WEEK); //time.getCurrentDay(); //cur.get(Calendar.DAY_OF_WEEK);
		//Log.e("curDay in ph", "" + curDay);
		
		Calendar op = Calendar.getInstance();
		op.set(Calendar.DAY_OF_WEEK, curDay);
		op.set(Calendar.MINUTE, 0);
	    op.set(Calendar.SECOND, 0);
	    op.set(Calendar.MILLISECOND, 0);

		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.DAY_OF_WEEK, curDay);
	    cl.set(Calendar.MINUTE, 0);
	    cl.set(Calendar.SECOND, 0);
	    cl.set(Calendar.MILLISECOND, 0);
		
		StringBuffer sb, sbO, sbC;
		int col;
		
		sb = new StringBuffer();
		sbO = new StringBuffer();
		sbC = new StringBuffer();
		if(curDay == 1) {//Sunday
			if(opHSUN.equals("Closed") && clHSUN.equals("Closed")) {
				sb.append("Stängt");
			} else {
				sbO.append(opHSUN);
				sbC.append(clHSUN);
					
				col = sbO.indexOf(":");
				op.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbO.substring(0, col)));	
				col = sbO.indexOf(":");
				cl.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbC.substring(0, col)));
				
		    	if((cur.compareTo(op) >= 0) && (cur.compareTo(cl) < 0)) {//Show todays opening hours if open now
		    		sb.append(opHSUN);
		    		sb.append("-");
		    		sb.append(clHSUN);	    			
		    	}else { //Show closed
		    		sb.append("Stängt");
		    	}
			}
		}
		else if(curDay == 7) {//Saturday
			if(opHSAT.equals("Closed") && clHSAT.equals("Closed")) {
				sb.append("Stängt");
			} else {
				sbO.append(opHSAT);
				sbC.append(clHSAT);
					
				col = sbO.indexOf(":");
				op.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbO.substring(0, col)));
				col = sbO.indexOf(":");
				cl.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbC.substring(0, col)));
				
		    	if((cur.compareTo(op) >= 0) && (cur.compareTo(cl) < 0)) {//Show todays opening hours if open now
		    		sb.append(opHSAT);
		    		sb.append("-");
		    		sb.append(clHSAT);	    			
		    	}else { //Show closed
		    		sb.append("Stängt");
		    	}
			}
		}
		else {//Weekday
			if(opHWD.equals("Closed") && clHWD.equals("Closed")) {
				sb.append("Stängt");
			} else {
				sbO.append(opHWD);
				sbC.append(clHWD);
				
				col = sbO.indexOf(":");
		    	op.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbO.substring(0, col)));
				col = sbO.indexOf(":");
		    	cl.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sbC.substring(0, col)));
		    	
		    	if((cur.compareTo(op) >= 0) && (cur.compareTo(cl) < 0)) {//Show todays opening hours if open now
		    		sb.append(opHWD);
		    		sb.append("-");
		    		sb.append(clHWD);	    			
		    	}else { //Show closed
		    		sb.append("Stängt");
		    	}
			}
		}
		return sb.toString();
	}
	
	public String getOpeningHoursWD() {
		StringBuffer sb = new StringBuffer();
		if(opHWD.equals("Closed") && clHWD.equals("Closed")) {
			sb.append("Stängt");
		}
		else {
			sb.append(opHWD);
			sb.append("-");
			sb.append(clHWD);
		}
		return sb.toString();
	}
	
	public String getOpeningHoursSAT() {
		StringBuffer sb = new StringBuffer();
		if(opHSAT.equals("Closed") && clHSAT.equals("Closed")) {
			sb.append("Stängt");
		}
		else {
			sb.append(opHSAT);
			sb.append("-");
			sb.append(clHSAT);
		}
		return sb.toString();
	}
	
	public String getOpeningHoursSUN() {
		StringBuffer sb = new StringBuffer();
		if(opHSUN.equals("Closed") && clHSUN.equals("Closed")) {
			sb.append("Stängt");
		}
		else {
			sb.append(opHSUN);
			sb.append("-");
			sb.append(clHSUN);
		}
		return sb.toString();
	}
	
	public String getPharmacyName() {
		return phName;
	}
	
	public void setIcon() {
		if(chName.equals("Apoteket")) {
			iconId = R.drawable.apoteket_ikon;
		}
		else if(chName.equals("Apotek Hjärtat")) {
			iconId = R.drawable.apotekhjartat_ikon;
		}
		else if(chName.equals("Apoteksgruppen")) {
			iconId = R.drawable.apoteksg_ikon;
		}
		else if(chName.equals("Cura apoteket")) {
			iconId = R.drawable.curaa_ikon;
		}
		else if(chName.equals("Lloyds Apotek")) {
			iconId = R.drawable.lloydsa_ikon;
		}
		else if(chName.equals("Kronans droghandel")) {
			iconId = R.drawable.kronansd_ikon;
		}
		else if(chName.equals("Medstop")) {
			iconId = R.drawable.medstop_ikon;
		}
		else if(chName.equals("Vårdapoteket")) {
			iconId = R.drawable.varda_ikon;
		}
		else {
			iconId = R.drawable.apotek_ikon;
		}
	}
	
	public int getIcon() {
		return iconId;
	}
	
	public String getWebPage() {
		return wPage;
	}
	
	public String getPhoneNbr() {
		return pNbr;
	}
	
	public String getAddress() {
		return addr;
	}
	
	public String getPostalAC() {
		StringBuffer sb = new StringBuffer();
		sb.append(pCode);
		sb.append(" ");
		sb.append(pArea.toUpperCase());
		return sb.toString();
	}
}
