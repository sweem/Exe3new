package com.example.exjobb;

import java.util.Comparator;

public class OrderByStrength implements Comparator<String> {//500 mg/30mg, 24 mg/ml ect is not included here.
	@Override
	public int compare(String lhs, String rhs) {
		StringBuffer sbLhs = new StringBuffer(lhs);
		StringBuffer sbRhs = new StringBuffer(rhs);
		
		int spaceLhs = sbLhs.indexOf(" ");
		//Log.e("spacelhs", Integer.toString(spaceLhs));
		int spaceRhs = sbRhs.indexOf(" ");
		//Log.e("spacerhs", Integer.toString(spaceRhs));
		
		String unitLhs = sbLhs.substring(spaceLhs+1);
		//Log.e("unitlhs", unitLhs);
		String unitRhs = sbRhs.substring(spaceRhs+1);
		//Log.e("unitrhs", unitRhs);
		
		if(unitLhs.equals(unitRhs)) {
			//Log.e("units equal", "true");
			int tmpLhs = Integer.parseInt(sbLhs.substring(0, spaceLhs));
			//Log.e("tmplhs", Integer.toString(tmpLhs));
			int tmpRhs = Integer.parseInt(sbRhs.substring(0, spaceRhs));
			//Log.e("tmprhs", Integer.toString(tmpRhs));
			
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
		}
		else {
			//Log.e("units equal", "false");
			return unitLhs.compareTo(unitRhs);
		}
	}
}
