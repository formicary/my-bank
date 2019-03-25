package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Checking_Account extends Account {

	public Checking_Account(String accountType, int accountID) {
		super(accountType, accountID);
	}

	@Override
	public BigDecimal interestEarned() {
		BigDecimal interestEarned = getBalance().multiply(Constants.CHECKING_ACCOUNT_INTEREST_RATE);
		return interestEarned.setScale(2, RoundingMode.HALF_UP);
	}
}
