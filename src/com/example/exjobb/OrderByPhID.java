package com.example.exjobb;

import java.util.Comparator;

public class OrderByPhID implements Comparator<Pharmacy> {
	@Override
	public int compare(Pharmacy lhs, Pharmacy rhs) {
		/*int retVal = 0;
		if(Integer.parseInt(lhs.id) > Integer.parseInt(rhs.id)) {
			retVal = 1;
		}
		else if(Integer.parseInt(lhs.id) < Integer.parseInt(rhs.id)) {
			retVal = -1;
		}
		else if(Integer.parseInt(lhs.id) == Integer.parseInt(rhs.id)) {
			retVal = 0;
		}
		return retVal;*/
		
		return lhs.id.compareTo(rhs.id);
	}
}
