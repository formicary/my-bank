package com.abc;

public final class Maxi_SavingsAccount extends Account{
	
	public Maxi_SavingsAccount(){
		super(accountTypes.MAXI_SAVINGS);
	}
	
	public double interestEarned(){
		double amount = sumTransactions();
		if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000 && amount > 1000)
            return 20 + (amount-1000) * 0.05;
        return 70 + (amount-2000) * 0.1;
	}
}
