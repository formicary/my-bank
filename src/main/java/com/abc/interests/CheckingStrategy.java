package com.abc.interests;

import java.math.BigDecimal;

import static com.abc.interests.RateConstants.*;

final public class CheckingStrategy implements InterestRateStrategy {
	
	final private static BigDecimal INTEREST = new BigDecimal("0.001").divide(YEAR, AFTERCOMMA, ROUNDINGMODE);
	
	/**
	 * This method computes an interest that can be earned with a given amount for a given strategy for Checking Account. 
	 * @return amount earned.
	 */
	@Override
	public BigDecimal computeInterestEarned(BigDecimal amount) {
		return amount.multiply(INTEREST);
	
	}

}
