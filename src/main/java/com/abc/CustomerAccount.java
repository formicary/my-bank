package com.abc;

import java.util.List;

public interface CustomerAccount {

	public void deposit(double amount);
	public void withdraw(double amount);
	public double interestEarned();
	public double sumTransactions();
	public boolean hasWithdrawalBeenMade();
	public int getAccountType();
	public List<Transaction> getTransactions();
	
}
