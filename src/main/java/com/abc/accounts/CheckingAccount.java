package com.abc.accounts;


import com.abc.Account;
import com.abc.interests.CheckingStrategy;
import com.abc.interests.InterestRateStrategy;

public final class CheckingAccount extends Account {

	@Override
	public AccountType getAccountType() {
		return AccountType.CHECKING;
	}

	@Override
	public boolean isApplicable(InterestRateStrategy strategy) {
		if (strategy instanceof CheckingStrategy)
			return true;
		return false;
	}

}
