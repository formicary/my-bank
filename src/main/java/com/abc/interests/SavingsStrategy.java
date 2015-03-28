package com.abc.interests;

import java.math.BigDecimal;

import static com.abc.interests.RateConstants.*;


final public class SavingsStrategy implements InterestRateStrategy {
	
	final private static BigDecimal MINRATE = new BigDecimal("0.001").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	final private static BigDecimal MINAMOUNT = new BigDecimal("1000");
	final private static BigDecimal MAXRATE = new BigDecimal("0.002").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);

	/**
	 * This method computes an interest that can be earned with a given amount for a given strategy
	 * for Savings Account. 
	 * @return amount earned.
	 */
	@Override
	public BigDecimal computeInterestEarned(BigDecimal amount) {
	      
		if (amount.compareTo(MINAMOUNT) > 0) {
			return amount.subtract(MINAMOUNT)
						 .multiply(MAXRATE)
						 .add((MINAMOUNT.multiply(MINRATE)));
		} else if (amount.compareTo(BigDecimal.ZERO) > 0) {
			return amount.multiply(MINRATE);
		} else {
			return BigDecimal.ZERO;
		}

	}
	
}
