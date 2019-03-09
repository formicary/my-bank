package com.abc;

public interface ICustomer {

	String getName();

	void openAccount(Account account);

	int getNumberOfAccounts();

	//Get the sum of the total interest earned for all accounts
	double sumInterestEarned();

	//Return the statement for all accounts, each account is labelled with an account number.
	String getStatement();

	/*Transfer money between two accounts opened by a customer, 
	 * each account can be referenced by the account number shown in the statement
	 * To transfer, it will withdraw money from one account and deposit the money to 
	 * the other account.  
	*/
	void internalTransfer(int fromAccountNo, int toAccountNo, double amount);

}