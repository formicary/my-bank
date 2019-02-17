package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

    private final int accountType;
	private double balance;
	private List<Transaction> transactions;

	public Account(int accountType, double openingBalance) {
        this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance = openingBalance;
	}

	public Account(int accountType) {
		this(accountType, 0.0f);
	}

	public abstract double interestEarned();

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			balance += amount;
			transactions.add(new Transaction(amount));
		}
	}

	/**
	 * Depositing method with a date. ONLY FOR TESTING SPECIAL INTEREST RATE!
	 * @param amount
	 * @param when
	 */
	public void deposit(double amount, LocalDate when) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			balance += amount;
			transactions.add(new Transaction(when, amount));
		}
	}
	
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			balance -= amount;
			transactions.add(new Transaction(-amount));
		}
	}

	/** 
	 * Method to transfer funds between accounts of a given customer
	 * @param cust
	 * @param from
	 * @param to
	 * @param amount
	 */
	public void transfer(Customer cust, Account from, Account to, double amount) {
		// check the customer has these two accounts
		if (cust.hasAccount(from) && cust.hasAccount(to)) {
			if (from.getBalance() < amount) {
				throw new IllegalArgumentException("Amount to transfer exceeds balance in account.");
			} else {
				from.withdraw(amount);
				to.deposit(amount);
				System.out.println("Transaction completed.");
			}	
		} else {
			throw new IllegalArgumentException("Customer does not have ownwership of both accounts.");
		}
	}

	/**
	 * Old method to get value of account. not very efficient (running total better)
	 * @return
	 */
	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.getAmount();
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions(){
		return this.transactions;
	}

	/**
	 * @return the most recent transaction, or null if there are none yet
	 */
	public Transaction getMostRecentTransaction() {
		return (this.transactions.isEmpty()) ? null : this.transactions.get(this.transactions.size()-1);
	}
	
	public double getBalance() {
		return this.balance;
	}

}
