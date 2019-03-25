package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Savings_Account extends Account {

	public Savings_Account(String accountType, int accountID) {
		super(accountType, accountID);
	}

	@Override
	public BigDecimal interestEarned() {
		if (getBalance().compareTo(Constants.SAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD) == Constants.ZERO_INT 
				|| getBalance().compareTo(Constants.SAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD) == Constants.NEGATIVE_ONE) {
			BigDecimal interestEarned = getBalance().multiply(Constants.SAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE);
			return interestEarned.setScale(2, RoundingMode.HALF_UP);
		} else {
			BigDecimal interestEarnedSecondTier = (getBalance().subtract(Constants.SAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD)).multiply(Constants.SAVINGS_ACCOUNT_SECOND_TIER_INTEREST_RATE);
			BigDecimal interestEarnedFirstTier = Constants.SAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD.multiply(Constants.SAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE);
			BigDecimal interestEarned = interestEarnedSecondTier.add(interestEarnedFirstTier);
			return interestEarned.setScale(2, RoundingMode.HALF_UP);
		}
	}

}
