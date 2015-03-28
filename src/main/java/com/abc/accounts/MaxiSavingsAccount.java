package com.abc.accounts;


import com.abc.Account;
import com.abc.interests.DefaultMaxiSavingsStrategy;
import com.abc.interests.InterestRateStrategy;
import com.abc.interests.WithdrawalBasedMaxiStrategy;

final public class MaxiSavingsAccount extends Account {

	
	@Override
	public AccountType getAccountType() {
		return AccountType.MAXISAVINGS;
	}

	@Override
	public boolean isApplicable(InterestRateStrategy strategy) {
		if ((strategy instanceof WithdrawalBasedMaxiStrategy) || (strategy instanceof DefaultMaxiSavingsStrategy))
			return true;
		return false;
	}

}
