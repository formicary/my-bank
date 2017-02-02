package com.abc;

public interface CustomerAccount {

	public void deposit(double amount);
	public void withdraw(double amount);
	public double interestEarned();
	public double sumTransactions();
	public int getAccountType();
	
}
