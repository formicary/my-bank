package com.abc;

public class CheckingAccount extends Account {
	
	public CheckingAccount(int accountNumber) {
		super(accountNumber);
	}
	
	public double interestEarned() {
		return updateBalance() * getInterestRate();
	}
	
	public double setInterestRate() {
		return 0.001;
	}
	
	@Override
	public String toString() {
		
		return "Checking Account\n" + super.toString();
	}

}
