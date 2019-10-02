package com.abc;

public class CheckingAccount extends Account{

	@Override
	public String toString() {
		return "Checking Account";
	}

	@Override
	public double getInterest() {
		return sumTransactions() * 0.001;
	}
}
