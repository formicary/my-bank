package com.abc;

public interface BankCustomer {
	
	public String getName();
	public Customer openAccount(Account account);
	public int getNumberOfAccounts();
	public double totalInterestEarned();
	public String getStatement();
	
}
