package com.abc.interests;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** 
 * Interest rate constants used among various interest computation strategies.
 */
final public class RateConstants {

	final public static BigDecimal YEAR = new BigDecimal("365");
	final public static RoundingMode ROUNDINGMODE = RoundingMode.HALF_UP;
	final public static int AFTERCOMMA = 12; 
}
