package com.abc;

public class SavingsAccount extends Account{

	@Override
	public double interestEarned() {
		if (balance <= 1000)
            return balance * 0.001;
        else
            return 1 + (balance-1000) * 0.002;
	}

	@Override
	public String getAccountType() {
		return "Savings Account";
	}

}
