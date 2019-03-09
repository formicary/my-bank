package com.abc;

import java.util.List;

public interface IAccount {

	//Deposit money into account
	void deposit(double amount);

	//Withdraw money into account
	void withdraw(double amount);

	//Return the total amount in account is transaction exists in account.
	double sumTransactions();

	//Return the account type
	String getAccountType();

	//Return the list of transaction for account
	List<Transaction> getTransactions();

	//Method used to calculate interested earned, to be override by subclasses
	double interestEarned();

	//Get the total interest earned for the account. 
	double totalinterestEarned();

}