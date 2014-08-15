package com.example.exjobb;

import java.util.Comparator;

/*
 * The distance between two pharmacies is sorted in order.
 */

public class OrderByDistance implements Comparator<Pharmacy> {
	@Override
	public int compare(Pharmacy lhs, Pharmacy rhs) {
		int retVal = 0;
		if(lhs.distToPh > rhs.distToPh) {
			retVal = 1;
		}
		else if(lhs.distToPh < rhs.distToPh) {
			retVal = -1;
		}
		else if(lhs.distToPh == rhs.distToPh) {
			retVal = 0;
		}
		return retVal;
	}
}
