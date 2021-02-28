package com.abc;

public class CheckingAccount extends Account {
	
	private final static String accountType = "CHECKING";

	public CheckingAccount() {
		super(accountType);
	}
	
	@Override
	public double interestEarned() {
		return sumTransactions()*0.001;
	}

}
 