package com.abc;

public final class CheckingAccount extends Account{
	
	public CheckingAccount(){
		super(accountTypes.CHECKING);
	}
	
	public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
}
