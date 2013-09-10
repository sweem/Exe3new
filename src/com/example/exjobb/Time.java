package com.example.exjobb;

import java.util.Calendar;

import android.util.Log;

public class Time {
	Calendar cal;
	
	public Time () {
		super();
    	cal = Calendar.getInstance();
    	/*cal.set(Calendar.DAY_OF_WEEK, 3); //Change current day
     	
    	cal.set(Calendar.HOUR_OF_DAY, 20); //Change current time
     	cal.set(Calendar.MINUTE, 0);
     	cal.set(Calendar.SECOND, 0);
     	cal.set(Calendar.MILLISECOND, 0);*/
	}
	
	/*public Time (int curDay, int hour, int min, int sec) {
		super();
    	cal = Calendar.getInstance();    	
        cal.set(Calendar.DAY_OF_WEEK, curDay); //Change current day
    	cal.set(Calendar.HOUR_OF_DAY, hour); //Change current time
    	cal.set(Calendar.MINUTE, min);
    	cal.set(Calendar.SECOND, sec);
    	cal.set(Calendar.MILLISECOND, 0);
	}*/

	public void setCurrentDay(int day) {
        cal.set(Calendar.DAY_OF_WEEK, day);
	}
	
	public void setCurrentTime(String curTime) {
		StringBuffer currentTime = new StringBuffer(curTime);//00:00:00
		int start = 0;
		int col = currentTime.indexOf(";");
		int hour = Integer.parseInt(currentTime.substring(0, col));
    	cal.set(Calendar.HOUR_OF_DAY, hour);
		
		start = col + 1;
		col = currentTime.indexOf(":", start);
		int min = Integer.parseInt(currentTime.substring(start, col));
    	cal.set(Calendar.MINUTE, min);
		
		start = col + 1;
		col = currentTime.indexOf(":", start);
		int sec = Integer.parseInt(currentTime.substring(start));
    	cal.set(Calendar.SECOND, sec);
		
    	cal.set(Calendar.MILLISECOND, 0);
	}
	
	public Calendar getCal() {
		return cal;
	}
	
	public int getCurrentDay() {
    	Log.e("Curday in time", "" + cal.get(Calendar.DAY_OF_WEEK));
    	return cal.get(Calendar.DAY_OF_WEEK);
    }
	
	public String getCurrentTime() {  	
    	StringBuffer currentTime = new StringBuffer();
    	int hour = cal.get(Calendar.HOUR_OF_DAY);
    	int min = cal.get(Calendar.MINUTE);
    	int sec = cal.get(Calendar.SECOND);
    	
    	if(hour <= 9)
    		currentTime.append("0");
    	currentTime.append(hour + ":");
    	if(min <= 9)
    		currentTime.append("0");
    	currentTime.append(min + ":");
    	if(sec <= 9)
    		currentTime.append("0");
    	currentTime.append(sec);
    	
    	Log.e("Curtime in time", currentTime.toString());
    	
		return currentTime.toString();
    }

}
