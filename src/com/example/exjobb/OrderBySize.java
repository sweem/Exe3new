package com.example.exjobb;

import java.util.Comparator;

public class OrderBySize implements Comparator<String> {//3 x 30 st is not included here
	@Override
	public int compare(String lhs, String rhs) {
		//Log.e("lhs", lhs);
		//Log.e("rhs", rhs);
		StringBuffer sbLhs = new StringBuffer(lhs);
		StringBuffer sbRhs = new StringBuffer(rhs);
		
		int xLhs = sbLhs.indexOf("x");
		//Log.e("xLhs", Integer.toString(xLhs));
		
		int spaceLhs = sbLhs.lastIndexOf(" ");
		String unitLhs = sbLhs.substring(spaceLhs+1, sbLhs.length());
		//Log.e("unitLhs", unitLhs);
		
		int xRhs = sbRhs.indexOf("x");
		//Log.e("xRhs", Integer.toString(xRhs));
		
		int spaceRhs = sbRhs.lastIndexOf(" ");
		String unitRhs = sbRhs.substring(spaceRhs+1, sbRhs.length());
		//Log.e("unitRhs", unitRhs);
		
		int tmpLhs, tmpRhs;
		
		if(xLhs == -1 && xRhs == -1) { //No x both in Lhs and Rhs
			//Log.e("xLhs && xRhs == -1", "true");
			
			String nbrLhs = sbLhs.substring(0, spaceLhs);
			//Log.e("nbrLhs", nbrLhs);
			String nbrRhs = sbRhs.substring(0, spaceRhs);
			//Log.e("nbrRhs", nbrRhs);
			
			tmpLhs = Integer.parseInt(nbrLhs);
			tmpRhs = Integer.parseInt(nbrRhs);
		} else if(xLhs == -1 && xRhs != -1) { //No x in Lhs but x in Rhs 
			//Log.e("xLhs == -1", "true");
			
			String firstNbrRhs = sbRhs.substring(0, xRhs-1);
			//Log.e("firstNbrRhs", firstNbrRhs);
			String lastNbrRhs = sbRhs.substring(xRhs+2, spaceRhs);
			//Log.e("lastNbrRhs", lastNbrRhs);
			
			String nbrLhs = sbLhs.substring(0, spaceLhs);
			//Log.e("nbrLhs", nbrLhs);
			
			tmpLhs = Integer.parseInt(nbrLhs);
			tmpRhs = (Integer.parseInt(firstNbrRhs) * Integer.parseInt(lastNbrRhs));
		} else if(xRhs == -1 && xLhs != -1) { //No x in Rhs but x in Lhs
			//Log.e("xRhs == -1", "true");
			
			String firstNbrLhs = sbLhs.substring(0, xLhs-1);
			//Log.e("firstNbrLhs", firstNbrLhs);
			String lastNbrLhs = sbLhs.substring(xLhs+2, spaceLhs);
			//Log.e("lastNbrLhs", lastNbrLhs);
			
			String nbrRhs = sbRhs.substring(0, spaceRhs);
			//Log.e("nbrRhs", nbrRhs);
			
			tmpLhs = Integer.parseInt(firstNbrLhs) * Integer.parseInt(lastNbrLhs);
			tmpRhs = Integer.parseInt(nbrRhs);
		} else { //x in bot Lhs and Rhs
			//Log.e("xLhs && xRhs == -1", "false");

			String firstNbrLhs = sbLhs.substring(0, xLhs-1);
			//Log.e("firstNbrLhs", firstNbrLhs);
			String lastNbrLhs = sbLhs.substring(xLhs+2, spaceLhs);
			//Log.e("lastNbrLhs", lastNbrLhs);
			
			String firstNbrRhs = sbRhs.substring(0, xRhs-1);
			//Log.e("firstNbrRhs", firstNbrRhs);
			String lastNbrRhs = sbRhs.substring(xRhs+2, spaceRhs);
			//Log.e("lastNbrRhs", lastNbrRhs);
			
			tmpLhs = Integer.parseInt(firstNbrLhs) * Integer.parseInt(lastNbrLhs);
			tmpRhs = Integer.parseInt(firstNbrRhs) * Integer.parseInt(lastNbrRhs);
		}
		
		if(unitLhs.equals(unitRhs)) {//Units are equal
			//Log.e("units equal", "true");
			int retVal = 0;
			
			if(tmpLhs > tmpRhs) {
				retVal = 1;
			}
			else if(tmpLhs < tmpRhs) {
				retVal = -1;
			}
			else if(tmpLhs == tmpRhs) {
				retVal = 0;
			}
			
			return retVal;
		} else { //Units are not equal
			//Log.e("units equal", "false");
			return unitLhs.compareTo(unitRhs);
		}
	}
}
