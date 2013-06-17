package com.example.exjobb;

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
	
	public Pharmacy(String rID, String chainName, String pharmacyName, String address, String postalCode, String postalArea, String phoneNbr, String openingHoursWD, String closingHoursWD, String openingHoursSAT, String closingHoursSAT, String openingHoursSUN, String closingHoursSUN, String latitude, String longitude) {
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
	}
}
