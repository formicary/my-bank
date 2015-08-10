package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
	// below 1000 dollars, firstRate applies
	private static BigDecimal firstRateAmount = new BigDecimal(
			String.valueOf(1000.00));

	/* firstRate = one percent per annum, secondRate = two percent per annum */
	private static BigDecimal firstRate = new BigDecimal(String.valueOf(0.001));
	private static BigDecimal secondRate = new BigDecimal(String.valueOf(0.002));

	public SavingsAccount(int accountNumber) {
		super(Account.SAVINGS, accountNumber);
	}

	public BigDecimal getInterestRateForBalance() {
		if (balance.compareTo(BigDecimal.valueOf(1000.00)) <= 0) {
			return new BigDecimal(String.valueOf(0.001));
		} else {
			return new BigDecimal(String.valueOf(0.002));
		}
	}

	/**
	 * @return amountEarned, a BigDecimal containing the interest earned, using
	 *         the guideline of 0.1% for the first $1,000, and 0.2% for the
	 *         remainder.
	 */
	public BigDecimal interestEarned() {
		/*
		 * interest rates and the balances at which they apply are stored at the
		 * top of the file, so that they can be easily read and changed at a
		 * class-wide level.
		 */

		BigDecimal amountEarned = BigDecimal.ZERO;
		BigDecimal remainder = balance;

		if (balance.compareTo(firstRateAmount) <= 0) {
			amountEarned = balance.multiply(firstRate);
		}

		// subtract the initial 1,000, calculate 1% of it, and 2% of the
		// remainder.
		else {
			remainder = remainder.subtract(firstRateAmount);
			amountEarned = firstRateAmount.multiply(firstRate);
			/* amountEarned += secondRate * (remainder) */
			amountEarned = amountEarned.add(remainder.multiply(secondRate));
		}
		return amountEarned;
	}

}
