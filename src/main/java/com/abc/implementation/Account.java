package com.abc.implementation;

import com.abc.interfaces.IAccount;
import com.abc.interfaces.ITransaction;
import com.abc.utilities.AccountType;

public abstract class Account implements IAccount {

	private AccountType accountType;

	protected ITransaction rootTransaction;
	protected ITransaction currentTransaction;

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.rootTransaction = null;
	}

	public abstract double compoundInterestEarned();

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			ITransaction newTransaction = new Transaction(amount);
			if (rootTransaction == null) {
				rootTransaction = newTransaction;
			} else {
				currentTransaction.setNextTransaction(newTransaction);
			}
			currentTransaction = newTransaction;
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			if (amount <= getTotalBalance()) {

				ITransaction newTransaction = new Transaction(-amount);
				if (rootTransaction == null) {
					rootTransaction = newTransaction;
				} else {
					currentTransaction.setNextTransaction(newTransaction);
				}
				currentTransaction = newTransaction;
			} else {
				throw new IllegalArgumentException("you don't have sufficient funds available");
			}
		}
	}

	public double getTotalBalance() {
		double balance = 0;
		ITransaction transaction = rootTransaction;
		while (transaction != null) {
			balance += transaction.getAmount();
			transaction = transaction.getNextTransaction();
		}
		return balance;
	}

	public ITransaction getRootTransaction() {
		return rootTransaction;
	}

	public AccountType getAccountType() {
		return accountType;
	}

}
