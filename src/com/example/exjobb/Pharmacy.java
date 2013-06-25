package com.example.exjobb;

import java.util.Calendar;

public class Pharmacy {
	public String id;
	public String chName;
	public String phName;
	public String addr;
	public String pCode;
	public String pArea;
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
	
	public Pharmacy(String rID, String chainName, String pharmacyName, String address, String postalCode, String postalArea, String phoneNbr, String openingHoursWD, String closingHoursWD, String openingHoursSAT, String closingHoursSAT, String openingHoursSUN, String closingHoursSUN, String latitude, String longitude, float distToPharmacy) {
		super();
		this.id = rID;
		this.chName = chainName;
		this.phName = pharmacyName;
		this.addr = address;
		this.pCode = postalCode;
		this.pArea = postalArea;
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
	
	public String getOpeningHours() {
		StringBuffer sb = new StringBuffer();
		Calendar cal = Calendar.getInstance();
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		if(dayOfWeek == 1) {//Sunday
			sb.append(opHSUN);
			sb.append("-");
			sb.append(clHSUN);
		}
		else if(dayOfWeek == 7) {//Saturday
			sb.append(opHSAT);
			sb.append("-");
			sb.append(clHSAT);
		}
		else {//Weekday
			sb.append(opHWD);
			sb.append("-");
			sb.append(clHWD);
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
}
