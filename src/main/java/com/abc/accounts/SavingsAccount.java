package com.abc.accounts;


import com.abc.Account;
import com.abc.interests.InterestRateStrategy;
import com.abc.interests.SavingsStrategy;

final public class SavingsAccount extends Account {

	
	@Override
	public AccountType getAccountType() {
		return AccountType.SAVINGS;
	}

	@Override
	public boolean isApplicable(InterestRateStrategy strategy) {
		if (strategy instanceof SavingsStrategy)
			return true;
		return false;
	}

}
