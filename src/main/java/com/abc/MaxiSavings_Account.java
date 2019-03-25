package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MaxiSavings_Account extends Account {

	public MaxiSavings_Account(String accountType, int accountID) {
		super(accountType, accountID);

	}

	@Override
	public BigDecimal interestEarned() {
		if (getBalance().compareTo(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD) == Constants.ZERO_INT || getBalance().compareTo(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD) == Constants.NEGATIVE_ONE) {
			BigDecimal interestEarned = getBalance().multiply(Constants.MAXISAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE);
			return interestEarned.setScale(2, RoundingMode.HALF_UP);
		}
		if (getBalance().compareTo(Constants.MAXISAVINGS_ACCOUNT_THIRD_TIER_LOWER_THRESHOLD) == Constants.ZERO_INT || 
		(getBalance().compareTo(Constants.MAXISAVINGS_ACCOUNT_THIRD_TIER_LOWER_THRESHOLD) == Constants.NEGATIVE_ONE && getBalance().compareTo(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD) == Constants.ONE)) {
			BigDecimal interestEarnedSecondTier = (getBalance().subtract(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD)).multiply(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_INTEREST_RATE);
			BigDecimal interestEarnedFirstTier = Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD.multiply(Constants.MAXISAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE);	
			BigDecimal totalInterestEarned = interestEarnedFirstTier.setScale(2, RoundingMode.HALF_UP).add(interestEarnedSecondTier.setScale(2, RoundingMode.HALF_UP));
			return totalInterestEarned;
		} else {
			BigDecimal interestEarnedFirstTier = Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_LOWER_THRESHOLD.multiply(Constants.MAXISAVINGS_ACCOUNT_FIRST_TIER_INTEREST_RATE);
			BigDecimal interestEarnedSecondTier = Constants.ONE_THOUSAND.multiply(Constants.MAXISAVINGS_ACCOUNT_SECOND_TIER_INTEREST_RATE);
			BigDecimal interestEarnedThirdTier = (getBalance().subtract(Constants.MAXISAVINGS_ACCOUNT_THIRD_TIER_LOWER_THRESHOLD)).multiply(Constants.MAXISAVINGS_ACCOUNT_THIRD_TIER_INTEREST_RATE);
			BigDecimal totalInterestEarned = interestEarnedFirstTier.setScale(2, RoundingMode.HALF_UP).add(interestEarnedSecondTier.setScale(2, RoundingMode.HALF_UP)).add(interestEarnedThirdTier.setScale(2, RoundingMode.HALF_UP));
			return totalInterestEarned;
		}
	}

}
