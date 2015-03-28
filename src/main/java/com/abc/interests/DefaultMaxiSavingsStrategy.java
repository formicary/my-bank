package com.abc.interests;

import java.math.BigDecimal;

import static com.abc.interests.RateConstants.*;

final public class DefaultMaxiSavingsStrategy implements InterestRateStrategy {
	
	final private static BigDecimal MINAMOUNT = new BigDecimal("1000");
	final private static BigDecimal MIDAMOUNT = new BigDecimal("2000");
	final private static BigDecimal MINRATE = new BigDecimal("0.02").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	final private static BigDecimal MIDRATE = new BigDecimal("0.05").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	final private static BigDecimal MAXRATE = new BigDecimal("0.1").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);

	/**
	 * This method computes an interest that can be earned with a given amount for a given strategy
	 *  for Maxi-Savings Account. 
	 * @return amount earned.
	 */
	@Override
	public BigDecimal computeInterestEarned(final BigDecimal amount) {
	
		if (amount.compareTo(MIDAMOUNT) > 0) {
        	return amount.subtract(MIDAMOUNT)
        			.multiply(MAXRATE)
        			.add(MINAMOUNT.multiply(MINRATE))
        			.add(MIDAMOUNT.subtract(MINAMOUNT).multiply(MIDRATE));
        } else if (amount.compareTo(MINAMOUNT) > 0) {
        	return amount.subtract(MINAMOUNT)
        			.multiply(MIDRATE)
        			.add(MINAMOUNT.multiply(MINRATE));
        } else if (amount.compareTo(BigDecimal.ZERO) > 0) {
        	return amount.multiply(MINRATE);
        	
        } else return BigDecimal.ZERO;
	}

}
