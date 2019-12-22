package com.abc;

public class SavingsAccount extends Account {

	public SavingsAccount() {
		super(Account.SAVINGS, false);
	}
	
	public SavingsAccount(boolean debuggingEnabled) {
		super(Account.SAVINGS, debuggingEnabled);
	}
	

	public void applyInterest() {
		double amount = sumTransactions();
		if (amount > 0 && daysSinceLastTransaction() > 0) {
			if (amount <= 1000) {
				interest(amount * 0.001);
			} else {
				interest(1 + (amount - 1000) * 0.002);
			}
		}
	}

}
