package com.abc.account;

import java.util.List;

import com.abc.transaction.Transaction;

public interface AccountI {

	
	 void deposit(double amount);

	 void withdraw(double amount);

     double interestEarned();

	 double sumTransactions();
	 
	 List<Transaction> getTransactions();

	
	 
	 
}
