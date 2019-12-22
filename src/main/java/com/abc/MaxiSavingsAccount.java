package com.abc;

public class MaxiSavingsAccount extends Account {

	public MaxiSavingsAccount() {
		super(Account.MAXI_SAVINGS, false);
	}
	
	public MaxiSavingsAccount(boolean debuggingEnabled) {
		super(Account.MAXI_SAVINGS, debuggingEnabled);
	}

	public void applyInterest() {
		double amount = sumTransactions();
		if (amount > 0 && daysSinceLastTransaction() > 0) {
			if (amount <= 1000) {
				interest(amount * 0.02);

			} else if (amount <= 2000) {
				interest(20 + (amount - 1000) * 0.05);
			} else {
				interest(70 + (amount - 2000) * 0.1);

			}
		}
	}
}
