package com.example.exjobb;

public class Choice {
	public int icon;
	public String title;
	public String distance;
	public String hours;
	
	public Choice() {
		super();
	}
	
	public Choice(int icon, String title) {
		super();
		this.icon = icon;
		this.title = title;
	}
	
	public Choice(int icon, String title, String distance, String hours){
		super();
		this.icon = icon;
		this.title = title;
		this.distance = distance;
		this.hours = hours;
	}
}
