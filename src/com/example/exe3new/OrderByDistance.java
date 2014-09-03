package com.example.exe3new;

import java.util.Comparator;

/*
 * The distance between two pharmacies is sorted in order.
 */

public class OrderByDistance implements Comparator<Pharmacy> {
	@Override
	public int compare(Pharmacy lhs, Pharmacy rhs) {
		int retVal = 0;
		if(lhs.getDistToPh() > rhs.getDistToPh()) { //lhs.distToPh > rhs.distToPh
			retVal = 1;
		}
		else if(lhs.getDistToPh() < rhs.getDistToPh()) { //lhs.distToPh < rhs.distToPh
			retVal = -1;
		}
		else if(lhs.getDistToPh() == rhs.getDistToPh()) { //lhs.distToPh == rhs.distToPh
			retVal = 0;
		}
		return retVal;
	}
}
