package com.abc.interests;

import java.math.BigDecimal;

/**
 * 
 * Interest rates should accrue daily (incl. weekends), however initial rates are per-annum.
 * This class assumes 365 days in a year (it treats leap years as consisting of 365 days too). 
 *
 */

public interface InterestRateStrategy {
	
	/**
	 * This method computes an interest that can be earned with a given amount for a given strategy. 
	 * @return amount earned.
	 */
	BigDecimal computeInterestEarned (final BigDecimal amount); 

}
