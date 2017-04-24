package com.abc.interfaces;


public interface ICustomer {
	public String getName();
	 public ICustomer openAccount(IAccount account);
	 public int getNumberOfAccounts();
	 public double totalInterestEarned();
	 public String getStatement();
	 public boolean TransferFunds(IAccount from, IAccount to, double amount);
}
