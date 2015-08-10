package com.abc;

import java.math.BigDecimal;
import java.util.Date;

public class MaxiAccount extends Account {
	// one thousand dollars: below this amount, customers get 2% interest on
	// their account
	private static BigDecimal firstRateAmount = new BigDecimal(
			String.valueOf(1000.00));
	// two thousand dollars: below this amount, customers get 2% on 1000 and 5%
	// on the remainder
	private static BigDecimal secondRateAmount = new BigDecimal(
			String.valueOf(2000.00));

	/* firstRate, secondRate and thirdRate are 2%, 5% and 10% respectively */
	private static BigDecimal firstRate = new BigDecimal(String.valueOf(0.02));
	private static BigDecimal secondRate = new BigDecimal(String.valueOf(0.05));
	private static BigDecimal thirdRate = new BigDecimal(String.valueOf(0.10));

	public MaxiAccount(int accountNumber) {
		super(Account.MAXI_SAVINGS, accountNumber);
	}

	public BigDecimal getInterestRateForBalance() {
		if (balance.compareTo(firstRateAmount) <= 0) {
			return firstRate;
		} else if (balance.compareTo(secondRateAmount) <= 0) {
			return secondRate;
		} else {
			return thirdRate;
		}
	}

	/**
	 * 
	 * @return true if a withdrawal was made in the previous 10 days, else false
	 */
	public boolean madeRecentWithdrawal() {
		/*
		 * "In the last ten days" is taken to mean to the millisecond. If for
		 * example the last withdrawal was made at 11:59:23:01 pm on the 31st of
		 * a month, this function will return true until 11:59:23:01 pm on the
		 * 10th of the following month.
		 */
		Date currentTime = DateProvider.now();
		Date period = DateProvider.daysBefore(currentTime, 10);

		for (int i = transactions.size() - 1; i >= 0; i--) {
			/*
			 * if the transaction is a withdrawal (i.e. a negative transaction
			 * amount)
			 */
			if (transactions.get(i).getAmount().compareTo(BigDecimal.ZERO) < 0) {
				/* if the date of the withdrawal is after the desired period */
				if (transactions.get(i).getDate().after(period)) {
					/*
					 * the customer has made a recent withdrawal, thus return
					 * true
					 */
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * This function is the implementation of the second additional feature
	 * listed on the readme.
	 * 
	 * @return 0.1% if a withdrawal was made in the past 10 days, or 5%
	 *         otherwise. This code should replace that of interestEarned() to
	 *         fulfil the second additional feature requirement.
	 */
	public BigDecimal updatedInterestPolicy() {
		if (madeRecentWithdrawal()) {
			return new BigDecimal(String.valueOf(0.001));
		} else {
			return new BigDecimal(String.valueOf(0.05));
		}
	}

	/**
	 * @return a BigDecimal of the interest earned on the balance, using the
	 *         desired guidelines of 2% of the first $1,000, 5% for the next
	 *         $1,000, and 10% for the remainder.
	 */
	public BigDecimal interestEarned() {
		/*
		 * this code has been refactored so that the interest rates and amounts
		 * at which they apply can be changed simply by changing the static
		 * variable at the top of the class definition.
		 */
		BigDecimal amount = BigDecimal.ZERO;
		BigDecimal remainder = balance;

		// if balance is less than 1,000, do 1% of 1,000
		if (balance.compareTo(firstRateAmount) <= 0) {
			amount = balance.multiply(firstRate);
		}
		/*
		 * if less than or equal to 2,000, subtract the maximum amount at which
		 * 1% applies, calculate 1% of it, and add 2% of the remainder.
		 */
		else if (balance.compareTo(secondRateAmount) <= 0) {
			remainder = remainder.subtract(firstRateAmount);
			amount = firstRateAmount.multiply(firstRate);
			amount = amount.add(remainder.multiply(secondRate));
		} else {
			remainder = remainder.subtract(secondRateAmount);

			/* amount = firstRate * firstRateAmount */
			amount = firstRateAmount.multiply(firstRate);
			/* amount += secondRate * (secondRateAmount - firstRateAmount) */
			amount = amount.add(secondRate.multiply((secondRateAmount
					.subtract(firstRateAmount))));
			/* amount += thirdRate * remainder */
			amount = amount.add(remainder.multiply(thirdRate));
		}
		return amount;
	}

}
