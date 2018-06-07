package com.abc;

import static java.lang.Math.abs;

public class StatementUtil {
	
	//Format Double to String with Dollar Sign
	public static String toDollars(double amount, Boolean useNegative) {
		//Check if Negative Symbol is needed
		if ((amount < 0.0) & useNegative) {
    		return String.format("-$%,.2f", abs(amount));
    	}
    	else {
    		return String.format("$%,.2f", abs(amount));
    	}
	}
}
