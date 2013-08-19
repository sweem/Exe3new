package com.example.exjobb;

import java.util.Comparator;

public class OrderByPhID implements Comparator<Pharmacy> {
	@Override
	public int compare(Pharmacy lhs, Pharmacy rhs) {	
		return lhs.id.compareTo(rhs.id);
	}
}
