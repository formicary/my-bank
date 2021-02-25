package com.abc;

public class CheckingAccount extends Account {
	
	private final static String accountType = "CHECKING";

	public CheckingAccount() {
		super(accountType);
	}

	@Override
	public double interestEarned(Account thisAccount , double amount) {
		return amount*0.001;
	}

}
