package com.abc;

public class CheckingAccount extends Account{
	
	@Override
	public double interestEarned() {
		return balance * 0.001;
	}

	@Override
	public String getAccountType() {
		return "Checking Account";
	}

}
