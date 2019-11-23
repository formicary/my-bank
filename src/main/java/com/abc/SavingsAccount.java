package com.abc;

public class SavingsAccount extends Account {
	
	public SavingsAccount(int accountNumber) {
		super(accountNumber);
	}
	
	public double interestEarned() {
		double balance = updateBalance();
		return balance-1000 > 0 ? 1 + (balance-1000) * getInterestRate() : balance * getInterestRate();
	}
	
	public double setInterestRate() {
		if (getBalance() < 1000)
			return 0.001;
		else
			return 0.002;
	}
	
	@Override
	public String toString() {
		
		return "Savings Account\n" + super.toString();
	}

}
